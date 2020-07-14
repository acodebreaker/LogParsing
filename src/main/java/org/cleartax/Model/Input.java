package org.cleartax.Model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Input {

    long timestamp;
    String url;
    String method;
    long responseTime;
    int httpStatus;
    int frequency;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public Input(long timestamp, String url, String method, int httpStatus,long responseTime)
    {
        this.timestamp=timestamp;
        this.url=url;
        this.method=method;
        this.httpStatus=httpStatus;
        this.frequency=0;
        this.responseTime=responseTime;
    }


    @Override
    public boolean equals(Object input)
    {
        Input inputObj = (Input) input;
        if(  inputObj.getUrl().equals(this.getUrl())  && inputObj.getMethod().equals( this.getMethod() ) )
            return true;
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.getUrl().length() + this.getMethod().length();
    }

}
