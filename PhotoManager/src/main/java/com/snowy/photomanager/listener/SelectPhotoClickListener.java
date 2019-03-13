package com.snowy.photomanager.listener;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.snowy.photomanager.adapter.SelectManyPhotoGridAdapter;


public class SelectPhotoClickListener implements View.OnClickListener {

    private Context context;
    private int position;
    private SelectManyPhotoGridAdapter adapter;
    private Button ok_bt;


    public SelectPhotoClickListener(Context context, Button ok_bt, SelectManyPhotoGridAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        this.ok_bt = ok_bt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public void onClick(View v) {

        CheckBox checkBox = (CheckBox) v;
        boolean isChecked = checkBox.isChecked();

        if (isChecked && adapter.getSelectList().size() >= 9) {
            Toast.makeText(context, "最多选择9张照片", Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return;
        }

        adapter.getImageList().get(position).setChecked(isChecked);

        if (isChecked) {
            adapter.getSelectList().add(adapter.getImageList().get(position).getPath());
            adapter.getImageList().get(position).setNum(adapter.getSelectList().size());
            adapter.getSelectPositionList().add(position);
            checkBox.setText(String.valueOf(adapter.getSelectList().size()));
        } else {
            checkBox.setText("");
            adapter.getImageList().get(position).setNum(0);
            adapter.getSelectList().remove(adapter.getImageList().get(position).getPath());
            adapter.getSelectPositionList().remove(Integer.valueOf(position));
            for (int i : adapter.getSelectPositionList()) {

                String path = adapter.getImageList().get(i).getPath();
                adapter.getImageList().get(i).setNum(adapter.getSelectList().indexOf(path) + 1);
            }
        }
        ok_bt.setText("确定(" + adapter.getSelectList().size() + ")");

        adapter.notifyDataSetChanged();
    }
}

