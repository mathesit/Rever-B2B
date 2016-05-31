package com.rever.rever_b2b.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rever.rever_b2b.R;


/**
 * Created by Matheswari on 5/13/2016.
 */
public class ServiceRequestFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_screen, container, false);
        return rootView;
    }
}
