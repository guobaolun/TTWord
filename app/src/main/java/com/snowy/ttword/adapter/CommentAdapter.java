package com.snowy.ttword.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.common.utils.TimeUtils;
import com.snowy.glide.GlideUtils;
import com.snowy.ttword.R;
import com.snowy.ttword.activity.PhoneticDetailsActivity;
import com.snowy.ttword.entity.Comment;
import com.snowy.ttword.entity.PageData;
import com.snowy.ttword.listener.CommentVoiceClickListener;
import com.snowy.ttword.util.FollowListViewUtil;

import java.util.List;

/**
 * @author guobaolun
 * @since 2018/4/17
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;

    private static final int TYPE_CHILD = 1;

    private static final int TYPE_LOADING = 2;
    private static final int TYPE_ENPTY_DATA = 3;

    private static final int TEXT = 0;
    private static final int VOICE = 1;


    private Context activity;
    private FirstLoaderViewHolder.LoaderListener loaderListener;


    private List<Comment> mDataList;



    private RecyclerView.ViewHolder headViewHolder;

    private long totalItem = 0;


    private boolean loaderError = false;


    public CommentAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setLoaderListener(FirstLoaderViewHolder.LoaderListener loaderListener){
        this.loaderListener = loaderListener;
    }

    public void setDataList(PageData<Comment> data) {
        if (data.isFirstPage()) {
            mDataList = data.getList();
        } else {
            mDataList.addAll(data.getList());
        }
        totalItem = data.getTotalItem();
        notifyDataSetChanged();
    }

    public List<Comment> getDataList() {
        return mDataList;
    }

    public void addComment(Comment comment) {
        mDataList.add(0,comment);
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
            View view = LayoutInflater.from(activity).inflate(R.layout.item_comment, parent, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_HEADER:
                PhoneticDetailsActivity.HeadViewHolder headViewHolder = (PhoneticDetailsActivity.HeadViewHolder) holder;
                headViewHolder.commentCountTv.setText(String.valueOf(totalItem));
                break;
            case TYPE_LOADING:
            case TYPE_ENPTY_DATA:
                onBindFirstLoaderHolder((FirstLoaderViewHolder) holder);
                break;
            case TYPE_CHILD:
                onBindItemViewHolder((ChildViewHolder) holder, position-1);
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
            return mDataList.size()+1;
        }
    }


    private void onBindItemViewHolder(ChildViewHolder holder, int position) {
        Comment comment = mDataList.get(position);

        holder.nicknameTv.setText(comment.getNickname());
        holder.timeTv.setText((TimeUtils.getTime(comment.getTime())));
        GlideUtils.loadAsBitmap(activity, comment.getPortrait(), holder.portraitIv, 0, 0);

        if (comment.getChildCommentList() == null || comment.getChildCommentList().size() == 0) {
            holder.listView.setVisibility(View.GONE);
        } else {
            holder.listView.setVisibility(View.VISIBLE);
            holder.adapter.setData(comment.getChildCommentList());
            holder.footView.setText("共" + comment.getCommentCount() + "条回复");
            FollowListViewUtil.setListViewHeightBasedOnChildren(holder.listView);
        }
        holder.position = position;


        switch (mDataList.get(position).getType()) {
            case TEXT:
                holder.contentTv.setVisibility(View.VISIBLE);
                holder.voiceRl.setVisibility(View.GONE);
                holder.contentTv.setText(comment.getContent());
                break;
            case VOICE:
                holder.contentTv.setVisibility(View.GONE);
                holder.voiceTimeTv.setText(comment.getVoiceTime() + "''");
                holder.voiceRl.setVisibility(View.VISIBLE);

                CommentVoiceClickListener voiceClickListener = new CommentVoiceClickListener(activity);
                voiceClickListener.setUrl(comment.getVoice());
                voiceClickListener.setVoiceIconView(holder.voiceIconIv);
                holder.voiceRl.setOnClickListener(voiceClickListener);

                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) holder.voiceRl.getLayoutParams();
                lParams.width= ScreenUtils.dip2px(activity,70+comment.getVoiceTime());
                holder.voiceRl.setLayoutParams(lParams);

                break;
            default:
                break;
        }
    }

    private void onBindFirstLoaderHolder(FirstLoaderViewHolder holder) {
            if (mDataList == null) {
                if (loaderError){
                    holder.reloadBt.setVisibility(View.VISIBLE);
                    holder.enptyTv.setVisibility(View.GONE);
                    holder.loadingIv.setVisibility(View.GONE);
                }else{
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

    private class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        RelativeLayout voiceRl;
        TextView voiceTimeTv;

        ImageView portraitIv;
        TextView timeTv;
        TextView nicknameTv;
        TextView commentCountTv;
        ImageView voiceIconIv;
        ImageButton childCommentIb;
        ListView listView;
        CommentFollowAdapter adapter;
        int position ;

        TextView footView;

        ChildViewHolder(View view) {
            super(view);
            portraitIv = view.findViewById(R.id.portrait_iv);
            nicknameTv = view.findViewById(R.id.nickname_tv);
            timeTv = view.findViewById(R.id.time_tv);
            commentCountTv = view.findViewById(R.id.comment_count_tv);
            listView = view.findViewById(R.id.listview);
            childCommentIb = view.findViewById(R.id.child_comment_ib);
            adapter = new CommentFollowAdapter(activity);
            listView.setAdapter(adapter);
            footView = new TextView(activity);
            footView.setText("共0条回复");
            footView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startChildCommentActivity(position);
                }
            });
            //TODO
//            int color = ContextCompat.getColor(activity, R.color.click_color);
//            footView.setTextColor(color);
            footView.setPadding(ScreenUtils.dip2px(activity,10),ScreenUtils.dip2px(activity,3),ScreenUtils.dip2px(activity,10),ScreenUtils.dip2px(activity,3));
            listView.addFooterView(footView);
            contentTv = view.findViewById(R.id.content_tv);
            voiceTimeTv = view.findViewById(R.id.voice_time_tv);
            voiceRl = view.findViewById(R.id.voice_rl);
            voiceIconIv = view.findViewById(R.id.voice_icon_iv);



            childCommentIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startChildCommentActivity(position);
                }
            });

        }
    }

        private void startChildCommentActivity(int position){
        //TODO

//            Intent intent = new Intent(activity, ChildCommentActivity.class);
//            intent.putExtra(ChildCommentActivity.INTENT_COMMENT_ID,mDataList.get(position).getCommentId());
//            intent.putExtra(ChildCommentActivity.INTENT_NICKNAME,mDataList.get(position).getNickname());
//            intent.putExtra(ChildCommentActivity.INTENT_PORTRAIT,mDataList.get(position).getPortrait());
//            intent.putExtra(ChildCommentActivity.INTENT_TIME,mDataList.get(position).getTime());
//            intent.putExtra(ChildCommentActivity.INTENT_CONTENT,mDataList.get(position).getContent());
//            intent.putExtra(ChildCommentActivity.INTENT_VOICE,mDataList.get(position).getVoice());
//            intent.putExtra(ChildCommentActivity.INTENT_VOICE_TIME,mDataList.get(position).getVoiceTime());
//            intent.putExtra(ChildCommentActivity.INTENT_TYPE,mDataList.get(position).getType());
//            activity.startActivity(intent);
        }





}
