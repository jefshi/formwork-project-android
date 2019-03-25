package com.csp.formwork.network.visit;


/**
 * Demo 网络访问，根据业务模块划分
 */
public class DemoVisit {

//    public static void requestMarketing(MarketingReq request, NetObserver<MarketingRsp> netObserver) {
//        RequestBody body = RequestBodyFormat.getJsonBody(request);
//        RequestBody body = RequestBodyFormat.getFormBody(request);
//        if (body == null) {
//            netObserver.onError(new VisitException(VisitException.REQUEST_NULL));
//            return;
//        }
//
//        BaseRetrofit.getInstance()
//                .createNetService()
//                .requestMarketing(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(netObserver);
//    }
}
