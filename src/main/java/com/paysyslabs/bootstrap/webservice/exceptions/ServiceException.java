package com.paysyslabs.bootstrap.webservice.exceptions;

import com.paysyslabs.bootstrap.webservice.models.ServiceResponse;

public class ServiceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 520924980768584055L;

    private final long requestNumber;
    private final String code;

    public ServiceException(long requestNumber, String code, String message) {
        super(message);
        this.requestNumber = requestNumber;
        this.code = code;
    }

    public ServiceResponse getResponse() {
        return new ServiceResponse(code, getMessage());
    }

    public long getRequestNumber() {
        return requestNumber;
    }

}
