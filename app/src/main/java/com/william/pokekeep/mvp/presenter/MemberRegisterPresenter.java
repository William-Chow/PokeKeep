package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.Repository;

import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArtTemplate on 11/19/2019 09:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MemberRegisterPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public MemberRegisterPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    public void inputChecking(Message msg, String etEmail, String etPassword, FirebaseAuth mAuth, ProgressBar progressBar) {
        IView view = PresenterKit.viewGetTarget(msg);
        String error;
        progressBar.setVisibility(View.GONE);
        if (etEmail.length() == 0) {
            error = activity.getResources().getString(R.string.registerEmailCannotBeEmpty);
            PresenterKit.displayErrorMessage(view, error);
        } else if (!Utils.isEmail(etEmail)) {
            error = activity.getResources().getString(R.string.registerEmailInvalid);
            PresenterKit.displayErrorMessage(view, error);
        } else if (etPassword.length() == 0) {
            error = activity.getResources().getString(R.string.registerPasswordCannotEmpty);
            PresenterKit.displayErrorMessage(view, error);
        } else if (etPassword.length() < 6) {
            error = activity.getResources().getString(R.string.registerPasswordLess);
            PresenterKit.displayErrorMessage(view, error);
        } else {
            registerUser(msg, mAuth, etEmail, etPassword, progressBar);
        }
    }

    private void registerUser(Message msg, FirebaseAuth mAuth, String etEmail, String etPassword, ProgressBar progressBar) {
        IView view = PresenterKit.viewGetTarget(msg);
        mAuth.createUserWithEmailAndPassword(etEmail, etPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                PresenterKit.successResult(msg, PresenterKit.REGISTER);
            } else {
                progressBar.setVisibility(View.GONE);
                PresenterKit.displayErrorMessage(view, activity.getResources().getString(R.string.registration_fail));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}