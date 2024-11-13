package com.paysyslabs.bootstrap.webservice.models;

import java.util.List;

public class TransactionDetails {
    private Integer transactionId;
    private List<String> allowedIps;
    private List<String> allowedRequestParameters;
    private String inQueue;
    private String outQueue;
    private String queueType;
    private Integer HostId;
    private String reversalApi;
    
    public Integer getHostId() {
        return HostId;
    }

    public void setHostId(Integer hostId) {
        HostId = hostId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public List<String> getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }

    public List<String> getAllowedRequestParameters() {
        return allowedRequestParameters;
    }

    public void setAllowedRequestParameters(List<String> allowedRequestParameters) {
        this.allowedRequestParameters = allowedRequestParameters;
    }

    public String getInQueue() {
        return inQueue;
    }

    public void setInQueue(String inQueue) {
        this.inQueue = inQueue;
    }

    public String getOutQueue() {
        return outQueue;
    }

    public void setOutQueue(String outQueue) {
        this.outQueue = outQueue;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getReversalApi() {
		return reversalApi;
	}

	public void setReversalApi(String reversalApi) {
		this.reversalApi = reversalApi;
	}

	public boolean isWildcardAllowed() {
        if (allowedIps == null || allowedIps.size() <= 0)
            return false;

        return allowedIps.get(0).equals("*");
    }
    
    public boolean isIPAllowed(String ip) {
        if (allowedIps == null || allowedIps.size() <= 0)
            return false;
        
        return allowedIps.contains(ip);
    }

    @Override
	public String toString() {
		return "TransactionDetails [transactionId=" + transactionId + ", allowedIps=" + allowedIps
				+ ", allowedRequestParameters=" + allowedRequestParameters + ", inQueue=" + inQueue + ", outQueue="
				+ outQueue + ", queueType=" + queueType + ", HostId=" + HostId + ", reversalApi=" + reversalApi + "]";
	}

}
