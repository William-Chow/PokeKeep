/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.william.pokekeep.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.william.pokekeep.R;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link ResponseErrorListener} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @SuppressLint("TimberExceptionLogging")
    @Override
    public void handleResponseError(Context context, Throwable t) {
        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        String msg = context.getResources().getString(R.string.unknown_error);
        if (t instanceof UnknownHostException) {
            msg = context.getResources().getString(R.string.unknown_host_error);
        } else if (t instanceof SocketTimeoutException) {
            msg = context.getResources().getString(R.string.socket_time_out_error);
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(context, httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = context.getResources().getString(R.string.data_invalid);
        }
        ArtUtils.snackbarText(msg);
    }

    private String convertStatusCode(Context context, HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = context.getResources().getString(R.string.server_error);
        } else if (httpException.code() == 404) {
            msg = context.getResources().getString(R.string.address_error);
        } else if (httpException.code() == 403) {
            msg = context.getResources().getString(R.string.server_rejected);
        } else if (httpException.code() == 307) {
            msg = context.getResources().getString(R.string.error_connection);
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
