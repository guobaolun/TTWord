package com.snowy.ttword.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.ttword.R;
import com.snowy.ttword.adapter.PhoneticListAdapter;


/**
 * 只为占位
 */
public class EmptyFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       View view = inflater.inflate(R.layout.fragment_study, container,false);
        return view;
    }





}