package com.rever.rever_b2b.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NetUtils {
    public static final String HOST = "http://54.179.167.160:8080/Yarraa/";
    public static final String PAYMENT_API_URL = "http://staging.2mpayment.com/CCPG/WebServicePayment";

    public static final String STOCK_URL = "service-centers/stockBalance";
    public static final String SERV_REQ_URL = "service-centers/srcount";
    public static final String CASE_LOG_URL = "service-centers/caselogcount";
    public static final String TOP_FAILURE_URL = "sr/topFailures";
    public static final String TOP5_PROD_URL = "service-centers/top5usedproducts";
    public static final String PENDING_QUOT_URL = "quotations/pendingcount";

    public static final String SR_LIST = "sr/list";
    public static final String SR_INFO = "sr/details/%s";
    public static final String PRODUCT_INFO = "sr/product/%s";

    public static final String LOGIN_URL = "users/login";
    public static final String RESET_URL ="users/reset";
    public static final String CHANGE_PWD_URL ="users/change_pwd";
    public static final String COUNTRIES = "countries";
    public static final String USERS = "users", GCM_REGISTER="push/register";
    public static final String BRANDS = "brands", SERVICE_CENTER_SEARCH="service-centers/search";
    public static final String CURRENCIES="currencies", WARRANTIES="warranties", PRODUCTS = "products";
    public static  final String apiKey="AIzaSyBIVyxKDbvFBFem7EwRWuIc3-ojUsYz9es";

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        } else {
            Toast.makeText(context, "WARNING! \n NO INTERNET CONNECTION.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static String sendRequest(Activity activity, String url, String data) {
        Log.i("myLog","sendRequest url:"+url);
        String method = (data !=null && data.trim().length() > 0)?"POST":"GET";
        Log.i("myLog","method:"+method);
        if(NetUtils.hasNetwork(activity)) {
            try {
                Log.i("myLog", "HOST+url :" + HOST + url);
                URL loginUrl = new URL(HOST+url);
                if(data!=null && data.equalsIgnoreCase("payment")){
                    loginUrl = new URL(PAYMENT_API_URL+url);
                    method = "GET";
                }
                Log.i("myLog", "URL:" + loginUrl);
                HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
                conn.setRequestMethod(method);
                conn.setConnectTimeout(25000);
                if(method.equals("POST")) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                }
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
                if(MasterCache.userSessionToken.size()>0){
                    String auth = MasterCache.userSessionToken.get(MasterCache.userId.get(0));
                    conn.setRequestProperty("Authorization", auth);
                }else{
                    conn.setRequestProperty("Authorization","asFgTIEE");
                }
                conn.connect();
                if(method.equals("POST")) {
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeUTF(URLEncoder.encode(data, "UTF-8"));
                    dos.flush();
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuffer responseBuffer = new StringBuffer();
                while((output = br.readLine())!=null) {
                    responseBuffer.append(output);
                }
                conn.disconnect();
                return responseBuffer.toString();
            }catch (Exception e) {
                    e.printStackTrace();
            }
        }
        return null;
    }

    public static String sendCommand(Context activity, String url, String data, String method) {
        Log.i("myLog","Data:"+data);
        String response = null;
        InputStream is;
     //   String method = (data !=null && data.trim().length() > 0)?"POST":"GET";
        if(NetUtils.hasNetwork(activity)) {
            try {
                URL loginUrl = new URL(HOST+url);
                Log.i("myLog", "url:" + HOST + url);
                HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
                conn.setRequestMethod(method);
                conn.setConnectTimeout(25000);
                if(method.equals("POST")) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                }
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
                if(MasterCache.userSessionToken.size()>0){
                    String auth = MasterCache.userSessionToken.get(MasterCache.userId.get(0));
                    conn.setRequestProperty("Authorization", auth);
                    Log.i("myLog","if Authoriztion:"+auth);
                }else{
                    Log.i("myLog","else Authoriztion:"+"asFgTIEE");
                    conn.setRequestProperty("Authorization","asFgTIEE");
                }
                conn.connect();
                if(method.equals("POST")) {
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    wr.close();
                }
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    is = conn.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    response = sb.toString();
                }
                conn.disconnect();
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        result.append("{");
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append(",");
            result.append("\"");
            result.append(entry.getKey());
           // result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("\"");
            result.append(":");
            result.append("\"");
            result.append(entry.getValue());
          //  result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("\"");
        }
        result.append("}");
        return result.toString();
    }

    public static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        result.append("?");
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(entry.getKey());
            // result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            //result.append(entry.getValue());
            result.append(Uri.encode(entry.getValue()));
         //   result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
