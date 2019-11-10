package com.snowy.ttword.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowy.ttword.Constants;
import com.snowy.ttword.R;
import com.snowy.ttword.entity.Word;
import com.snowy.ttword.listener.MyPlayerStateListener;
import com.snowy.ttword.manager.MediaPlayerManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guobaolun
 * @since 2018/4/18
 */
public class PhoneticWordBaseAdapter extends BaseAdapter {


    private List<Word> wordList;
    private Context context;
    private String regex;
    private final MyPlayerStateListener stateListener;

    public PhoneticWordBaseAdapter(Context context) {
        this.context = context;
        stateListener = new MyPlayerStateListener();

    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
        notifyDataSetChanged();
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public int getCount() {
        return wordList == null ? 0:wordList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_phonetic_word, null);
        }
        final ImageView soundIv = convertView.findViewById(R.id.sound_iv);
        TextView wordTv = convertView.findViewById(R.id.word_tv);
        TextView phoneticTv = convertView.findViewById(R.id.phonetic_tv);
        LinearLayout textLl = convertView.findViewById(R.id.text_ll);

        wordTv.setText(wordList.get(position).getWord());

        String ukPhonetic = "[" + wordList.get(position).getUkPhonetic() + "]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ukPhonetic);
        SpannableStringBuilder builder = new SpannableStringBuilder(ukPhonetic);
        while (matcher.find()) {
            builder.setSpan(new ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        phoneticTv.setText(builder);

        textLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Constants.RESOURCES_UK_WORD_PATH + wordList.get(position).getWord().toLowerCase() + ".mp3";
                stateListener.setImageView(soundIv);
                stateListener.setPlayingResId(R.drawable.anim_sound);
                stateListener.setCompletionResId(R.drawable.ic_sound_up_off);
                MediaPlayerManager.play(context, filePath, stateListener);
            }
        });


        return convertView;
    }
}
