package com.csp.formwork.network.okhttp;

import android.util.Log;

import com.zqyl.utillib.LogCat;
import com.zqylapp.network.response.CommonRsp;
import com.zqylapp.utils.Constant;

import java.net.SocketTimeoutException;
import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * subscribe() 参数封装
 * Created by csp on 2018/07/30.
 * Modified by csp on 2019/03/15.
 *
 * @version 1.1.1
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class NetObserver<T extends CommonRsp> implements Observer<T> {

    private Disposable mDisposable;
    private NetObserver<T> mOnNext;

    public Disposable getDisposable() {
        return mDisposable;
    }

    public void setOnNext(NetObserver<T> onNext) {
        mOnNext = onNext;
    }

    public NetObserver() {
    }

    public NetObserver(NetObserver<T> onNext) {
        mOnNext = onNext;
    }

    @Override
    public final void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public final void onNext(T rsp) {
        try {
            accept(rsp);

            if (mOnNext != null)
                mOnNext.accept(rsp);
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof SocketTimeoutException)
            LogCat.printStackTrace(Log.DEBUG, "（网络请求）", t);
        else
            LogCat.printStackTrace("（网络请求）", t);

        if (mOnNext != null)
            mOnNext.onError(t);
    }

    @Override
    public void onComplete() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }

        if (mOnNext != null)
            mOnNext.onComplete();
    }

    public void accept(T rsp) throws Exception {
        checkDataNotNull(rsp);
    }

    protected final void checkNotNull(T rsp) throws VisitException {
        if (rsp == null)
            throw new VisitException(VisitException.RESPONSE_NULL);
    }

    protected final void checkCodeCorrect(T rsp) throws VisitException {
        checkNotNull(rsp);

        if (!Constant.Network.SUCCESSFUL_CODE.equals(rsp.getStatus()))
            throw new VisitException(rsp.getStatus(), rsp.getMsg());
    }

    protected final void checkCodeCorrect(T rsp, String... successes) throws VisitException {
        checkNotNull(rsp);

        boolean fail = true;
        String code = rsp.getStatus();
        for (String success : successes) {
            if (success.equals(code)) {
                fail = false;
                break;
            }
        }

        if (fail)
            throw new VisitException(rsp.getStatus(), rsp.getMsg());
    }

    protected final void checkDataNotNull(T rsp) throws VisitException {
        checkCodeCorrect(rsp);

        if (rsp.getData() == null)
            throw new VisitException(VisitException.RESPONSE_DATA_NULL);
    }

    protected final void checkDataNotEmpty(T rsp) throws VisitException {
        checkDataNotNull(rsp);

        Object data = rsp.getData();
        if (data instanceof Collection
                && ((Collection) data).isEmpty())
            throw new VisitException(VisitException.RESPONSE_DATA_EMPTY);
    }
}
