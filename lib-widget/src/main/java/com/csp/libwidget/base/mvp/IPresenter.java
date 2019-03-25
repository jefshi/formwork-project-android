package com.csp.libwidget.base.mvp;

public interface IPresenter {
    /**
     * 页面初始化时，所需的数据处理（包括请求）
     */
    void onCreate();

    /**
     * Presenter 销毁回调
     */
    void onDestroy();
}
