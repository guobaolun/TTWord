package com.snowy.ttword.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.snowy.common.dialog.BottomSlideDialog;
import com.snowy.ttword.R;
import com.snowy.widget.ItemView;

/**
 * @author guobaolun
 */
public class MeFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_study, container,false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPortraitDialog();
            }
        });
        return view;
    }


    public void showPortraitDialog() {
        View view = View.inflate(getActivity(), R.layout.dialog_logout, null);


        BottomSlideDialog  mBottomSlideDialog = new BottomSlideDialog(getActivity(), R.style.ActionSheetDialogStyle);
        mBottomSlideDialog.setContentView(view);
        mBottomSlideDialog.show();
    }





}