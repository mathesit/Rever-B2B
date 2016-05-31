package com.rever.rever_b2b.views;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rever.rever_b2b.R;
import com.rever.rever_b2b.utils.MasterCache;
import com.rever.rever_b2b.utils.NetUtils;


/**
 * Created by Matheswari on 3/24/2016.
 */
public class DashboardFragment extends Fragment {
    private View rootView;
    private TableLayout tblStockBal, tblFailure;
    private TextView txtCaseLogCount, txtServReqCount, txtPendQuoteCount;
    private TableRow.LayoutParams stockParam, failureParam;
    private int width, height;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews();
        initParams();
        new GetStockBalTask().execute("Stock");
        new GetStockBalTask().execute("CaseLog");
        new GetStockBalTask().execute("Failures");
        new GetStockBalTask().execute("Product");
        new GetStockBalTask().execute("ServiceReq");
        return rootView;
    }

    private void initViews(){
        tblStockBal = (TableLayout) rootView.findViewById(R.id.tblStockBalInDashboard);
        txtCaseLogCount = (TextView)rootView.findViewById(R.id.txtCaseLogsCountInDashboard);
        txtPendQuoteCount = (TextView)rootView.findViewById(R.id.txtPendQuotCountInDashboard);
        txtServReqCount = (TextView)rootView.findViewById(R.id.txtServReqCountInDashboard);
        tblFailure = (TableLayout)rootView.findViewById(R.id.tblFailuresInDashboard);
        tblStockBal.setVisibility(View.VISIBLE);
    }

    private void initParams(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            Toast.makeText(getActivity(), "Xlarge screen", Toast.LENGTH_LONG).show();
            int swidth = (width *80)/100;
            stockParam = new TableRow.LayoutParams(swidth/5,dpToPx(50));
            failureParam = new TableRow.LayoutParams((swidth/2)/5,dpToPx(50));

        } else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Toast.makeText(getActivity(), "Large screen", Toast.LENGTH_LONG).show();
            int swidth = width - dpToPx(150);
            Log.i("myLog","Swidth:"+swidth);
            stockParam = new TableRow.LayoutParams(swidth/5,dpToPx(45));
            failureParam = new TableRow.LayoutParams((swidth/2)/5,dpToPx(45));

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            Toast.makeText(getActivity(), "Normal sized screen" , Toast.LENGTH_LONG).show();
            int swidth = (width *75)/100;
            stockParam = new TableRow.LayoutParams(swidth,dpToPx(50));
            failureParam = new TableRow.LayoutParams((swidth/2)/5,dpToPx(40));

        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public class GetStockBalTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String option =params[0];
            String url ="";
            if (option.equalsIgnoreCase("Stock")) {
                url= NetUtils.STOCK_URL;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.saveStockBalCache(resp);

            } else if (option.equalsIgnoreCase("CaseLog")) {
                url=NetUtils.CASE_LOG_URL;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.saveCaseLogCache(resp);

            } else if (option.equalsIgnoreCase("Failures")) {
                url=NetUtils.TOP_FAILURE_URL;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.saveFailureCache(resp);

            } else if (option.equalsIgnoreCase("Product")) {
                url=NetUtils.TOP5_PROD_URL;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.saveUsedProdCache(resp);

            } else if (option.equalsIgnoreCase("Quotation")) {
                int compId = MasterCache.companyId.get(MasterCache.userId.get(0));
                url=NetUtils.PENDING_QUOT_URL+compId;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.savePendingQuotationCache(resp);

            } else if (option.equalsIgnoreCase("ServiceReq")) {
                url=NetUtils.SERV_REQ_URL;
                String resp = NetUtils.sendRequest(getActivity(),url,null);
                Log.i("myLog",option+" Response:"+resp);
                MasterCache.saveServiceRequestCache(resp);
            }
            return option;
        }

        @Override
        protected void onPostExecute(String option)
        {
            Log.i("myLog","Option:"+option);
            if (option.equalsIgnoreCase("Stock")){
                displayStockBal();

            } else if(option.equalsIgnoreCase("CaseLog")){
                if(MasterCache.caseLogCount.size()>0) {
                    int caseLog = MasterCache.caseLogCount.get(0);
                    txtCaseLogCount.setText(String.valueOf(caseLog));
                }

            } else if(option.equalsIgnoreCase("Failures")){
                displayFailures();

            } else if(option.equalsIgnoreCase("Product")){


            } else if(option.equalsIgnoreCase("Quotation")){
                if(MasterCache.pendingQuoteCount.size()>0) {
                    int quoteCount = MasterCache.pendingQuoteCount.get(0);
                    txtPendQuoteCount.setText(String.valueOf(quoteCount));
                }

            } else if(option.equalsIgnoreCase("ServiceReq")){
                if(MasterCache.srCount.size()>0) {
                    int srCount = MasterCache.srCount.get(0);
                    txtServReqCount.setText(String.valueOf(srCount));
                }
            }
        }

    }

    public void displayStockBal(){
        Log.i("myLog","displayStockBal");
        int size= MasterCache.stockProdType.size();
        if(size>=5)
            size= 5;
        Log.i("myLog","Size:"+MasterCache.stockProdType.size());
        for(int start=0; start<size; start++){
            Log.i("myLog", "displayStockBal index:" + start);
            TableRow tr= new TableRow(getActivity());
            TextView txtAlert = new TextView(getActivity());
            tr.addView(txtAlert, stockParam);
            Log.i("myLog", "alert");
            TextView txtProdType = new TextView(getActivity());
            txtProdType.setText(MasterCache.stockProdType.get(start));
            txtProdType.setGravity(Gravity.CENTER);
            txtProdType.setTextColor(Color.BLACK);
            txtProdType.setTextSize(R.dimen.textsize_normal);
            tr.addView(txtProdType, stockParam);
            Log.i("myLog", "prodtype:" + MasterCache.stockProdType.get(start));
            TextView txtBrand = new TextView(getActivity());
            txtBrand.setText(MasterCache.stockBrand.get(start));
            txtBrand.setGravity(Gravity.CENTER);
            tr.addView(txtBrand, stockParam);
            Log.i("myLog", "brand:"+MasterCache.stockBrand.get(start));
            TextView txtModel = new TextView(getActivity());
            txtModel.setText(MasterCache.stockModel.get(start));
            txtModel.setGravity(Gravity.CENTER);
            tr.addView(txtModel, stockParam);
            Log.i("myLog", "model:"+MasterCache.stockModel.get(start));
            TextView txtStockBal= new TextView(getActivity());
            Log.i("myLog","count:"+MasterCache.stockCount.get(start));
          //   txtStockBal.setText(MasterCache.stockCount.get(start));
            //txtStockBal.setGravity(Gravity.CENTER);
            // tr.addView(txtStockBal);
            tblStockBal.addView(tr);
            Log.i("myLog","after add to tbl");
        }
    }

    public void displayFailures(){
        int size = MasterCache.failureBrand.size();
        if(size>=5){
            size=5;
        }
        for(int start=0; start<size; start++){
            TableRow tr= new TableRow(getActivity());
            TextView txtBrand = new TextView(getActivity());
            txtBrand.setText(MasterCache.failureBrand.get(start));
            txtBrand.setGravity(Gravity.CENTER);
            tr.addView(txtBrand, failureParam);
            TextView txtModel = new TextView(getActivity());
            txtModel.setText(MasterCache.failureModel.get(start));
            txtModel.setGravity(Gravity.CENTER);
            tr.addView(txtModel, failureParam);
            TextView txtProdType = new TextView(getActivity());
            txtProdType.setText(MasterCache.failureProdType.get(start));
            txtProdType.setGravity(Gravity.CENTER);
            txtProdType.setTextSize(R.dimen.textsize_normal);
            tr.addView(txtProdType, failureParam);
          //  TextView txtCount = new TextView(getActivity());
           // txtCount.setText(MasterCache.failureCount.get(start));
            //txtCount.setGravity(Gravity.CENTER);
            //txtCount.setTextSize(R.dimen.textsize_normal);
            //tr.addView(txtCount, failureParam);
            tblFailure.addView(tr);
        }
    }
}
