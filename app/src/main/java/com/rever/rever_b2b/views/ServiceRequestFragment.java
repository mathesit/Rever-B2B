package com.rever.rever_b2b.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rever.rever_b2b.R;
import com.rever.rever_b2b.model.ServiceRequest;
import com.rever.rever_b2b.model.ServiceRequestList;
import com.rever.rever_b2b.utils.MasterCache;
import com.rever.rever_b2b.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.rever.rever_b2b.model.ServiceRequest.*;


/**
 * Created by Matheswari on 5/13/2016.
 */
public class ServiceRequestFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_screen, container, false);
        readAllServiceRequest();
        return rootView;
    }


    public void readAllServiceRequest(){

        String url = NetUtils.HOST+ NetUtils.SR_LIST;
        Log.i("myLog", "url:" + url);
        Map<String, String> params = new HashMap();
        params.put("page_no", "1");
        params.put("page_count", "5");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.i("myLog","Response");
                MasterCache.saveServiceRequestListCache(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.i("myLog","Error Response");
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonRequest);
    }

    public void readServiceRequest(){

        String url = NetUtils.HOST+ NetUtils.SR_LIST;
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        MasterCache.saveServiceRequestListCache(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonRequest);

    }
}
