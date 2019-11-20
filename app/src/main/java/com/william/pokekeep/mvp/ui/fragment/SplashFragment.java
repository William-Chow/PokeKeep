package com.william.pokekeep.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.presenter.SplashPresenter;

import java.util.Objects;

import butterknife.BindView;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArtTemplate on 11/19/2019 09:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SplashFragment extends BaseFragment<SplashPresenter> implements IView {

    @BindView(R.id.tvJumpAdvert)
    TextView tvJumpAdvert;

    private CountDownTimer countDownTimer;
    private boolean isTimeDone = false;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        countDownSetup();
    }

    @Override
    @Nullable
    public SplashPresenter obtainPresenter() {
        return new SplashPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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

    private void countDownSetup() {
        countDownTimer = new CountDownTimer(3000, 500) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvJumpAdvert != null) {
                    tvJumpAdvert.setText(SplashFragment.this.getResources().getString(R.string.please_wait) + " " + (millisUntilFinished / 1000 + 1) + " " + SplashFragment.this.getResources().getString(R.string.second));
                }
            }

            @Override
            public void onFinish() {
                isTimeDone = true;
                moveFragment();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.cancelTimer(countDownTimer);
        }
    }

    private void moveFragment() {
        if (isTimeDone) {
            Utils.startLoginFragment(SplashFragment.this);
        }
    }
}
