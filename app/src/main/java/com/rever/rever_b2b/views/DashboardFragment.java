package com.rever.rever_b2b.views;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rever.rever_b2b.R;
import com.rever.rever_b2b.utils.MasterCache;
import com.rever.rever_b2b.utils.NetUtils;

import java.util.ArrayList;


/**
 * Created by Matheswari on 3/24/2016.
 */
public class DashboardFragment extends Fragment {
    private View rootView;
    private TableLayout tblStockBal, tblFailure;
    private TextView txtCaseLogCount, txtServReqCount, txtPendQuoteCount;
    private TableRow.LayoutParams stockParam, failureParam, failureLineParam, stockLineParam;
    private RelativeLayout.LayoutParams alertParam;
    private int width, height;
    private LinearLayout linearGraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews();
        initParams();

        new GetStockBalTask().execute("Stock");
        new GetStockBalTask().execute("CaseLog");
        new GetStockBalTask().execute("Failures");
        new GetStockBalTask().execute("Quotation");
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
        linearGraph = (LinearLayout)rootView.findViewById(R.id.linearGraphInDashboard);
        tblStockBal.setVisibility(View.VISIBLE);
    }

    private void initParams(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            Toast.makeText(getActivity(), "Xlarge screen", Toast.LENGTH_LONG).show();
            int swidth = width - dpToPx(160);
            alertParam = new RelativeLayout.LayoutParams(dpToPx(30),dpToPx(30));
            stockParam = new TableRow.LayoutParams(swidth/5,dpToPx(50));
            failureParam = new TableRow.LayoutParams((swidth/2)/4,dpToPx(50));
            failureLineParam = new TableRow.LayoutParams(swidth/2,dpToPx(1));
            failureLineParam.span = 4;
            stockLineParam = new TableRow.LayoutParams(swidth,dpToPx(1));
            stockLineParam.span = 5;

        } else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Toast.makeText(getActivity(), "Large screen", Toast.LENGTH_LONG).show();
            int swidth = width - dpToPx(135);
            Log.i("myLog","Swidth:"+swidth);
            alertParam = new RelativeLayout.LayoutParams(dpToPx(30),dpToPx(30));
            stockParam = new TableRow.LayoutParams(swidth/5,dpToPx(45));
            failureParam = new TableRow.LayoutParams((swidth/2)/4,dpToPx(45));
            failureLineParam = new TableRow.LayoutParams(swidth/2,dpToPx(1));
            failureLineParam.span = 4;
            stockLineParam = new TableRow.LayoutParams(swidth,dpToPx(1));
            stockLineParam.span = 5;

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            Toast.makeText(getActivity(), "Normal sized screen" , Toast.LENGTH_LONG).show();
            alertParam = new RelativeLayout.LayoutParams(dpToPx(30),dpToPx(30));
            int swidth = width - dpToPx(100);
            stockParam = new TableRow.LayoutParams(swidth,dpToPx(50));
            failureParam = new TableRow.LayoutParams((swidth/2)/4,dpToPx(40));
            failureLineParam = new TableRow.LayoutParams(swidth/2,dpToPx(1));
            failureLineParam.span = 4;
            stockLineParam = new TableRow.LayoutParams(swidth,dpToPx(1));
            stockLineParam.span = 4;

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
                url = NetUtils.TOP5_PROD_URL;
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
                displayLineGraph();

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
            RelativeLayout rel = new RelativeLayout(getActivity());
           // txtAlert.setBackgroundResource(R.drawable.alert);
            ImageView img = new ImageView(getActivity());
            img.setImageResource(R.drawable.alert);
          //  rel.addView(img, alertParam);
            tr.addView(rel, stockParam);
            TextView txtProdType = new TextView(getActivity());
            txtProdType.setText(MasterCache.stockProdType.get(start));
            txtProdType.setGravity(Gravity.CENTER);
            tr.addView(txtProdType, stockParam);
            TextView txtBrand = new TextView(getActivity());
            txtBrand.setText(MasterCache.stockBrand.get(start));
            txtBrand.setGravity(Gravity.CENTER);
            tr.addView(txtBrand, stockParam);
            TextView txtModel = new TextView(getActivity());
            txtModel.setText(MasterCache.stockModel.get(start));
            txtModel.setGravity(Gravity.CENTER);
            tr.addView(txtModel, stockParam);
            TextView txtStockBal= new TextView(getActivity());
            txtStockBal.setText(String.valueOf(MasterCache.stockCount.get(start)));
            txtStockBal.setGravity(Gravity.CENTER);
            tr.addView(txtStockBal, stockParam);
            View v = new View(getActivity());
            v.setBackgroundColor(Color.LTGRAY);
            TableRow trLine = new TableRow(getActivity());
            trLine.addView(v, stockLineParam);
            tblStockBal.addView(tr);
            tblStockBal.addView(trLine);

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
            tr.addView(txtProdType, failureParam);
            TextView txtCount = new TextView(getActivity());
            txtCount.setText(String.valueOf(MasterCache.failureCount.get(start)));
            txtCount.setGravity(Gravity.CENTER);
            tr.addView(txtCount, failureParam);
            View v = new View(getActivity());
            v.setBackgroundColor(Color.LTGRAY);
            TableRow trLine = new TableRow(getActivity());
            trLine.addView(v, failureLineParam);
            tblFailure.addView(tr);
            tblFailure.addView(trLine);
        }
    }


    private void displayLineGraph() {
        linearGraph.removeAllViews();
        LineChart chart = new LineChart(getActivity());
        chart.setDescription("");
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextSize(13);
        xAxis.setAxisLineWidth(1);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(5, true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextSize(13);
        leftAxis.setAxisLineWidth(1);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setLabelCount(5, true);
        rightAxis.setDrawAxisLine(true);
        rightAxis.setAxisLineWidth(1);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(true);
        chart.setData(generateDataLine());
        chart.animateX(750);

        Legend l = chart.getLegend();
        l.setEnabled(true);
        linearGraph.addView(chart, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("myLog","line chart Value selected at "+dataSetIndex+"  "+e.getVal()+" h:"+e.getXIndex());

            }

            @Override
            public void onNothingSelected() {
                Log.i("myLog","Line graph onNothingSelected ");
            }
        });
    }

    private LineData generateDataLine() {
        int cnt = MasterCache.usedProdQty.size();
        ArrayList<Entry> e1 = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            e1.add(new Entry(MasterCache.usedProdQty.get(i), i));
        }
        LineDataSet d1 = new LineDataSet(e1, " Top 5 Plans ");
        d1.setLineWidth(1f);
        d1.setColor(ContextCompat.getColor(getActivity(), R.color.blue_txt_color));
       // d1.setCircleSize(5f);
        d1.setCircleRadius(8f);

        d1.setDrawCircles(true);
        d1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.blue_txt_color));
        d1.setHighLightColor(Color.rgb(239, 122, 145));
        d1.setDrawValues(false);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
       return new LineData(MasterCache.usedProdPartNo, sets);

    }
}
