package com.william.pokekeep.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.presenter.MemberInfoPresenter;

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
 * Created by MVPArtTemplate on 11/19/2019 09:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MemberInfoFragment extends BaseFragment<MemberInfoPresenter> implements IView {

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    // Creating FirebaseAuth.
    private FirebaseAuth firebaseAuth;

    // Creating FirebaseAuth.
    private FirebaseUser firebaseUser;

    public static MemberInfoFragment newInstance() {
        Bundle args = new Bundle();
        MemberInfoFragment fragment = new MemberInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_info, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // Adding FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // On activity start check whether there is user previously logged in or not.
        if (firebaseAuth.getCurrentUser() == null) {
            // Finishing current Profile activity.
            Utils.startLoginFragment(MemberInfoFragment.this);
        }

        // Adding firebaseAuth current user info into firebaseUser object.
        firebaseUser = firebaseAuth.getCurrentUser();
        if (null != firebaseUser) {
            tvEmail.setText("Successfully logged in, and Your email is " + "\n\n" + firebaseUser.getEmail());
        }
    }

    @Override
    @Nullable
    public MemberInfoPresenter obtainPresenter() {
        return new MemberInfoPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Optional
    @OnClick({R.id.btnLogout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                // Destroying login season.
                firebaseAuth.signOut();
                // Finishing current User Profile activity.
                // Redirect to Login Activity after click on logout button.
                Utils.startLoginFragment(MemberInfoFragment.this);
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        //handle back press event
    }
}
