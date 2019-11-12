package com.snowy.ttword.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.ttword.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * @author guobaolun
 */
public class SwipeToLoadManager {


    private SwipeToLoadLayout swipeToLoadLayout;

    private LinearLayout progressLl;

    private LinearLayout reloadLl;

    private Context context;
    private View view;

    private SwipeToLoadManagerListener managerListener;

    private RecyclerView.Adapter adapter;
    private SwipeMenuRecyclerView recyclerView;
    private OnScrollListener scrollListener;
    private SwipeMenuCreator swipeMenuCreator;
    private SwipeMenuItemClickListener mSwipeMenuItemClickListener;







    public SwipeToLoadManager(Context context, View view, SwipeToLoadManagerListener managerListener, RecyclerView.Adapter adapter) {
        this.context = context;
        this.view = view;
        this.managerListener = managerListener;
        this.adapter = adapter;
        initRecyclerView();
    }


    public SwipeToLoadManager(Context context, View view, SwipeToLoadManagerListener managerListener, RecyclerView.Adapter adapter, SwipeMenuCreator swipeMenuCreator, SwipeMenuItemClickListener mSwipeMenuItemClickListener) {
        this.context = context;
        this.view = view;
        this.managerListener = managerListener;
        this.adapter = adapter;
        this.swipeMenuCreator = swipeMenuCreator;
        this.mSwipeMenuItemClickListener = mSwipeMenuItemClickListener;
        initRecyclerView();
    }


    public void showRecyclerView() {
        showView(R.id.swipeToLoadLayout);
    }

    public void showReloadView() {
        showView(R.id.reload_ll);
    }

    public void showProgressView() {
        showView(R.id.progress_ll);
    }


    public void setRefreshing(boolean refreshing) {
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    public void setLoadingMore(boolean loadingMore) {
        swipeToLoadLayout.setLoadingMore(loadingMore);
    }


    public void addItemDecoration() {
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.line_divider);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context, drawable, ScreenUtils.dip2px(context, 0.8f)));
    }


    private void initRecyclerView() {
        this.progressLl = view.findViewById(R.id.progress_ll);
        this.reloadLl = view.findViewById(R.id.reload_ll);
        this.reloadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerListener.onReload();
            }
        });


        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        recyclerView = view.findViewById(R.id.swipe_target);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setSwipeMenuCreator(swipeMenuCreator);

        recyclerView.setSwipeMenuItemClickListener(mSwipeMenuItemClickListener);



        recyclerView.setAdapter(adapter);

        swipeToLoadLayout.setOnRefreshListener(managerListener);

        swipeToLoadLayout.setOnLoadMoreListener(managerListener);


    }


    public void setScrollListener(OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setScrollLoadingMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (scrollListener != null) {
                    scrollListener.onScrollStateChanged(recyclerView, newState);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
    }


    public void setLoadMoreEnabled(boolean enable) {
        swipeToLoadLayout.setLoadMoreEnabled(enable);
    }

    public void setRefreshEnabled(boolean enable) {
        swipeToLoadLayout.setRefreshEnabled(enable);
    }


    private void showView(int id) {
        switch (id) {
            case R.id.progress_ll:
                progressLl.setVisibility(View.VISIBLE);
                swipeToLoadLayout.setVisibility(View.GONE);
                reloadLl.setVisibility(View.GONE);
                break;
            case R.id.reload_ll:
                progressLl.setVisibility(View.GONE);
                reloadLl.setVisibility(View.VISIBLE);
                swipeToLoadLayout.setVisibility(View.GONE);
                break;
            case R.id.swipeToLoadLayout:
                progressLl.setVisibility(View.GONE);
                reloadLl.setVisibility(View.GONE);
                swipeToLoadLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    public interface OnScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }




    public interface SwipeToLoadManagerListener extends OnRefreshListener, OnLoadMoreListener {
        void onReload();
    }

}
