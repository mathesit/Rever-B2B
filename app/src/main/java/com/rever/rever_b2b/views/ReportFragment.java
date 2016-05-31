package com.rever.rever_b2b.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rever.rever_b2b.R;
import com.rever.rever_b2b.downloader.FileDownloader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Matheswari on 4/5/2016.
 */
public class ReportFragment extends Fragment {
    private View rootView;
    private LinearLayout linearTitle, linearDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initViews();
        return rootView;
    }

    private void initViews(){
        linearTitle = (LinearLayout)rootView.findViewById(R.id.linearTitlesInReports);
        linearDetail = (LinearLayout)rootView.findViewById(R.id.linearDetailInReports);

        TextView txtGen= (TextView)rootView.findViewById(R.id.txtGenReportInReports);
        txtGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetPdf().execute();
            }
        });
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }


    public class GetPdf extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            generateReport();
            return "test";
        }

        @Override
        protected void onPostExecute(String option)
        {
            showPdf();
        }
    }

        public void generateReport(){
        Log.i("myLog","generateReport");
        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, "Read.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileDownloader.DownloadFile("http://54.179.167.160:8080/Yarraa/reports/exportCaseLogReportPDF", file);
            Log.i("myLog", "generateReport aft3r download");


    }

    public void showPdf()
    {
        Log.i("myLog","showpdf");
        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
      //  List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

}
