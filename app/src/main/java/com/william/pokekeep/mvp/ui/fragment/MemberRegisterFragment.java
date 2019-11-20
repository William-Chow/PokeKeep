package com.william.pokekeep.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.PromptAlertDialogKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.presenter.MemberRegisterPresenter;

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
 * Created by MVPArtTemplate on 11/19/2019 09:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MemberRegisterFragment extends BaseFragment<MemberRegisterPresenter> implements IView {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public static MemberRegisterFragment newInstance() {
        Bundle args = new Bundle();
        MemberRegisterFragment fragment = new MemberRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_register, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    @Nullable
    public MemberRegisterPresenter obtainPresenter() {
        return new MemberRegisterPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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
            case PresenterKit.REGISTER:
                if (null != getActivity()) {
                    etEmail.setText("");
                    etPassword.setText("");
                    PromptAlertDialogKit.commonAlertDialogWithPositiveButtonGoToLogin(getActivity(), MemberRegisterFragment.this.getActivity().getResources().getString(R.string.register), MemberRegisterFragment.this.getActivity().getResources().getString(R.string.registration_success), MemberRegisterFragment.this.getActivity().getResources().getString(R.string.btnOk), MemberRegisterFragment.this);
                }
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    @Optional
    @OnClick({R.id.btnRegister, R.id.tvLogin})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                if (null != mPresenter) {
                    progressBar.setVisibility(View.VISIBLE);
                    mPresenter.inputChecking(PresenterKit.obtainMessage(MemberRegisterFragment.this), etEmail.getText().toString().trim(), etPassword.getText().toString().trim(), mAuth, progressBar);
                }
                break;
            case R.id.tvLogin:
                Utils.startLoginFragment(MemberRegisterFragment.this);
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        //handle back press event
    }
}
