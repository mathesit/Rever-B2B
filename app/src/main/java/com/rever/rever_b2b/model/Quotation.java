package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class Quotation {
    private int quotationCount;

    public Quotation(int count){
        this.quotationCount =count;

    }
    public void setQuotationCount(int count){
        this.quotationCount = count;
    }

    public int getQuotationCount(){ return quotationCount; }
}