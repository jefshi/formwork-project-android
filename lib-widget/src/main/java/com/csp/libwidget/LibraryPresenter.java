package com.csp.libwidget;

import com.csp.libwidget.mvp.IPresenter;
import com.csp.libwidget.mvp.IView;

/**
 * LibraryActivity
 * Created by csp on 2017/06/23.
 * Modified by csp on 2017/06/23.
 *
 * @version 1.0.0
 */
public abstract class LibraryPresenter implements IPresenter {
    protected IView mView;

    @Override
    public void onDestroy() {
        // mView = null;
    }
}
