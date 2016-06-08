package com.rever.rever_b2b.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private TextView txtServReqDet, txtProdDet;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_request, container, false);
        initViews();
        readAllServiceRequest();
      //  ListAdapter adapter = new SimpleAdapter(getContext().getApplicationContext(), MasterCache.jo, R.layout.list_item, new String[]
        //        {vmodel_name, vproduct_type, vserial_no, vconsumer_name},
          //      new int[] {R.id.model_name, R.id.product_type, R.id.serial_no, R.id.consumer_name });
        //listView.setAdapter(adapter);

        return rootView;
    }

    public void initViews(){
        linearDetails = (LinearLayout)rootView.findViewById(R.id.linearDetailInServReq);
        txtServReqDet = (TextView)rootView.findViewById(R.id.txtServReqDetInServReq);
        txtProdDet = (TextView)rootView.findViewById(R.id.txtProdDetInServReq);
        listView = (ListView)rootView.findViewById(R.id.listInServReq);
        txtServReqDet.setOnClickListener(this);
        txtProdDet.setOnClickListener(this);
    }

    public void readAllServiceRequest(){
        // HTTP POST
        String url = NetUtils.HOST+ NetUtils.SR_LIST;
        Log.i("myLog","SamplejsonPost url:"+url);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page_no", "1");
            jsonObject.put("page_count", "5");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // do something...
                    Log.i("myLog", "Success Response");
                    MasterCache.saveServiceRequestListCache(response);
                    CustomList listAdapter = new CustomList(getActivity(),MasterCache.srNo, MasterCache.srStatus, MasterCache.srCreatedOn, MasterCache.srConsumerName);
                    listView.setAdapter(listAdapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // do something...
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
    }

    public void showProductDetails(){
        linearDetails.removeAllViews();
        LayoutInflater li = LayoutInflater.from(getActivity());
        View layout = li.inflate(R.layout.service_prod_det, null, false);
        linearDetails.addView(layout);
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
            txtStatus.setText(status.get(position));
            txtConsumer.setText(consumer.get(position));
            txtCreatedOn.setText(createdOn.get(position));
            txtSrNo.setText(srNo.get(position));
            return  listViewItem;
        }
    }
}
