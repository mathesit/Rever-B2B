package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class UsedProduct {
    private int quantity, rank;
    private String part_no;

    public UsedProduct(int qty, int rank, String partNo){
        this.quantity = qty;
        this.rank = rank;
        this.part_no = partNo;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getRank(){
        return rank;
    }

    public String getPartNo(){
        return part_no;
    }

    public void setQuantity(int qty){
        this.quantity = qty;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public void setPartNo(String partNo){
        this.part_no = partNo;
    }
}
