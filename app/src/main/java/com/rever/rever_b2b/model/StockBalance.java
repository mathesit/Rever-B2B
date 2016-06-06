package com.rever.rever_b2b.model;

import android.util.Log;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class StockBalance {

    private int stockCount;
    private String stockModel, stockProdType, stockBrand;

    public StockBalance(String brand, String model, String prodType, int stock){
        Log.i("myLog","Stock bal resp:"+ brand+"  "+model+"  "+prodType+"  "+stock);
        this.stockBrand = brand;
        this.stockModel = model;
        this.stockProdType = prodType;
        this.stockCount = stock;
    }

    public int getStockCount(){ return stockCount; }

    public String getStockBrand(){ return stockBrand; }

    public String getStockModel(){ return stockModel; }

    public String getStockProdType(){ return stockProdType; }

    public void setStockCount(int count){ this.stockCount = count; }

    public void setStockModel(String model){ this.stockModel = model; }

    public void setStockProdType(String type){ this.stockProdType = type; }

    public void setStockBrand(String brand){ this.stockBrand = brand; }
}
