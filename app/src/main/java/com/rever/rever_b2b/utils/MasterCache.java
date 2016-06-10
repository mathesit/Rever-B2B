package com.rever.rever_b2b.utils;


import com.rever.rever_b2b.model.CaseLog;
import com.rever.rever_b2b.model.Failures;
import com.rever.rever_b2b.model.Quotation;
import com.rever.rever_b2b.model.ServiceRequest;
import com.rever.rever_b2b.model.ServiceRequestDetails;
import com.rever.rever_b2b.model.ServiceRequestList;
import com.rever.rever_b2b.model.StockBalance;
import com.rever.rever_b2b.model.UsedProduct;
import com.rever.rever_b2b.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matheswari on 3/25/2016.
 */

public class MasterCache {
    public static List<User> userList = new ArrayList<>();
    public static List<Integer> userId = new ArrayList<>();
    public static Map<Integer, Integer> companyId = new HashMap<>();
    public static Map<Integer, String> userSessionToken = new HashMap<>();
    public static Map<Integer, String> userFirstName = new HashMap<>();
    public static Map<Integer, String> userCountryCode = new HashMap<>();
    public static Map<Integer, String> userCity = new HashMap<>();
    public static Map<Integer, String> userEmail = new HashMap<>();
    public static Map<Integer, String> userType = new HashMap<>();

    public static List<StockBalance> stockList = new ArrayList<>();
    public static List<Integer> stockCount = new ArrayList<>();
    public static List<String> stockBrand = new ArrayList<>(), stockModel = new ArrayList<>(), stockProdType = new ArrayList<>();

    public static List<Failures> failureList = new ArrayList<>();
    public static List<Integer> failureCount = new ArrayList<>();
    public static List<String> failureBrand = new ArrayList<>(), failureModel = new ArrayList<>(), failureProdType = new ArrayList<>();

    public static List<CaseLog> caseLogList = new ArrayList<>();
    public static List<Integer> caseLogCount = new ArrayList<>();

    public static List<ServiceRequest> srList = new ArrayList<>();
    public static List<Integer> srCount = new ArrayList<>();

    public static List<Quotation> pendingQuoteList = new ArrayList<>();
    public static List<Integer> pendingQuoteCount = new ArrayList<>();

    public static List<UsedProduct> usedProductList = new ArrayList<>();
    public static List<Integer> usedProdQty = new ArrayList<>(), usedProdRank = new ArrayList<>();
    public static List<String> usedProdPartNo = new ArrayList<>();

    public static List<ServiceRequestList> serviceReqList = new ArrayList<>();
    public static List<String> srId = new ArrayList<>(), srNo = new ArrayList<>(), srStatus = new ArrayList<>(), srConsumerName = new ArrayList<>(), srCreatedOn = new ArrayList<>();

    public static List<String> srDetSrId= new ArrayList<>(), srDetSrNo =new ArrayList<>(), srDetStatus = new ArrayList<>(),
            srDetCreatedOn = new ArrayList<>(), srDetWarrStatus = new ArrayList<>(), srDetRetCount = new ArrayList<>(),
            srDetUserId = new ArrayList<>(), srDetFirstName = new ArrayList<>(), srDetLastName = new ArrayList<>(),
            srDetCity= new ArrayList<>(), srDetCountryCode = new ArrayList<>(), srDetMobile = new ArrayList<>(),
            srDetIcNo = new ArrayList<>(), srDetAddrLine1= new ArrayList<>(), srDetState = new ArrayList<>(),
            srDetPostalCode = new ArrayList<>(), srDetOtherDesc= new ArrayList<>(), srDetRemarks = new ArrayList<>(),
            srDetProductStatus = new ArrayList<>(), srDetCompanyName = new ArrayList<>(), srDetBrandName = new ArrayList<>(),
            srDetModelName = new ArrayList<>(), srDetSerialNo = new ArrayList<>(), srDetProdType = new ArrayList<>(),
            srDetEwCount = new ArrayList<>(), srDetFailureDesc = new ArrayList<>(), srDetCreatedBy = new ArrayList<>();

    public static void saveUserCache(String userJson) {
        userList = JsonUtils.parseUserJson(userJson);
        userId.clear();
        companyId.clear();
        userSessionToken.clear();
        userFirstName.clear();
        userCity.clear();
        userCountryCode.clear();
        userEmail.clear();
        userType.clear();
        for(User b : userList) {
            int uId = b.getUserId();
            userId.add(uId);
            companyId.put(uId, b.getComopanyId());
            userSessionToken.put(uId, b.getSessionToken());
            userFirstName.put(uId, b.getFirstName());
            userCountryCode.put(uId, b.getCountryCode());
            userCity.put(uId, b.getCity());
            userEmail.put(uId, b.getEmail());
            userType.put(uId, b.getUserType());
        }
    }

    public static void saveStockBalCache(String userJson) {
        stockList = JsonUtils.parseStockBalJson(userJson);
        stockBrand.clear();
        stockModel.clear();
        stockProdType.clear();
        stockCount.clear();
        for(StockBalance b : stockList) {
            stockBrand.add(b.getStockBrand());
            stockModel.add(b.getStockModel());
            stockCount.add(b.getStockCount());
            stockProdType.add(b.getStockProdType());
        }
    }

