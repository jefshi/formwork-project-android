package com.csp.libwidget.mvp;

public interface IView<T> {
    /**
     * 异常处理
     *
     * @param throwable 异常信息
     * @param type      数据处理来源（失败来源，如某个网络请求）
     */
    void onError(Throwable throwable, int type);

    /**
     * 数据加载过程中，页面显示(比如 ProgressBar 显示隐藏)
     *
     * @param showed true: 显示
     */
    void showRequestLoading(boolean showed);

    /**
     * @return 构建 Present 对象
     */
    T createPresent();
}
