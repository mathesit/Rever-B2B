package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class Failures {
    private int failureCount;
    private String failureModel, failureProdType, failureBrand;

    public Failures(String brand, String model, String prodType, int failure){
        this.failureBrand = brand;
        this.failureModel = model;
        this.failureProdType = prodType;
        this.failureCount = failure;
    }

    public int getFailureCount(){ return failureCount; }

    public String getFailureBrand(){ return failureBrand; }

    public String getFailureModel(){ return failureModel; }

    public String getFailureProdType(){ return failureProdType; }

    public void setFailureCount(int count){ this.failureCount = count; }

    public void setFailureModel(String model){ this.failureModel = model; }

    public void setFailureProdType(String type){ this.failureProdType = type; }

    public void setFailureBrand(String brand){ this.failureBrand = brand; }
}
