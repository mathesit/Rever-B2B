package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class ServiceRequest {
    private int srCount;

    public ServiceRequest(int count){
        this.srCount =count;

    }
    public void setSrCount(int count){
        this.srCount = count;
    }

    public int getSrCount(){ return srCount; }

}
