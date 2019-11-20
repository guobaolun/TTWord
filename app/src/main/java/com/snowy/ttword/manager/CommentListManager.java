package com.snowy.ttword.manager;


import androidx.recyclerview.widget.RecyclerView;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.adapter.CommentAdapter;
import com.snowy.ttword.adapter.FirstLoaderViewHolder;

public class CommentListManager {



    private SwipeToLoadManager swipeToLoadManager;
    private CommentAdapter commentAdapter;
    private CommentSendManager commentSendManager;
    private BaseActivity mActivity;



    public CommentListManager(BaseActivity activity ) {
//        mHandler = new MyHandler(this);
        mActivity = activity;
        initView();
    }




    private void initView() {
        commentAdapter = new CommentAdapter(mActivity);
        MyLoaderListener loaderListener =  new MyLoaderListener();
        commentAdapter.setLoaderListener(loaderListener);



        SwipeToLoadListener listener = new SwipeToLoadListener();
        swipeToLoadManager = new SwipeToLoadManager(mActivity, mActivity.getContentView(), listener, commentAdapter);
        swipeToLoadManager.setScrollLoadingMore();
        swipeToLoadManager.showRecyclerView();
//        swipeToLoadManager.setScrollListener(commentSendManager.getOnScrollListener());
        swipeToLoadManager.setLoadMoreEnabled(false);

        swipeToLoadManager.showRecyclerView();

    }

    public void setHeadView(RecyclerView.ViewHolder headViewHolder){
        commentAdapter.setHeaderView(headViewHolder);
    }



    class SwipeToLoadListener implements SwipeToLoadManager.SwipeToLoadManagerListener {

        @Override
        public void onRefresh() {
//            mPresenter.getListData(1, getCommentListURL, articleId, null);
        }

        @Override
        public void onLoadMore() {
//            mPresenter.getListData(page, getCommentListURL, articleId, null);
        }

        @Override
        public void onReload() {
        }
    }






    class MyLoaderListener implements FirstLoaderViewHolder.LoaderListener {

        @Override
        public void loadData() {
            //TODO
//            mPresenter.getListData(1, getCommentListURL, articleId, null);
        }
    }



}
