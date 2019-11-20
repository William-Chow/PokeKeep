package com.william.pokekeep.app.utils;

import android.app.Activity;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.william.pokekeep.R;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by William Chow on 2019-11-19.
 */
public class ErrorUtils {

    /**
     * onError Result Throw
     *
     * @param _activity activity
     * @param t         t
     */
    public static void onFailureResult(Activity _activity, Throwable t) {
        if (t.toString().length() > 0) {
            if (null != _activity) {
                if (!(_activity).isFinishing()) {
                    PromptAlertDialogKit.commonAlertDialogWithNegativeButton(_activity, _activity.getResources().getString(R.string.dialogNoticeTitle), handleResponseError(_activity, t), _activity.getResources().getString(R.string.btnOk));
                }
            }
        } else {
            if (!(_activity).isFinishing()) {
                PromptAlertDialogKit.commonAlertDialogWithNegativeButton(_activity, _activity.getResources().getString(R.string.dialogNoticeTitle), handleResponseError(_activity, t), _activity.getResources().getString(R.string.btnOk));
            }
        }
    }

    private static String handleResponseError(Activity _activity, Throwable t) {
        String msg;
        if (t instanceof UnknownHostException) {
            msg = _activity.getResources().getString(R.string.unknown_host_error);
        } else if (t instanceof SocketTimeoutException) {
            msg = _activity.getResources().getString(R.string.socket_time_out_error);
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(_activity, httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = _activity.getResources().getString(R.string.data_invalid);
        } else if (t instanceof SSLHandshakeException) {
            msg = _activity.getResources().getString(R.string.socket_time_out_error);
        } else {
            msg = _activity.getResources().getString(R.string.unknown_error);
        }

        return msg;
    }

    private static String convertStatusCode(Activity _activity, HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = _activity.getResources().getString(R.string.server_error);
        } else if (httpException.code() == 404) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 403) {
            msg = _activity.getResources().getString(R.string.server_rejected);
        } else if (httpException.code() == 307) {
            msg = _activity.getResources().getString(R.string.error_connection);
        } else if (httpException.code() == 401) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 408) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 502) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 503) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 504) {
            msg = _activity.getResources().getString(R.string.address_error);
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
