package com.snowy.common.mvp;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V,M> {

    /**
     * 当内存不足释放内存,view 的弱引用
     */
    protected WeakReference<V> mReference;
    protected M model;


    public BasePresenter(V view) {
        mReference = new WeakReference<>(view);
        model = createModel();
    }


    public void detachView(){
        if (mReference != null){
            mReference.clear();
            mReference = null;
        }
    }

    protected abstract M createModel();
}
