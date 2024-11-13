package com.paysyslabs.bootstrap.webservice.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.paysyslabs.bootstrap.webservice.models.TransactionDetails;

@Entity
@Table(name = "ws_req_param_details")
public class WSParamDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tran_id", unique = false)
    private Integer transactionId;

    @Column(name = "tran_type")
    private String transactionType;

    @Column(name = "req_params")
    private String requestParams;

    @Column(name = "queue_in")
    private String inboundQueue;

    @Column(name = "queue_type")
    private String queueType;

    @Column(name = "host_id")
    private Integer hostId;

    @Column(name = "from_ip")
    private String allowedIps;

    @Column(name = "enclosing_tag")
    private String requestEnclosingTag;

    public TransactionDetails getTransactionDetail() {
        TransactionDetails details = new TransactionDetails();
        details.setTransactionId(transactionId);

        if (allowedIps != null)
            details.setAllowedIps(Arrays.asList(allowedIps.split(",")));

        if (requestParams != null)
            details.setAllowedRequestParameters(Arrays.asList(requestParams.split(",")));

        details.setInQueue(inboundQueue);
        details.setQueueType(queueType);
        details.setHostId(hostId);
        return details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getInboundQueue() {
        return inboundQueue;
    }

    public void setInboundQueue(String inboundQueue) {
        this.inboundQueue = inboundQueue;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    public String getRequestEnclosingTag() {
        return requestEnclosingTag;
    }

    public void setRequestEnclosingTag(String requestEnclosingTag) {
        this.requestEnclosingTag = requestEnclosingTag;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }    
}