    public static void saveFailureCache(String userJson) {
        failureList = JsonUtils.parseFailuresJson(userJson);
        failureBrand.clear();
        failureModel.clear();
        failureProdType.clear();
        failureCount.clear();
        for(Failures b : failureList) {
            failureBrand.add(b.getFailureBrand());
            failureModel.add(b.getFailureModel());
            failureCount.add(b.getFailureCount());
            failureProdType.add(b.getFailureProdType());

        }
    }

    public static void saveCaseLogCache(String userJson) {
        caseLogList = JsonUtils.parseCaseLogJson(userJson);
        caseLogCount.clear();
        for(CaseLog b : caseLogList) {
            caseLogCount.add(b.getCaseLogCount());
        }
    }

    public static void savePendingQuotationCache(String userJson) {
        pendingQuoteList = JsonUtils.parseQuotationJson(userJson);
        pendingQuoteCount.clear();
        for(Quotation b : pendingQuoteList) {
            pendingQuoteCount.add(b.getQuotationCount());
        }
    }

    public static void saveServiceRequestCache(String userJson) {
        srList = JsonUtils.parseServiceRequestJson(userJson);
        srCount.clear();
        for(ServiceRequest b : srList) {
            srCount.add(b.getSrCount());
        }
    }

    public static void saveUsedProdCache(String userJson) {
        usedProductList = JsonUtils.parseUsedProductsJson(userJson);
        usedProdPartNo.clear();
        usedProdQty.clear();
        usedProdRank.clear();
        for(UsedProduct b : usedProductList) {
            usedProdQty.add(b.getQuantity());
            usedProdRank.add(b.getRank());
            usedProdPartNo.add(b.getPartNo());
        }
    }

    public static void saveServiceRequestListCache(JSONObject jsonRes) {
        serviceReqList = JsonUtils.parseServiceRequestListJson(jsonRes);
        srId.clear(); srNo.clear(); srStatus.clear(); srConsumerName.clear(); srCreatedOn.clear();
        for(ServiceRequestList b : serviceReqList) {
            srId.add(b.getSrId()); srNo.add(b.getSrNo()); srStatus.add(b.getStatus());
            srCreatedOn.add(b.getCreated_on()); srConsumerName.add(b.getConsumer_name());
        }
    }

    public static void saveServiceRequestDetailCache(JSONObject jsonRes) {
        ServiceRequestDetails serviceReqDet = JsonUtils.parseServiceRequestDetail(jsonRes);
        srDetSrId.clear(); srDetSrNo.clear(); srDetStatus.clear(); srDetCreatedOn.clear(); srDetFailureDesc.clear();
        srDetCreatedBy.clear(); srDetWarrStatus.clear(); srDetRetCount.clear(); srDetUserId.clear(); srDetFirstName.clear();
        srDetLastName.clear(); srDetCity.clear(); srDetCountryCode.clear(); srDetMobile.clear(); srDetIcNo.clear();
        srDetAddrLine1.clear(); srDetState.clear(); srDetPostalCode.clear(); srDetOtherDesc.clear(); srDetRemarks.clear();
        srDetProductStatus.clear(); srDetCompanyName.clear(); srDetBrandName.clear(); srDetModelName.clear();
        srDetSerialNo.clear(); srDetProdType.clear(); srDetEwCount.clear();
        //  for(ServiceRequestList b : serviceReqList) {
            srDetSrId.add(serviceReqDet.getSr_id()); srDetSrNo.add(serviceReqDet.getSr_no());
            srDetStatus.add(serviceReqDet.getStatus()); srDetCreatedOn.add(serviceReqDet.getCreated_on());
            srDetFailureDesc.add(serviceReqDet.getFailure_desc()); srDetCreatedBy.add(serviceReqDet.getCreated_by());
            srDetWarrStatus.add(serviceReqDet.getWarranty_status()); srDetRetCount.add(serviceReqDet.getReturn_count());
            srDetUserId.add(serviceReqDet.getUser_id()); srDetFirstName.add(serviceReqDet.getFirst_name());
            srDetLastName.add(serviceReqDet.getLast_name()); srDetCity.add(serviceReqDet.getCity());
            srDetCountryCode.add(serviceReqDet.getCountry_code()); srDetMobile.add(serviceReqDet.getMobile());
            srDetIcNo.add(serviceReqDet.getIc_no()); srDetAddrLine1.add(serviceReqDet.getAddress_line1());
            srDetState.add(serviceReqDet.getState()); srDetPostalCode.add(serviceReqDet.getPostal_code());
            srDetOtherDesc.add(serviceReqDet.getOther_desc()); srDetRemarks.add(serviceReqDet.getRemarks());
            srDetProductStatus.add(serviceReqDet.getProduct_status()); srDetCompanyName.add(serviceReqDet.getCompany_name());
            srDetBrandName.add(serviceReqDet.getBrand_name()); srDetModelName.add(serviceReqDet.getModel_name());
            srDetSerialNo.add(serviceReqDet.getSerial_no()); srDetProdType.add(serviceReqDet.getProduct_type());
            srDetEwCount.add(serviceReqDet.getEw_count());
        // }
    }

}
