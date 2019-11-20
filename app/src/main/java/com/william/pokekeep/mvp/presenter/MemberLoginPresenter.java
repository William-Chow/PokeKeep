package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
 * Created by MVPArtTemplate on 11/19/2019 09:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MemberLoginPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public MemberLoginPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    public void selectedRememberPassword(ImageView ivRememberPassword, TextView tvRememberPassword) {
        ivRememberPassword.setImageResource(R.drawable.drawable_button_selected);
        tvRememberPassword.setTextColor(activity.getResources().getColor(R.color.lightBlue));
    }

    public void unselectedRememberPassword(ImageView ivRememberPassword, TextView tvRememberPassword) {
        ivRememberPassword.setImageResource(R.drawable.drawable_button_in_selected);
        tvRememberPassword.setTextColor(activity.getResources().getColor(android.R.color.tab_indicator_text));
    }

    public void inputChecking(Message msg, String etEmail, String etPassword, FirebaseAuth mAuth, ProgressBar progressBar) {
        IView view = PresenterKit.viewGetTarget(msg);
        String error;
        progressBar.setVisibility(View.GONE);
        if (etEmail.length() == 0) {
            error = activity.getResources().getString(R.string.loginEmailCannotBeEmpty);
            PresenterKit.displayErrorMessage(view, error);
        } else if (!Utils.isEmail(etEmail)) {
            error = activity.getResources().getString(R.string.loginEmailInvalid);
            PresenterKit.displayErrorMessage(view, error);
        } else if (etPassword.length() == 0) {
            error = activity.getResources().getString(R.string.loginPasswordCannotEmpty);
            PresenterKit.displayErrorMessage(view, error);
        } else if (etPassword.length() < 6) {
            error = activity.getResources().getString(R.string.loginPasswordLess);
            PresenterKit.displayErrorMessage(view, error);
        } else {
            loginUser(msg, mAuth, etEmail, etPassword, progressBar);
        }
    }

    private void loginUser(Message msg, FirebaseAuth mAuth, String etEmail, String etPassword, ProgressBar progressBar) {
        IView view = PresenterKit.viewGetTarget(msg);
        mAuth.signInWithEmailAndPassword(etEmail, etPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                PresenterKit.successResult(msg, PresenterKit.LOGIN);
            } else {
                progressBar.setVisibility(View.GONE);
                PresenterKit.displayErrorMessage(view, activity.getResources().getString(R.string.login_fail));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}