package com.snowy.ttword.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.common.utils.TimeUtils;
import com.snowy.glide.GlideUtils;
import com.snowy.ttword.R;
import com.snowy.ttword.entity.ChildComment;
import com.snowy.ttword.entity.PageData;
import com.snowy.ttword.listener.CommentVoiceClickListener;

import java.util.List;

/**
 * @author guobaolun
 * @since 2018/4/17
 */
public class ChildCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;

    private static final int TYPE_CHILD = 1;

    private static final int TYPE_LOADING = 2;
    private static final int TYPE_ENPTY_DATA = 3;

    private static final int TEXT = 0;
    private static final int VOICE = 1;


    private Context activity;


    private List<ChildComment> mDataList;


    private RecyclerView.ViewHolder headViewHolder;

    private long totalItem;


    private boolean loaderError = false;

    private ItemClickListener itemClickListener;


    private FirstLoaderViewHolder.LoaderListener loaderListener;


    public ChildCommentAdapter(Activity activity, FirstLoaderViewHolder.LoaderListener loaderListener) {
        this.activity = activity;
        this.loaderListener = loaderListener;


    }

    public void setDataList(PageData<ChildComment> data) {

        if (data.isFirstPage()) {
            mDataList = data.getList();
        } else {
            mDataList.addAll(data.getList());
        }
        totalItem = data.getTotalItem();
        notifyDataSetChanged();
    }

    public List<ChildComment> getDataList() {
        return mDataList;
    }

    public void addComment(ChildComment comment) {
        mDataList.add(0, comment);
        totalItem++;
        notifyDataSetChanged();
    }

    public void setHeaderView(RecyclerView.ViewHolder headViewHolder) {
        this.headViewHolder = headViewHolder;
    }

    public void setLoaderError(boolean loaderError) {
        this.loaderError = loaderError;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == TYPE_HEADER) {
            return headViewHolder;
        } else if (viewType == TYPE_LOADING || viewType == TYPE_ENPTY_DATA) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_comment_first_loading, parent, false);
            return new FirstLoaderViewHolder(view,loaderListener);
        } else {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_child_comment, parent, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_HEADER:
//                ChildCommentActivity.HeadViewHolder headViewHolder = (ChildCommentActivity.HeadViewHolder) holder;
                break;
            case TYPE_LOADING:
            case TYPE_ENPTY_DATA:
                onBindFirstLoaderHolder((FirstLoaderViewHolder) holder);
                break;
            case TYPE_CHILD:
                onBindItemViewHolder((ChildViewHolder) holder, position - 1);
                break;
            default:
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            if (mDataList == null) {
                return TYPE_LOADING;
            } else if (mDataList.size() == 0) {
                return TYPE_ENPTY_DATA;
            } else {
                return TYPE_CHILD;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.size() == 0) {
            return 2;
        } else {
            return mDataList.size() + 1;
        }
    }


    private void onBindItemViewHolder(ChildViewHolder holder, int position) {
        ChildComment comment = mDataList.get(position);

        holder.timeTv.setText((TimeUtils.getTime(comment.getTime())));

        holder.position = position;

        GlideUtils.loadAsBitmap(activity, comment.getPortrait(), holder.portraitIv, 0, 0);

        switch (mDataList.get(position).getType()) {
            case TEXT:
                holder.contentTv.setVisibility(View.VISIBLE);
                holder.voiceRl.setVisibility(View.GONE);
                holder.nicknameTv.setText(comment.getNickname());
                holder.contentTv.setText(getClickableSpan(comment.getTargetUserNickName(), comment.getContent()));
                break;
            case VOICE:
                if (TextUtils.isEmpty(comment.getTargetUserNickName())){
                    holder.contentTv.setVisibility(View.GONE);
                }else{
                    holder.contentTv.setVisibility(View.VISIBLE);
                }
                holder.contentTv.setText(getClickableSpan(comment.getTargetUserNickName(), ""));
                holder.voiceTimeTv.setText(comment.getVoiceTime() + "''");
                holder.voiceRl.setVisibility(View.VISIBLE);

                CommentVoiceClickListener voiceClickListener = new CommentVoiceClickListener(activity);
                voiceClickListener.setUrl(comment.getVoice());
                voiceClickListener.setVoiceIconView(holder.voiceIconIv);
                holder.voiceRl.setOnClickListener(voiceClickListener);

                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) holder.voiceRl.getLayoutParams();
                lParams.width = ScreenUtils.dip2px(activity, 70 + comment.getVoiceTime());
                holder.voiceRl.setLayoutParams(lParams);
                break;
            default:
                break;
        }
    }

    private void onBindFirstLoaderHolder(FirstLoaderViewHolder holder) {
        if (mDataList == null) {
            if (loaderError) {
                holder.reloadBt.setVisibility(View.VISIBLE);
                holder.enptyTv.setVisibility(View.GONE);
                holder.loadingIv.setVisibility(View.GONE);
            } else {
                holder.enptyTv.setVisibility(View.GONE);
                holder.reloadBt.setVisibility(View.GONE);
                holder.loadingIv.setVisibility(View.VISIBLE);
            }
        } else {
            holder.reloadBt.setVisibility(View.GONE);
            holder.enptyTv.setVisibility(View.VISIBLE);
            holder.loadingIv.setVisibility(View.GONE);
        }

    }

    private class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView timeTv;
        TextView voiceTimeTv;
        ImageView voiceIconIv;
        RelativeLayout voiceRl;
        TextView contentTv;
        ImageView portraitIv;
        TextView nicknameTv;

        int position;

        ChildViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            timeTv = view.findViewById(R.id.time_tv);
            contentTv = view.findViewById(R.id.content_tv);
            voiceTimeTv = view.findViewById(R.id.voice_time_tv);
            voiceRl = view.findViewById(R.id.voice_rl);
            voiceIconIv = view.findViewById(R.id.voice_icon_iv);

            portraitIv = view.findViewById(R.id.portrait_iv);
            nicknameTv = view.findViewById(R.id.nickname_tv);
            nicknameTv = view.findViewById(R.id.nickname_tv);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, position);
        }
    }




    private SpannableString getClickableSpan(String targetNickname, String content) {
        SpannableString spannable;

        String source;
        String text = "回复 ";
        if (TextUtils.isEmpty(targetNickname)) {
            spannable = new SpannableString(content);
        } else {
            source = text + targetNickname + "：" + content;
            spannable = new SpannableString(source);
            spannable.setSpan(new ClickableListener(), text.length(), text.length() + targetNickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return spannable;
    }

    class ClickableListener extends ClickableSpan {


        @Override
        public void updateDrawState(TextPaint ds) {
            //TODO
//            ds.setColor(ContextCompat.getColor(activity, R.color.theme_bg_b));
        }

        @Override
        public void onClick(View v) {

        }
    }


    /**
     * 创建一个回调接口
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     */
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }





}
