package com.snowy.ttword.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.ttword.Constants;
import com.snowy.ttword.R;
import com.snowy.widget.ClickButton;

/**
 * @author guobaolun
 * @since 2019/4/4 14:29
 */


public class PhoneticListAdapter extends BaseAdapter {

    private String[] phoneticArr;
    private Context context;
    private int height;

    public PhoneticListAdapter(Context context, String[] phoneticArr,int height) {
        this.phoneticArr = phoneticArr;
        this.context = context;

        this.height = height;
    }

    @Override
    public int getCount() {
        return phoneticArr.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_phonetic_bt, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.height = height;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            convertView.setLayoutParams(params);
        }


        final String phonetic = phoneticArr[position];


        final ClickButton button = convertView.findViewById(R.id.phonetic_bt);


        if (!"".equals(phonetic)) {
            button.setText("[ " + phonetic + " ]");
        }


        button.setOnClickListener(new ClickButton.ClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
//                if ("".equals(phonetic)) {
//                    return;
//                }
//                if (loudspeakerIb.isSelected()) {
//                    MediaPlayerManager.play(context, Constants.RESOURCES_PHONETIC_PATH + phonetic.hashCode() + ".mp3");
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), PhoneticDetailsActivity.class);
//                    intent.putExtra(PHONETIC, phonetic);
//                    startActivity(intent);
//                }
            }
        });


        return convertView;

    }
}
