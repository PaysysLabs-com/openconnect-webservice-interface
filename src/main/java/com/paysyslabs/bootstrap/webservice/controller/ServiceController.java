package com.paysyslabs.bootstrap.webservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paysyslabs.bootstrap.webservice.exceptions.ServiceException;
import com.paysyslabs.bootstrap.webservice.models.ServiceResponse;
import com.paysyslabs.bootstrap.webservice.models.TransactionDetails;
import com.paysyslabs.bootstrap.webservice.utils.PrettyPrinter;
import com.paysyslabs.bootstrap.webservice.utils.RecoveringRpcClient;
import com.paysyslabs.bootstrap.webservice.validators.HashValidator;

@RestController
@RequestMapping(value = "${service.endpoint}", produces = { MediaType.APPLICATION_XML_VALUE })
public class ServiceController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ServiceController.class);
    
    @Autowired
    private HashValidator validator;
    
    @Autowired
    @Qualifier("transactionMap")
    private Map<String, TransactionDetails> transactionMap;
    
    @Autowired
    @Qualifier("rpcClient")
    private RecoveringRpcClient client;
    
    @Autowired
    private PrettyPrinter prettyPrinter;
    
    private AtomicLong counter = new AtomicLong(0);
    
    private String forwardInternal(HttpServletRequest request, String params, String hash) throws Exception {
    	long requestNumber = counter.incrementAndGet();
    	
        List<String> parameters = Arrays.asList(params.split(","));
        
        String serviceName = parameters.get(0);
        String ipAddress = request.getRemoteAddr();
        
        LOG.info(
            "\n\n[ REQUEST #" + requestNumber + " ]\n" +
             "IP      " + ipAddress + "\n" +
             "PARAMS  " + params + "\n" +
             "HASH    " + hash + "\n\n"
        );
        
        if (serviceName == null || serviceName.isEmpty())
            throw new ServiceException(requestNumber, "101", "Mandatory parameter 'serviceName' not provided");
        
        TransactionDetails details = transactionMap.get(serviceName);
        
        if (details == null)
            throw new ServiceException(requestNumber, "101", "Failed to find details for serviceName: " + serviceName);
        
        if (!details.isWildcardAllowed() && !details.isIPAllowed(ipAddress))
            throw new ServiceException(requestNumber, "103", "Invalid source IP address");
        
        if (parameters.size() - 1 != details.getAllowedRequestParameters().size())
            throw new ServiceException(requestNumber, "103", "Mandatory request params not provided");
        
        if (!validator.validate(parameters, hash))
            throw new ServiceException(requestNumber, "102", "Invalid hash value provided");
        
        StringBuilder builder = new StringBuilder();

        builder.append(serviceName);
        for (int i=0; i<details.getAllowedRequestParameters().size(); i++) {
            builder.append(String.format("/%s", parameters.get(i + 1)));
        }
        
        String call = builder.toString();
        
        LOG.info("R#{} calling {}", requestNumber , call);
        
        String result = client.call(call);
        prettyPrinter.print(requestNumber, result);
        
        return result;
    }
    
    @RequestMapping(value = "queueforwarding/{params}/{hash}", method = RequestMethod.GET)
    @ResponseBody
    public String forward(HttpServletRequest request, @PathVariable("params") String params, @PathVariable("hash") String hash) throws Exception {
        return forwardInternal(request, params, hash);
    }
    
    @RequestMapping(value = "queueforwarding/{hash}", method = RequestMethod.POST)
    @ResponseBody
    public String forwardPost(HttpServletRequest request, @RequestBody String params, @PathVariable("hash") String hash) throws Exception {
        return forwardInternal(request, params, hash);
    }
    
    
    @ExceptionHandler(value = ServiceException.class)
    private ServiceResponse exceptionHandler(ServiceException e) {
        LOG.error("R#{} ERROR: {}", e.getRequestNumber(), e.getMessage());
        return e.getResponse();
    }
    
    @ExceptionHandler(value = TimeoutException.class)
    private ServiceResponse timeoutExceptionHandler(TimeoutException e) {
        LOG.error("ERROR: {}", "Timed out!");
        return new ServiceResponse("100", "Request timed out");
    }
    
    @ExceptionHandler(value = Exception.class)
    private ServiceResponse genericExceptionHandler(Exception e) {
        LOG.error("ERROR: {}", e.getMessage());
        LOG.error("OOPS", e);
        return new ServiceResponse("500", e.getMessage());
    }
}
