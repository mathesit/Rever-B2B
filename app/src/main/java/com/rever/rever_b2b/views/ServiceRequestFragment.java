package com.rever.rever_b2b.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rever.rever_b2b.R;
import com.rever.rever_b2b.utils.MasterCache;
import com.rever.rever_b2b.utils.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Matheswari on 5/13/2016.
 */
public class ServiceRequestFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private LinearLayout linearDetails;
    private TextView txtServReqDet, txtProdDet, txtServNo, txtCreatedDate, txtCreatedBy, txtFailureDesc, txtSerialNo, txtBrand,
            txtProdType, txtModel, txtWarrStatus, txtPastServReturns, txtEmail, txtCountry, txtFirstName, txtLastName, txtCity,
            txtAddress, txtState, txtPostalCode, txtIcPassportNo, txtMobile, txtServCentre, txtFailDesc, txtRemarks, txtStatus;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_request, container, false);
        initViews();
        readAllServiceRequest();
        return rootView;
    }

    public void initViews(){
        linearDetails = (LinearLayout)rootView.findViewById(R.id.linearDetailInServReq);
        txtServReqDet = (TextView)rootView.findViewById(R.id.txtServReqDetInServReq);
        txtProdDet = (TextView)rootView.findViewById(R.id.txtProdDetInServReq);
        listView = (ListView)rootView.findViewById(R.id.listInServReq);
        txtServReqDet.setOnClickListener(this);
        txtProdDet.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sr_id = MasterCache.srId.get(position);
                Log.i("myLog","sr_id:"+sr_id);
                showServReqDetails();
                getServReqDetails(sr_id);
            }
        });
    }

    public void readAllServiceRequest(){
        String url = NetUtils.HOST+ NetUtils.SR_LIST;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page_no", "1");
            jsonObject.put("page_count", "5");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("myLog", "Success Response");
                    MasterCache.saveServiceRequestListCache(response);
                    CustomList listAdapter = new CustomList(getActivity(),MasterCache.srNo, MasterCache.srStatus, MasterCache.srCreatedOn, MasterCache.srConsumerName);
                    listView.setAdapter(listAdapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("myLog","Error Response");
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", ReverApplication.getSessionToken());
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txtServReqDetInServReq:
                showServReqDetails();
                break;

            case R.id.txtProdDetInServReq:
                showProductDetails();
                break;
        }
    }

    public void showServReqDetails(){
        linearDetails.removeAllViews();
        LayoutInflater li = LayoutInflater.from(getActivity());
        View layout = li.inflate(R.layout.service_req_det, null, false);
        linearDetails.addView(layout);
        initServReqViews(layout);
    }

    public void showProductDetails(){
        linearDetails.removeAllViews();
        LayoutInflater li = LayoutInflater.from(getActivity());
        View layout = li.inflate(R.layout.service_prod_det, null, false);
        linearDetails.addView(layout);
    }

    public void initServReqViews(View v){
        txtServNo = (TextView)v.findViewById(R.id.txtServNoInServReqDet);
        txtCreatedBy = (TextView)v.findViewById(R.id.txtCreatedByInServReqDet);
        txtCreatedDate = (TextView)v.findViewById(R.id.txtCreatedDateInServReqDet);
        txtFailureDesc = (TextView)v.findViewById(R.id.txtFailureDescInServReqDet);
        txtSerialNo = (TextView)v.findViewById(R.id.txtSerialNoInServReqDet);
        txtBrand = (TextView)v.findViewById(R.id.txtBrandInServReqDet);
        txtProdType = (TextView)v.findViewById(R.id.txtProdTypeInServReqDet);
        txtModel = (TextView)v.findViewById(R.id.txtModelInServReqDet);
        txtWarrStatus = (TextView)v.findViewById(R.id.txtWarrStatusInServReqDet);
        txtPastServReturns = (TextView)v.findViewById(R.id.txtPastServRetInServReqDet);
        txtEmail = (TextView)v.findViewById(R.id.txtEmailInServReqDet);
        txtCountry = (TextView)v.findViewById(R.id.txtCountryInServReqDet);
        txtFirstName = (TextView)v.findViewById(R.id.txtFirstNameInServReqDet);
        txtLastName = (TextView)v.findViewById(R.id.txtLastNameInServReqDet);
        txtCity = (TextView)v.findViewById(R.id.txtCityInServReqDet);
        txtState = (TextView)v.findViewById(R.id.txtStateInServReqDet);
        txtAddress = (TextView)v.findViewById(R.id.txtAddressInServReqDet);
        txtPostalCode = (TextView)v.findViewById(R.id.txtPostalCodeInServReqDet);
        txtIcPassportNo = (TextView)v.findViewById(R.id.txtIcPassportInServReqDet);
        txtMobile = (TextView)v.findViewById(R.id.txtMobileInServReqDet);
        txtServCentre = (TextView)v.findViewById(R.id.txtServCentresInServReqDet);
        txtFailDesc = (TextView)v.findViewById(R.id.txtFailDescInServReqDet);
        txtRemarks = (TextView)v.findViewById(R.id.txtRemarksInServReqDet);
        txtStatus = (TextView)v.findViewById(R.id.txtStatusInServReqDet);
    }

    public class CustomList extends ArrayAdapter<String> {
        private List<String> srNo, status, createdOn, consumer;
        private Activity context;

        public CustomList(Activity context, List<String> srNo, List<String> status, List<String> createdOn, List<String> consumer) {
            super(context, R.layout.list_item, srNo);
            this.context = context;
            this.srNo = srNo;
            this.status = status;
            this.createdOn = createdOn;
            this.consumer = consumer;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_item, null, true);
            TextView txtStatus = (TextView) listViewItem.findViewById(R.id.serial_no);
            TextView txtConsumer = (TextView) listViewItem.findViewById(R.id.consumer_name);
            TextView txtCreatedOn = (TextView)listViewItem.findViewById(R.id.product_type);
            TextView txtSrNo = (TextView)listViewItem.findViewById(R.id.model_name);
            txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alert_small, 0, 0, 0);
            txtCreatedOn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.calendar_small, 0, 0, 0);
            txtStatus.setText(status.get(position));
            txtConsumer.setText(consumer.get(position));
            txtCreatedOn.setText(createdOn.get(position));
            txtSrNo.setText(srNo.get(position));
            return  listViewItem;
        }
    }

    public void getServReqDetails(String sr_id){
        String servReqUrl = String.format(NetUtils.SR_INFO, sr_id);
        String url = NetUtils.HOST + servReqUrl;
        Log.i("myLog"," loadServReqDetails url : "+url);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("myLog", "loadServReqDetails Success Response");
                    MasterCache.saveServiceRequestDetailCache(response);
                    loadServReqDet();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("myLog","loadServReqDetails Error Response");
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", ReverApplication.getSessionToken());
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
    }

    private void loadServReqDet(){
        txtServNo.setText(MasterCache.srDetSrNo.get(0));
        txtCreatedBy.setText(MasterCache.srDetCreatedBy.get(0));
        txtCreatedDate.setText(MasterCache.srDetCreatedOn.get(0));
        txtFailureDesc.setText(MasterCache.srDetFailureDesc.get(0));
        txtSerialNo.setText(MasterCache.srDetSerialNo.get(0));
        txtBrand.setText(MasterCache.srDetBrandName.get(0));
        txtProdType.setText(MasterCache.srDetProdType.get(0));
        txtModel.setText(MasterCache.srDetModelName.get(0));
        txtWarrStatus.setText(MasterCache.srDetWarrStatus.get(0));
        txtPastServReturns.setText(MasterCache.srDetRetCount.get(0));
       // txtEmail.setText(MasterCache.srDetE.get(0));
        txtCountry.setText(MasterCache.srDetCountryCode.get(0));
        txtFirstName.setText(MasterCache.srDetFirstName.get(0));
        txtLastName.setText(MasterCache.srDetLastName.get(0));
        txtCity.setText(MasterCache.srDetCity.get(0));
        txtState.setText(MasterCache.srDetState.get(0));
        txtAddress.setText(MasterCache.srDetAddrLine1.get(0));
        txtPostalCode.setText(MasterCache.srDetPostalCode.get(0));
        txtIcPassportNo.setText(MasterCache.srDetIcNo.get(0));
        txtMobile.setText(MasterCache.srDetMobile.get(0));
        txtRemarks.setText(MasterCache.srDetRemarks.get(0));
        txtStatus.setText(MasterCache.srDetStatus.get(0));
    }
}
