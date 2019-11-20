package com.william.pokekeep.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PrefUtils;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.PromptAlertDialogKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.RememberMeBean;
import com.william.pokekeep.mvp.presenter.MemberLoginPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;


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
public class MemberLoginFragment extends BaseFragment<MemberLoginPresenter> implements IView {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.ivRememberPassword)
    ImageView ivRememberPassword;

    @BindView(R.id.tvRememberPassword)
    TextView tvRememberPassword;

    private RememberMeBean rememberMeBean = new RememberMeBean();
    private Boolean rememberFlag = false;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public static MemberLoginFragment newInstance() {
        Bundle args = new Bundle();
        MemberLoginFragment fragment = new MemberLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_login, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        if (null != mPresenter && null != getActivity()) {
            rememberMeBean = PrefUtils.getInstance(MemberLoginFragment.this.getActivity().getApplication()).getRememberMe();
            if (null != rememberMeBean) {
                etEmail.setText(rememberMeBean.getEmail());
                etPassword.setText(rememberMeBean.getPassword());
                if (null != rememberMeBean.getRememberMeMark()) {
                    if (rememberMeBean.getRememberMeMark()) {
                        rememberFlag = true;
                        mPresenter.selectedRememberPassword(ivRememberPassword, tvRememberPassword);
                    } else {
                        rememberFlag = false;
                        mPresenter.unselectedRememberPassword(ivRememberPassword, tvRememberPassword);
                    }
                }
            }
        }
    }

    @Override
    @Nullable
    public MemberLoginPresenter obtainPresenter() {
        return new MemberLoginPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArtUtils.snackbarText(message);
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case PresenterKit.LOGIN:
                if (null != getActivity()) {
                    saveRememberMe(rememberFlag);
                    PromptAlertDialogKit.commonAlertDialogWithPositiveButtonGoToMemberInfo(getActivity(), MemberLoginFragment.this.getActivity().getResources().getString(R.string.login), MemberLoginFragment.this.getActivity().getResources().getString(R.string.login_success), MemberLoginFragment.this.getActivity().getResources().getString(R.string.btnOk), MemberLoginFragment.this);
                }
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    @Optional
    @OnClick({R.id.btnLogin, R.id.llLoginRememberPassword, R.id.tvRegister})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (null != mPresenter) {
                    progressBar.setVisibility(View.VISIBLE);
                    mPresenter.inputChecking(PresenterKit.obtainMessage(MemberLoginFragment.this), etEmail.getText().toString().trim(), etPassword.getText().toString().trim(), mAuth, progressBar);
                }
                break;
            case R.id.llLoginRememberPassword:
                if (null != mPresenter) {
                    if (rememberFlag) {
                        rememberFlag = false;
                        mPresenter.unselectedRememberPassword(ivRememberPassword, tvRememberPassword);
                    } else {
                        rememberFlag = true;
                        mPresenter.selectedRememberPassword(ivRememberPassword, tvRememberPassword);
                    }
                }
                break;
            case R.id.tvRegister:
                Utils.startRegisterFragment(MemberLoginFragment.this);
                break;
            default:
                break;
        }
    }

    /**
     * Remember Me Feature
     *
     * @param _isRememberMe _isRememberMe
     */
    private void saveRememberMe(Boolean _isRememberMe) {
        rememberMeBean = new RememberMeBean();
        rememberMeBean.setEmail(etEmail.getText().toString().trim());
        rememberMeBean.setPassword(etPassword.getText().toString().trim());
        rememberMeBean.setRememberMeMark(_isRememberMe);
        initRememberMe(rememberMeBean, _isRememberMe);
    }

    private void initRememberMe(RememberMeBean _rememberMeBean, Boolean _isRememberMe) {
        if (null != _rememberMeBean && null != getActivity()) {
            if (_isRememberMe) {
                PrefUtils.saveRememberMe(MemberLoginFragment.this.getActivity().getApplication(), _rememberMeBean);
            } else {
                PrefUtils.getInstance(MemberLoginFragment.this.getActivity().getApplication()).removeRememberMe();
            }
        }
    }

    public void onBackPressed() {
        //handle back press event
    }
}
