package com.snowy.ttword.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.MyApplication;
import com.snowy.ttword.R;
import com.snowy.ttword.fragment.MeFragment;
import com.snowy.ttword.fragment.WordBookFragment;
import com.snowy.ttword.fragment.WordFragment;
import com.snowy.widget.TabFragmentHost;

/**
 * @author guobaolun
 */
public class IndexActivity extends BaseActivity {

    //定义数组来存放Fragment界面
//    private Class<?>[] fragmentArray = {
//            StudyFragment.class,
//            WordFragment.class,
//            EmptyFragment.class,
//            FindFragment.class,
//            MeFragment.class
//    };
//    private String[] mTextZhArray = {"学习", "单词本", "发现", "我"};
//    private String[] mTextEhArray = {"Study", "Notebook","Find", "Me"};

    private String[] mTextZhArray = {"单词", "单词本", "我"};
    private String[] mTextEhArray = {"Word", "Notebook", "Me"};



    private Class<?>[] fragmentArray = {
            WordFragment.class,
            WordBookFragment.class,
            MeFragment.class
    };


    @Override
    protected int onContentView() {
        return R.layout.activity_index;
    }



    @Override
    public void initView() {


        //实例化TabHost对象，得到TabHost
        TabFragmentHost mTabHost = findViewById(R.id.index_host);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.index_body);

        mTabHost.getTabWidget().setDividerDrawable(R.color.theme_bg_c);

        for (int i = 0; i < fragmentArray.length; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextZhArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//			//设置Tab按钮的背景
//			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }



    /**
     * 给Tab按钮设置文字
     */
    private View getTabItemView(int position) {
        View view = View.inflate(getApplicationContext(), R.layout.item_index_meun, null);


        TextView menuZhTV = view.findViewById(R.id.main_menu_zh_tv);
        menuZhTV.setText(mTextZhArray[position]);

        TextView menuEnTV = view.findViewById(R.id.main_menu_en_tv);
        menuEnTV.setText(mTextEhArray[position]);

        if (position == 0) {
            menuEnTV.setSelected(true);
        }

        return view;
    }

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication application = (MyApplication) getApplication();
                application.exit();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
