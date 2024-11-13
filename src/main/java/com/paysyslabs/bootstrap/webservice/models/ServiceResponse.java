package com.paysyslabs.bootstrap.webservice.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "response")
public class ServiceResponse {
    
    @JacksonXmlProperty(localName = "response_code")
    private String code;

    @JacksonXmlProperty(localName = "response_desc")
    private String description;

    public ServiceResponse(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public ServiceResponse() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
