package com.snowy.ttword.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.ttword.R;
import com.snowy.ttword.entity.ChildComment;
import com.snowy.ttword.listener.CommentVoiceClickListener;

import java.util.ArrayList;


/**
 * @author guobaolun
 */
public class CommentFollowAdapter extends BaseAdapter {

    private static final int TEXT = 0;
    private static final int VOICE = 1;

    private Context context;

    private ArrayList<ChildComment> childCommentList;



    public CommentFollowAdapter(Context context) {
        this.context = context;

    }


    public void setData(ArrayList<ChildComment> childCommentList) {
        this.childCommentList = childCommentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (childCommentList == null) {
            return 0;
        } else {
            return childCommentList.size();
        }
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_comment_follow, null);
            holder = new ViewHolder();
            holder.contentTv = convertView.findViewById(R.id.content_tv);

            holder.voiceTimeTv = convertView.findViewById(R.id.voice_time_tv);
            holder.voiceIconIv = convertView.findViewById(R.id.voice_icon_iv);
            holder.voiceRl = convertView.findViewById(R.id.voice_rl);

            holder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ChildComment childComment = childCommentList.get(position);


        switch (childComment.getType()) {
            case TEXT:
                holder.voiceRl.setVisibility(View.GONE);
                holder.contentTv.setText(getClickableSpan(childComment.getNickname(), childComment.getTargetUserNickName(), childComment.getContent()));
                break;
            case VOICE:
                holder.contentTv.setText(getClickableSpan(childComment.getNickname(), childComment.getTargetUserNickName(), ""));

                holder.voiceTimeTv.setText(childComment.getVoiceTime() + "''");
                holder.voiceRl.setVisibility(View.VISIBLE);

                CommentVoiceClickListener voiceClickListener = new CommentVoiceClickListener(context);
                voiceClickListener.setUrl(childComment.getVoice());
                voiceClickListener.setVoiceIconView(holder.voiceIconIv);
                holder.voiceRl.setOnClickListener(voiceClickListener);

                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) holder.voiceRl.getLayoutParams();
                lParams.width = ScreenUtils.dip2px(context, 70 + childComment.getVoiceTime());
                holder.voiceRl.setLayoutParams(lParams);
                break;
            default:
                break;
        }


        return convertView;
    }

    private class ViewHolder {
        TextView contentTv;
        TextView voiceTimeTv;
        ImageView voiceIconIv;
        RelativeLayout voiceRl;

    }


    private SpannableString getClickableSpan(String nickname, String targetNickname, String content) {
        SpannableString spannable;
        String source;
        String text = " 回复 ";
        if (TextUtils.isEmpty(targetNickname)) {
            spannable = new SpannableString(nickname + "：" + content);
        } else {
            source = nickname + text + targetNickname + "：" + content;
            spannable = new SpannableString(source);
            spannable.setSpan(new ClickableListener(), nickname.length()+text.length(), nickname.length()+text.length()+targetNickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        spannable.setSpan(new ClickableListener(), 0, nickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }


    class ClickableListener extends ClickableSpan {


        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            //TODO
//            int color = ContextCompat.getColor(context, R.color.click_color);
//            ds.setColor(color);
        }

        @Override
        public void onClick(@NonNull View v) {

        }
    }


}
