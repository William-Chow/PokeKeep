package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.william.pokekeep.mvp.model.Repository;

import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArtTemplate on 11/19/2019 09:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MemberInfoPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public MemberInfoPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}