package com.rever.rever_b2b.utils;


import android.util.Log;

import com.rever.rever_b2b.model.CaseLog;
import com.rever.rever_b2b.model.Failures;
import com.rever.rever_b2b.model.Quotation;
import com.rever.rever_b2b.model.ServiceRequest;
import com.rever.rever_b2b.model.ServiceRequestDetails;
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
                JSONArray array = json.getJSONArray("SR");
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

    public static ServiceRequestDetails parseServiceRequestDetail(JSONObject response) {
        Log.i("myLog","ServiceRequestDetails response:"+response.toString());
        //ServiceRequestDetails response:{"SR":{"created_by":"ramesh raj","other_desc":" FFTYTFYTYFT","status":"Quotation Pending","created_on":"16\/07\/2015 03:13","state":"TN","product_type":"TV","ic_no":"2232312321","country_code":"SG","serial_no":"888891889","model_name":"TV-S100","brand_name":"YARRAA","city":"Chennai","product_status":"Product Not Yet Received","first_name":"ramesh","address_line1":"Singapore","company_name":"CHIN TIONG SERVICE","postal_code":"613001","sr_no":"458","ew_count ":0,"last_name":"raj","user_id":2384,"sr_id":13601,"return_count":1,"mobile":"65-3434343434"}}
        ServiceRequestDetails servReqDet = new ServiceRequestDetails();
        try {
            JSONObject jObj = response.getJSONObject("SR");
            MasterCache.srDetSrId.clear(); MasterCache.srDetSrNo.clear(); MasterCache.srDetStatus.clear();
            MasterCache.srDetCreatedOn.clear(); MasterCache.srDetFailureDesc.clear();
            MasterCache.srDetCreatedBy.clear(); MasterCache.srDetWarrStatus.clear(); MasterCache.srDetRetCount.clear();
            MasterCache.srDetUserId.clear(); MasterCache.srDetFirstName.clear();
            MasterCache.srDetLastName.clear(); MasterCache.srDetCity.clear(); MasterCache.srDetCountryCode.clear();
            MasterCache.srDetMobile.clear(); MasterCache.srDetIcNo.clear();
            MasterCache.srDetAddrLine1.clear(); MasterCache.srDetState.clear(); MasterCache.srDetPostalCode.clear();
            MasterCache.srDetOtherDesc.clear(); MasterCache.srDetRemarks.clear();
            MasterCache.srDetProductStatus.clear(); MasterCache.srDetCompanyName.clear(); MasterCache.srDetBrandName.clear();
            MasterCache.srDetModelName.clear();
            MasterCache.srDetSerialNo.clear(); MasterCache.srDetProdType.clear(); MasterCache.srDetEwCount.clear();

            String sr_id= "", sr_no = "", status = "", createdOn = "", failure_desc = "", createdBy = "", returnCount = "",
                    userId = "", firstName = "", lastName = "", city = "", warrantyStatus = "",
                    countryCode = "", mobile = "",icNo = "", address = "", state = "", postalCode = "", otherDesc = "",
                    companyName = "", brandName = "", modelName = "", serialNo = "",
                    remarks = "", productType = "", productStatus = "";
            if(jObj.has("sr_id"))
                sr_id = jObj.getString("sr_id");
            if(jObj.has("sr_no"))
                sr_no = jObj.getString("sr_no");
            if(jObj.has("status"))
                status = jObj.getString("status");
            if(jObj.has("created_on"))
                createdOn = jObj.getString("created_on");
            if(jObj.has("failure_desc"))
                failure_desc = jObj.getString("failure_desc");
            if(jObj.has("warranty_status"))
                warrantyStatus = jObj.getString("warranty_status");
            if(jObj.has("created_by"))
                createdBy = jObj.getString("created_by");
            if(jObj.has("return_count"))
                returnCount = jObj.getString("return_count");
            if(jObj.has("user_id"))
                userId = jObj.getString("user_id");
            if(jObj.has("first_name"))
                firstName = jObj.getString("first_name");
            if(jObj.has("last_name"))
                lastName = jObj.getString("last_name");
            if(jObj.has("city"))
                city = jObj.getString("city");
            if(jObj.has("country_code"))
                countryCode = jObj.getString("country_code");
            if(jObj.has("mobile"))
                mobile = jObj.getString("mobile");
            if(jObj.has("ic_no"))
                icNo = jObj.getString("ic_no");
            if(jObj.has("address_line1"))
                 address = jObj.getString("address_line1");
            if(jObj.has("state"))
                state = jObj.getString("state");
            if(jObj.has("postal_code"))
                postalCode = jObj.getString("postal_code");
            if(jObj.has("other_desc"))
                otherDesc = jObj.getString("other_desc");
            if(jObj.has("remarks"))
                remarks = jObj.getString("remarks");
            if(jObj.has("product_status"))
                productStatus = jObj.getString("product_status");
            if(jObj.has("company_name"))companyName = jObj.getString("company_name");
            if(jObj.has("brand_name"))  brandName = jObj.getString("brand_name");
            if(jObj.has("model_name")) modelName = jObj.getString("model_name");
            if(jObj.has("serial_no")) serialNo = jObj.getString("serial_no");
            if(jObj.has("product_type")) productType = jObj.getString("product_type");

            servReqDet.setSr_id(sr_id); servReqDet.setSr_no(sr_no); servReqDet.setStatus(status);
            servReqDet.setCreated_on(createdOn); servReqDet.setFailure_desc(failure_desc);
            servReqDet.setCreated_by(createdBy);   servReqDet.setWarranty_status(warrantyStatus);
            servReqDet.setReturn_count(returnCount); servReqDet.setUser_id(userId);
            servReqDet.setFirst_name(firstName); servReqDet.setLast_name(lastName); servReqDet.setCity(city);
            servReqDet.setCountry_code(countryCode); servReqDet.setMobile(mobile); servReqDet.setIc_no(icNo);
            servReqDet.setAddress_line1(address); servReqDet.setState(state); servReqDet.setPostal_code(postalCode);
            servReqDet.setOther_desc(otherDesc); servReqDet.setRemarks(remarks); servReqDet.setProduct_status(productStatus);
            servReqDet.setCompany_name(companyName); servReqDet.setBrand_name(brandName); servReqDet.setModel_name(modelName);
            servReqDet.setSerial_no(serialNo); servReqDet.setProduct_type(productType); //servReqDet.setEw_count(ew_count);

            MasterCache.srDetSrId.add(servReqDet.getSr_id()); MasterCache.srDetSrNo.add(servReqDet.getSr_no());
            MasterCache.srDetStatus.add(servReqDet.getStatus()); MasterCache.srDetCreatedOn.add(servReqDet.getCreated_on());
            MasterCache.srDetFailureDesc.add(servReqDet.getFailure_desc()); MasterCache.srDetCreatedBy.add(servReqDet.getCreated_by());
            MasterCache.srDetWarrStatus.add(servReqDet.getWarranty_status()); MasterCache.srDetRetCount.add(servReqDet.getReturn_count());
            MasterCache.srDetUserId.add(servReqDet.getUser_id()); MasterCache.srDetFirstName.add(servReqDet.getFirst_name());
            MasterCache.srDetLastName.add(servReqDet.getLast_name()); MasterCache.srDetCity.add(servReqDet.getCity());
            MasterCache.srDetCountryCode.add(servReqDet.getCountry_code()); MasterCache.srDetMobile.add(servReqDet.getMobile());
            MasterCache.srDetIcNo.add(servReqDet.getIc_no()); MasterCache.srDetAddrLine1.add(servReqDet.getAddress_line1());
            MasterCache.srDetState.add(servReqDet.getState()); MasterCache.srDetPostalCode.add(servReqDet.getPostal_code());
            MasterCache.srDetOtherDesc.add(servReqDet.getOther_desc()); MasterCache.srDetRemarks.add(servReqDet.getRemarks());
            MasterCache.srDetProductStatus.add(servReqDet.getProduct_status()); MasterCache.srDetCompanyName.add(servReqDet.getCompany_name());
            MasterCache.srDetBrandName.add(servReqDet.getBrand_name()); MasterCache.srDetModelName.add(servReqDet.getModel_name());
            MasterCache.srDetSerialNo.add(servReqDet.getSerial_no()); MasterCache.srDetProdType.add(servReqDet.getProduct_type());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return servReqDet;
    }



}
