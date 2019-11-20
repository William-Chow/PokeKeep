package com.william.pokekeep.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;
import com.william.pokekeep.mvp.presenter.PokeDetailPresenter;

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
 * Created by MVPArtTemplate on 11/19/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PokeDetailFragment extends BaseFragment<PokeDetailPresenter> implements IView {

    @BindView(R.id.ivPokeImage)
    ImageView ivPokeImage;

    @BindView(R.id.tvPokeName)
    TextView tvPokeName;

    @BindView(R.id.tvGenerationName)
    TextView tvGenerationName;

    @BindView(R.id.ivPokeTypeMain)
    ImageView ivPokeTypeMain;

    @BindView(R.id.ivPokeTypeSecond)
    ImageView ivPokeTypeSecond;

    @BindView(R.id.tvPokeDescription)
    TextView tvPokeDescription;

    @BindView(R.id.tvSkill)
    TextView tvSkill;

    private int position = -1;

    public static PokeDetailFragment newInstance() {
        Bundle args = new Bundle();
        PokeDetailFragment fragment = new PokeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poke_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // handle your code here.
            position = bundle.getInt("key");
            if (null != mPresenter && position != -1) {
                mPresenter.callPokemonDetail(PresenterKit.obtainMessage(PokeDetailFragment.this), position);
            }
        }
    }

    @Override
    @Nullable
    public PokeDetailPresenter obtainPresenter() {
        return new PokeDetailPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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
            case PresenterKit.POKE_DETAIL:
                PokeDexObj pokeDexObj = (PokeDexObj) message.obj;
                if (null != getActivity()) {
                    Glide.with(PokeDetailFragment.this.getActivity()).load(pokeDexObj.getImage()).placeholder(R.drawable.drawable_poke_dex).into(ivPokeImage);
                    tvPokeName.setText(pokeDexObj.getName());
                    tvGenerationName.setText(Utils.returnGeneration(pokeDexObj.getGeneration()));
                    if (!pokeDexObj.getType().get(0).getMain().equalsIgnoreCase("")) {
                        Glide.with(PokeDetailFragment.this.getActivity()).load(Utils.returnType(pokeDexObj.getType().get(0).getMain())).placeholder(R.drawable.drawable_poke_dex).into(ivPokeTypeMain);
                    }
                    if (!pokeDexObj.getType().get(0).getSecond().equalsIgnoreCase("")) {
                        Glide.with(PokeDetailFragment.this.getActivity()).load(Utils.returnType(pokeDexObj.getType().get(0).getSecond())).placeholder(R.drawable.drawable_poke_dex).into(ivPokeTypeSecond);
                    }
                    tvPokeDescription.setText(pokeDexObj.getDescription());
                    StringBuilder builder = new StringBuilder();
                    for (String s : pokeDexObj.getPower()) {
                        builder.append(s);
                        builder.append(", ");
                    }
                    tvSkill.setText(builder.toString().trim());
                }
                break;
            case PresenterKit.FAILED:
                Utils.presenterKitFail(PokeDetailFragment.this.getActivity(), PokeDetailFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeDetailFragment.this.getResources().getString(R.string.dialogUnknownMessage), PokeDetailFragment.this.getResources().getString(R.string.btnOk));
                break;
            default:
                break;
        }
    }
}
