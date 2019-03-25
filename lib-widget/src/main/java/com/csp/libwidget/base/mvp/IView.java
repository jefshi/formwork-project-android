package com.csp.libwidget.base.mvp;

public interface IView {
    /**
     * 异常处理
     *
     * @param throwable 异常信息
     */
    void onError(Throwable throwable);

    /**
     * 数据加载过程中，页面显示(比如 ProgressBar 显示隐藏)
     *
     * @param showed true: 显示
     */
    void showRequestLoading(boolean showed);
}