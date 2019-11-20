package com.william.pokekeep.app.utils;

import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;

/**
 * Created by William Chow on 2019-08-07.
 */
public class PresenterKit {

    public static final int SUCCESS = 1;
    public static final int FAILED = -1;

    public static final int REGISTER = 2;
    public static final int LOGIN = 3;
    public static final int POKE_LIST = 4;
    public static final int POKE_DETAIL = 5;
    public static final int POKE_ADD = 6;
    public static final int POKE_EDIT = 7;
    public static final int POKE_DELETE = 8;

    /**
     * Global Setup Message (Jess MVP)
     *
     * @param _iView View
     * @return Message
     */
    public static Message obtainMessage(IView _iView) {
        return Message.obtain(_iView, new Object[]{true});
    }

    public static void displayErrorMessage(IView view, String error) {
        view.showMessage(error);
    }

    /**
     * 简约回复 View
     *
     * @param _msg Message
     * @return View
     */
    public static IView viewGetTarget(Message _msg) {
        return _msg.getTarget();
    }

    /**
     * 错误
     *
     * @param _msg   _msg
     * @param _error _error
     */
    public static void error(Message _msg, int _error) {
        _msg.what = _error;
        _msg.getTarget().handleMessage(_msg);
    }

    /**
     * 简约通知 handleMessage -1
     *
     * @param _msg Message
     */
    public static void systemIssues(Message _msg, int _failed) {
        _msg.what = _failed;
        _msg.getTarget().handleMessage(_msg);
    }

    /**
     * 简约回复 Obj
     *
     * @param _msg Message
     */
    public static void successResult(Message _msg, int _success) {
        _msg.what = _success;
        _msg.getTarget().handleMessage(_msg);
    }

    /**
     * 简约回复 Obj
     *
     * @param _msg   Message
     * @param object Object
     * @param <T>    Generic
     */
    public static <T> void successObjResult(Message _msg, T object, int _success) {
        _msg.what = _success;
        _msg.obj = object;
        _msg.getTarget().handleMessage(_msg);
    }
}
