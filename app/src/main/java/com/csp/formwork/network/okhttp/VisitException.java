package com.csp.formwork.network.okhttp;

/**
 * 请求参数格式化
 * Created by csp on 2018/07/08.
 * Modified by csp on 2019/3/13.
 *
 * @version 1.1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class VisitException extends Exception {

    public static final int DEFAULT = -1903111450;
    public static final int REQUEST_NULL = -1903111451;
    public static final int RESPONSE_NULL = -1903111452;
    public static final int RESPONSE_CODE_BAD = -1903111453;
    public static final int RESPONSE_DATA_NULL = -1903111454;
    public static final int RESPONSE_DATA_EMPTY = -1903111455;

    private int iCode = DEFAULT; // 网络请求响应码，类型 int
    private String sCode = null; // 网络请求响应码，类型 String

    public int getiCode() {
        return iCode;
    }

    public String getsCode() {
        return sCode;
    }

    private VisitException() {
        this(DEFAULT, null, null, null);
    }

    public VisitException(String message) {
        this(DEFAULT, null, null, message);
    }

    public VisitException(Throwable cause, String message) {
        this(DEFAULT, null, cause, message);
    }

    public VisitException(int iCode) {
        this(iCode, null, null, null);
    }

    public VisitException(int iCode, String message) {
        this(iCode, null, null, message);
    }

    public VisitException(String sCode, String message) {
        this(RESPONSE_CODE_BAD, sCode, null, message);
    }

    public VisitException(int iCode, String sCode, Throwable cause, String message) {
        super(message, cause);
        this.iCode = iCode;
        this.sCode = sCode;
    }

    @Override
    public String getLocalizedMessage() {
        return "（网络请求）异常，" + getCodeMean(iCode)
                + "，code = " + (sCode != null ? sCode : iCode)
                + "，message = " + getMessage();
    }

    private static String getCodeMean(int iCode) {
        switch (iCode) {
            case REQUEST_NULL:
                return "请求参数为空";
            case RESPONSE_NULL:
                return "响应数据为空";
            case RESPONSE_DATA_NULL:
                return "响应内容数据为空";
            case RESPONSE_DATA_EMPTY:
                return "响应集合数据为空";
            case RESPONSE_CODE_BAD:
            default:
                return "响应码不正确或其他异常";
        }
    }
}
