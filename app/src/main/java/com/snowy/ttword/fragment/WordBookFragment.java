package com.snowy.ttword.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.storm.R;
import com.english.storm.activity.LoginActivity;
import com.english.storm.activity.RegisterActivity;
import com.english.storm.adapter.WordBookAdapter;
import com.english.storm.adapter.WordBookSwipeMenuCreator;
import com.english.storm.common.fragment.BaseMVPFragment;
import com.english.storm.common.util.ScreenUtils;
import com.english.storm.dialog.manager.ConfirmationDialogManager;
import com.english.storm.entity.WordBook;
import com.english.storm.fragment.presenter.WordBookPresenter;
import com.english.storm.fragment.view.IWordBookView;
import com.english.storm.manager.SwipeToLoadManager;
import com.english.storm.manager.SwipeToLoadManagerListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import java.util.List;

/**
 * @author guobaolun
 */
public class WordBookFragment extends BaseMVPFragment<WordBookPresenter> implements OnClickListener, IWordBookView {


    private SwipeToLoadManager swipeToLoadManager;
    private WordBookAdapter mAdapter;

    public static final int BOOK_TYPE_DEFAULT = 0;
    public static final int BOOK_TYPE_OTHER = 1;

    public final static int CLEAR = 0;
    public final static int DELETE = 1;
    public final static int RENAME = 2;

    private ConfirmationDialogManager confirmationDialogManager;
    private DialogClickListener dialogClickListener;
    private RelativeLayout loginRl;
    private LinearLayout body;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_word_book, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button button = view.findViewById(R.id.create_bt);
        button.setOnClickListener(this);

        mAdapter = new WordBookAdapter(getActivity());
        SwipeToLoadListener listener = new SwipeToLoadListener();
        swipeToLoadManager = new SwipeToLoadManager(getActivity(), view, listener, mAdapter, new WordBookSwipeMenuCreator(getActivity()), mMenuItemClickListener);
        swipeToLoadManager.setLoadMoreEnabled(false);
        swipeToLoadManager.showRecyclerView();
        swipeToLoadManager.addItemDecoration();


        Button registerBt = view.findViewById(R.id.register_bt);
        registerBt.setOnClickListener(this);


        Button loginBt = view.findViewById(R.id.login_bt);
        loginBt.setOnClickListener(this);


        loginRl = view.findViewById(R.id.login_rl);
        body = view.findViewById(R.id.body);
        mPresenter.getBookList(true, false);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.getBookList(true, false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.LOGIN_CODE && resultCode == Activity.RESULT_OK) {
            mPresenter.getBookList(true, false);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.create_bt:
                showCreateDialog(null, null);
                break;
            case R.id.register_bt:
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, LoginActivity.LOGIN_CODE);
                break;
            case R.id.login_bt:
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, LoginActivity.LOGIN_CODE);
                break;
            default:
                break;
        }
    }



    public void showCreateDialog(final Long bookId, final String bookName) {

        final Dialog dialog = new Dialog(getActivity(), R.style.ProgressDialog);

        View view = View.inflate(getActivity(), R.layout.dialog_check_password, null);
        TextView titleTv = view.findViewById(R.id.title_tv);
        final EditText textEt = view.findViewById(R.id.text_et);
        if (TextUtils.isEmpty(bookName)) {
            titleTv.setText(R.string.create_book_title);
            textEt.setText(bookName);
        } else {
            titleTv.setText(R.string.rename_book_title);
        }
        textEt.setHint(R.string.create_book_hint);
        textEt.setMaxLines(18);
        textEt.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (ScreenUtils.getScreenWidth(getActivity()) * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);

        Button cancelBt = view.findViewById(R.id.cancel_bt);
        cancelBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button okBt = view.findViewById(R.id.ok_bt);
        okBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = textEt.getText().toString().trim();
                if (TextUtils.isEmpty(inputName)) {
                    Toast.makeText(getContext(), "请输入单词本名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(bookName)) {
                    mPresenter.addWordBook(inputName);
                } else {
                    mPresenter.renameBook(bookId, inputName);
                }
                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }


    public void showConfirmationDialog(int type, int position) {
        String text;
        if (type == DELETE) {
            text = "删除单词本后数据无法恢复\n是否继续?";
        } else {
            text = "清空单词后数据无法恢复\n是否继续?";
        }

        if (confirmationDialogManager == null) {
            dialogClickListener = new DialogClickListener();
            confirmationDialogManager = new ConfirmationDialogManager(getActivity(), dialogClickListener, text);
        }
        dialogClickListener.setType(type);
        dialogClickListener.setPosition(position);
        confirmationDialogManager.setText(text);
        confirmationDialogManager.showDialog();

    }


    @Override
    public void setListData(List<WordBook> data) {
        mAdapter.setData(data);
    }

    @Override
    public void setRecyclerRefreshing(boolean refreshing) {
        swipeToLoadManager.setLoadingMore(refreshing);
        swipeToLoadManager.setRefreshing(refreshing);
    }


    @Override
    public void showRecyclerView() {
        swipeToLoadManager.showRecyclerView();
    }

    @Override
    public void showReloadView() {
        swipeToLoadManager.showReloadView();
    }

    @Override
    public void showProgressView() {
        swipeToLoadManager.showProgressView();
    }


    @Override
    public void setPage(int page) {
    }

    @Override
    protected WordBookPresenter createPresenter() {
        return new WordBookPresenter(this);
    }

    @Override
    public void showLoginView() {
        loginRl.setVisibility(View.VISIBLE);
        body.setVisibility(View.GONE);
    }

    @Override
    public void showBody() {
        body.setVisibility(View.VISIBLE);
        loginRl.setVisibility(View.GONE);
    }


    class SwipeToLoadListener implements SwipeToLoadManagerListener {


        @Override
        public void onRefresh() {
            mPresenter.getBookList(false, true);
        }


        @Override
        public void onLoadMore() {
        }

        @Override
        public void onReload() {
            mPresenter.getBookList(true, false);
        }
    }


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {


        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int adapterPosition = menuBridge.getAdapterPosition();
            int menuPosition = menuBridge.getPosition();

            switch (menuPosition) {
                case CLEAR:
                    if (mAdapter.getData().get(adapterPosition).getWordCount() > 0) {
                        showConfirmationDialog(CLEAR, adapterPosition);
                    }
                    break;
                case DELETE:
                    showConfirmationDialog(DELETE, adapterPosition);
                    break;
                case RENAME:
                    Long bookId = mAdapter.getData().get(adapterPosition).getBookId();
                    String bookName = mAdapter.getData().get(adapterPosition).getBookName();
                    showCreateDialog(bookId, bookName);
                    break;
                default:
                    break;
            }

        }
    };

    class DialogClickListener implements ConfirmationDialogManager.OnClickListener {

        private int type;
        private int position;

        public void setType(int type) {
            this.type = type;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void ok() {
            if (type == DELETE) {
                mPresenter.deleteBook(mAdapter.getData().get(position).getBookId());
            } else {
                mPresenter.clearWord(mAdapter.getData().get(position).getBookId());
            }
            confirmationDialogManager.dismiss();
        }

        @Override
        public void cancel() {
            confirmationDialogManager.dismiss();
        }
    }


}