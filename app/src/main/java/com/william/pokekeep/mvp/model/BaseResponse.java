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
package com.william.pokekeep.mvp.model;


import com.william.pokekeep.app.utils.PresenterKit;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link BaseResponse}, 如果你实在看不懂, 请忽略
 * <p>
 * Created by JessYan on 26/09/2016 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -6904988272814075868L;

    private int code;
    private T data;
    private List<T> list;
    private String msg;

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public List<T> getList() {
        return list;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (code == PresenterKit.SUCCESS) {
            return true;
        } else {
            return false;
        }
    }
}
