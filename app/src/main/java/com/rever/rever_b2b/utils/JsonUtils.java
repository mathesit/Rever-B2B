package com.rever.rever_b2b.utils;


import com.rever.rever_b2b.model.CaseLog;
import com.rever.rever_b2b.model.Failures;
import com.rever.rever_b2b.model.Quotation;
import com.rever.rever_b2b.model.ServiceRequest;
import com.rever.rever_b2b.model.ServiceRequestList;
import com.rever.rever_b2b.model.StockBalance;
import com.rever.rever_b2b.model.UsedProduct;
import com.rever.rever_b2b.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class JsonUtils {

    public static List<User> parseUserJson(String json) {
        List<User> users = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            JSONObject cObj = response.getJSONObject("user");
            users.add(new User(cObj.getInt("user_id"), cObj.getInt("company_id"), cObj.getString("session_token"),cObj.getString("first_name"),
                        cObj.getString("country_code"), cObj.getString("city"), cObj.getString("email"), cObj.getString("user_type")));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<StockBalance> parseStockBalJson(String json) {
        List<StockBalance> stocks = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            JSONArray array = response.getJSONArray("stockbalance");
            for(int i = 0; i<array.length();i++) {
                JSONObject cObj = array.getJSONObject(i);
                stocks.add(new StockBalance(cObj.getString("brand_name"),cObj.getString("model_name"),
                        cObj.getString("product_type"), cObj.getInt("stock")));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public static List<CaseLog> parseCaseLogJson(String json) {
        List<CaseLog> caseLogs = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            caseLogs.add(new CaseLog( response.getInt("caselog-count")));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return caseLogs;
    }

    public static List<Quotation> parseQuotationJson(String json) {
        List<Quotation> quotCount = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            quotCount.add(new Quotation( response.getInt("pending-quotation-count")));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return quotCount;
    }

    public static List<ServiceRequest> parseServiceRequestJson(String json) {
        List<ServiceRequest> srCount = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            srCount.add(new ServiceRequest( response.getInt("sr-count")));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return srCount;
    }

    public static List<ServiceRequestList> parseServiceRequestListJson(JSONObject json) {
        List<ServiceRequestList> srList = new ArrayList<>();
            try {
                JSONArray array = json.getJSONArray("EW");
                int size= array.length();
                for(int index = 0 ; index < size; index++) {
                    JSONObject jObj = array.getJSONObject(index);
                    String sr_id = jObj.getString("sr_id"),
                            sr_no = jObj.getString("sr_no"),
                            status = jObj.getString("status"),
                            createdOn = jObj.getString("created_on"),
                            consumerName = jObj.getString("consumer_name");
                    srList.add(new ServiceRequestList(sr_id, sr_no, status, createdOn, consumerName));
                    System.out.println("sr_id: " + sr_id + " sr_no: " + sr_no+" status:"+status+"  createdOn:"+createdOn+"  consumer:"+consumerName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return srList;
    }

    public static List<Failures> parseFailuresJson(String json) {
        List<Failures> failures = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            JSONArray array = response.getJSONArray("SR");
            for(int i = 0; i<array.length();i++) {
                JSONObject cObj = array.getJSONObject(i);
                String brand="", model="", prodtype="";
                int count=0;
                if(cObj.has("brand_name")) brand = cObj.getString("brand_name");
                if(cObj.has("model_name")) model = cObj.getString("model_name");
                if(cObj.has("product_type")) prodtype = cObj.getString("product_type");
                if(cObj.has("failure_count")) count = cObj.getInt("failure_count");
                failures.add(new Failures(brand, model, prodtype, count));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return failures;
    }

    public static List<UsedProduct> parseUsedProductsJson(String json) {
        List<UsedProduct> usedProds = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(json);
            JSONArray array = response.getJSONArray("top5-products");
            for(int i = 0; i<array.length();i++) {
                JSONObject cObj = array.getJSONObject(i);
                usedProds.add(new UsedProduct( cObj.getInt("quantity"),cObj.getInt("rank"),
                        cObj.getString("part_no")));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return usedProds;
    }




}
