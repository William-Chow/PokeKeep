package com.william.pokekeep.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.PromptAlertDialogKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;
import com.william.pokekeep.mvp.presenter.PokeListingPresenter;

import java.util.List;
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
 * Created by MVPArtTemplate on 11/19/2019 09:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PokeListingFragment extends BaseFragment<PokeListingPresenter> implements IView {

    @BindView(R.id.tvMiddleText)
    TextView tvMiddleText;

    @BindView(R.id.tvRightText)
    TextView tvRightText;

    @BindView(R.id.tvRightText2)
    TextView tvRightText2;

    @BindView(R.id.rvPokeList)
    RecyclerView rvPokeList;

    public static PokeListingFragment newInstance() {
        Bundle args = new Bundle();
        PokeListingFragment fragment = new PokeListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poke_listing, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (null != mPresenter) {
            mPresenter.setTopBar(tvMiddleText, tvRightText, tvRightText2);
            mPresenter.getPokeList(PresenterKit.obtainMessage(PokeListingFragment.this));
        }
    }

    @Override
    @Nullable
    public PokeListingPresenter obtainPresenter() {
        return new PokeListingPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case PresenterKit.POKE_LIST:
                List<PokeDexObj> result = (List<PokeDexObj>) message.obj;
                if (null != mPresenter) {
                    mPresenter.setupRecyclerView(PresenterKit.obtainMessage(PokeListingFragment.this), PokeListingFragment.this, rvPokeList, result);
                }
                break;
            case PresenterKit.POKE_DELETE:
                PromptAlertDialogKit.commonAlertDialogWithNegativeButton(PokeListingFragment.this.getActivity(), PokeListingFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeListingFragment.this.getResources().getString(R.string.dialogDeleteSuccessful), PokeListingFragment.this.getResources().getString(R.string.btnOk));
                mPresenter.getPokeList(PresenterKit.obtainMessage(PokeListingFragment.this));
                break;
            case PresenterKit.FAILED:
                Utils.presenterKitFail(PokeListingFragment.this.getActivity(), PokeListingFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeListingFragment.this.getResources().getString(R.string.dialogUnknownMessage), PokeListingFragment.this.getResources().getString(R.string.btnOk));
                break;
            default:
                break;
        }
    }

    @Optional
    @OnClick({R.id.tvRightText})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRightText:
                // Add
                Utils.startPokeEditFragment(PokeListingFragment.this, true, -1);
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        //handle back press event
    }
}
