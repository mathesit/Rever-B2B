package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class CaseLog {
    private int caseLogCount;

    public CaseLog(int count){
        this.caseLogCount =count;

    }
    public void setCaseLogCount(int count){
        this.caseLogCount = count;
    }

    public int getCaseLogCount(){ return caseLogCount; }
}
