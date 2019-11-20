package com.william.pokekeep.mvp.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.PromptAlertDialogKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;
import com.william.pokekeep.mvp.presenter.PokeEditPresenter;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by MVPArtTemplate on 11/19/2019 09:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArt">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PokeEditFragment extends BaseFragment<PokeEditPresenter> implements IView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.etEditPokemonName)
    EditText etEditPokemonName;

    @BindView(R.id.tvSelectPokemonGeneration)
    TextView tvSelectPokemonGeneration;

    @BindView(R.id.tvPokemonTypeMain)
    TextView tvPokemonTypeMain;

    @BindView(R.id.tvPokemonTypeSecond)
    TextView tvPokemonTypeSecond;

    @BindView(R.id.etEditPokemonDescription)
    EditText etEditPokemonDescription;

    @BindView(R.id.tvPokemonRanking)
    TextView tvPokemonRanking;

    @BindView(R.id.etEditPokemonAddress)
    EditText etEditPokemonAddress;

    @BindView(R.id.btnEditAdd)
    Button btnEditAdd;

    private int position = -1;
    // If False means Edit Page
    // If True means Add Page
    private boolean isAdd = false;

    private List<String> menuPokemonGenerationArrayList = new ArrayList<>();
    private int positionPokemonGeneration = -1;
    private List<String> menuTypeArrayList = new ArrayList<>();
    private int positionPokemonMainType = -1, positionPokemonSecondType = -1, positionRanking = -1;
    private List<String> menuRankingArrayList = new ArrayList<>();

    PokeDexObj pokeDexObj = new PokeDexObj();

    public static PokeEditFragment newInstance() {
        Bundle args = new Bundle();
        PokeEditFragment fragment = new PokeEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poke_edit, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        menuPokemonGenerationArrayList = Arrays.asList(getResources().getStringArray(R.array.menuPokemonGenerationListArray));
        menuTypeArrayList = Arrays.asList(getResources().getStringArray(R.array.menuTypeListArray));
        menuRankingArrayList = Arrays.asList(getResources().getStringArray(R.array.menuRankingListArray));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isAdd = bundle.getBoolean("flag");
            position = bundle.getInt("key");
            if (isAdd) {
                tvTitle.setText(PokeEditFragment.this.getResources().getString(R.string.addTitle));
                btnEditAdd.setText(PokeEditFragment.this.getResources().getString(R.string.btnAdd));
                setupFirstTime();
            } else {
                tvTitle.setText(PokeEditFragment.this.getResources().getString(R.string.editTitle));
                btnEditAdd.setText(PokeEditFragment.this.getResources().getString(R.string.btnEdit));
                if (null != mPresenter && position != -1) {
                    mPresenter.callPokemonDetail(PresenterKit.obtainMessage(PokeEditFragment.this), position);
                }
            }
        }
    }

    private void setupFirstTime() {
        etEditPokemonName.setText("");
        positionPokemonGeneration = 0;
        tvSelectPokemonGeneration.setText(menuPokemonGenerationArrayList.get(positionPokemonGeneration));
        positionPokemonMainType = 0;
        tvPokemonTypeMain.setText(menuTypeArrayList.get(positionPokemonMainType));
        positionPokemonSecondType = 1;
        tvPokemonTypeSecond.setText(menuTypeArrayList.get(positionPokemonSecondType));
        etEditPokemonDescription.setText("");
        positionRanking = 0;
        tvPokemonRanking.setText(menuRankingArrayList.get(positionRanking));
        etEditPokemonAddress.setText("");
    }

    @Override
    @Nullable
    public PokeEditPresenter obtainPresenter() {
        return new PokeEditPresenter(ArtUtils.obtainAppComponentFromContext(getActivity()), Objects.requireNonNull(getActivity()).getApplication(), getActivity());
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
                pokeDexObj = (PokeDexObj) message.obj;
                if (null != getActivity() && !isAdd) {
                    etEditPokemonName.setText(pokeDexObj.getName());
                    positionPokemonGeneration = pokeDexObj.getGeneration() - 1;
                    tvSelectPokemonGeneration.setText(menuPokemonGenerationArrayList.get(positionPokemonGeneration));

                    positionPokemonMainType = Utils.returnNumberType(pokeDexObj.getType().get(0).getMain());
                    if (positionPokemonMainType != 18) {
                        tvPokemonTypeMain.setText(menuTypeArrayList.get(positionPokemonMainType));
                    }
                    positionPokemonSecondType = Utils.returnNumberType(pokeDexObj.getType().get(0).getSecond());
                    if (positionPokemonSecondType != 18) {
                        tvPokemonTypeSecond.setText(menuTypeArrayList.get(positionPokemonSecondType));
                    }

                    etEditPokemonDescription.setText(pokeDexObj.getDescription());
                    positionRanking = Utils.returnRankingNumber(pokeDexObj.getRanking());
                    tvPokemonRanking.setText(menuRankingArrayList.get(positionRanking));
                    etEditPokemonAddress.setText(pokeDexObj.getAddress());
                }
                break;
            case PresenterKit.POKE_ADD:
                PromptAlertDialogKit.commonAlertDialogWithPositiveButtonGoToPokemonList(PokeEditFragment.this.getActivity(), PokeEditFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeEditFragment.this.getResources().getString(R.string.dialogAddedSuccessMessage), PokeEditFragment.this.getResources().getString(R.string.btnOk), PokeEditFragment.this);
                break;
            case PresenterKit.POKE_EDIT:
                PromptAlertDialogKit.commonAlertDialogWithPositiveButtonGoToPokemonList(PokeEditFragment.this.getActivity(), PokeEditFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeEditFragment.this.getResources().getString(R.string.dialogEditSuccessMessage), PokeEditFragment.this.getResources().getString(R.string.btnOk), PokeEditFragment.this);
                break;
            case PresenterKit.FAILED:
                Utils.presenterKitFail(PokeEditFragment.this.getActivity(), PokeEditFragment.this.getResources().getString(R.string.dialogNoticeTitle), PokeEditFragment.this.getResources().getString(R.string.dialogUnknownMessage), PokeEditFragment.this.getResources().getString(R.string.btnOk));
                break;
            default:
                break;
        }
    }

    @Optional
    @OnClick({R.id.tvSelectPokemonGeneration, R.id.tvPokemonTypeMain, R.id.tvPokemonTypeSecond, R.id.tvPokemonRanking, R.id.btnEditAdd})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSelectPokemonGeneration:
                promptPokemonGeneration();
                break;
            case R.id.tvPokemonTypeMain:
                promptPokemonMainType();
                break;
            case R.id.tvPokemonTypeSecond:
                promptPokemonSecondType();
                break;
            case R.id.tvPokemonRanking:
                promptPokemonRanking();
                break;
            case R.id.btnEditAdd:
                if (null != mPresenter) {
                    mPresenter.callEndPoint(PresenterKit.obtainMessage(PokeEditFragment.this), isAdd, position, pokeDexObj, etEditPokemonName, positionPokemonGeneration + 1, tvPokemonTypeMain, tvPokemonTypeSecond, etEditPokemonDescription, Utils.returnRankingNumber(tvPokemonRanking.getText().toString()), etEditPokemonAddress);
                }
                break;
            default:
                break;
        }
    }

    private void promptPokemonGeneration() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokeEditFragment.this.getActivity());
        builder.setTitle(R.string.edit_pokemon_generation_label);
        builder.setSingleChoiceItems(menuPokemonGenerationArrayList.toArray(new String[menuPokemonGenerationArrayList.size()]), positionPokemonGeneration, null).setCancelable(false).setPositiveButton(PokeEditFragment.this.getResources().getString(R.string.btnOk), (dialog, position) -> {
            int checkedItemPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (checkedItemPosition != -1) {
                positionPokemonGeneration = checkedItemPosition;
                tvSelectPokemonGeneration.setText(menuPokemonGenerationArrayList.get(checkedItemPosition));
            }
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.btnCancel, (dialog, position) -> dialog.dismiss()).show();
    }

    private void promptPokemonMainType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokeEditFragment.this.getActivity());
        builder.setTitle(R.string.edit_pokemon_type_label);
        builder.setSingleChoiceItems(menuTypeArrayList.toArray(new String[menuTypeArrayList.size()]), positionPokemonMainType, null).setCancelable(false).setPositiveButton(PokeEditFragment.this.getResources().getString(R.string.btnOk), (dialog, position) -> {
            int checkedItemPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (checkedItemPosition != -1) {
                positionPokemonMainType = checkedItemPosition;
                tvPokemonTypeMain.setText(menuTypeArrayList.get(checkedItemPosition));
            }
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.btnCancel, (dialog, position) -> dialog.dismiss()).show();
    }

    private void promptPokemonSecondType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokeEditFragment.this.getActivity());
        builder.setTitle(R.string.edit_pokemon_type_label);
        builder.setSingleChoiceItems(menuTypeArrayList.toArray(new String[menuTypeArrayList.size()]), positionPokemonSecondType, null).setCancelable(false).setPositiveButton(PokeEditFragment.this.getResources().getString(R.string.btnOk), (dialog, position) -> {
            int checkedItemPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (checkedItemPosition != -1) {
                positionPokemonSecondType = checkedItemPosition;
                tvPokemonTypeSecond.setText(menuTypeArrayList.get(checkedItemPosition));
            }
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.btnCancel, (dialog, position) -> dialog.dismiss()).show();
    }

    private void promptPokemonRanking() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokeEditFragment.this.getActivity());
        builder.setTitle(R.string.edit_pokemon_ranking_label);
        builder.setSingleChoiceItems(menuRankingArrayList.toArray(new String[menuRankingArrayList.size()]), positionRanking, null).setCancelable(false).setPositiveButton(PokeEditFragment.this.getResources().getString(R.string.btnOk), (dialog, position) -> {
            int checkedItemPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (checkedItemPosition != -1) {
                positionRanking = checkedItemPosition;
                tvPokemonRanking.setText(menuRankingArrayList.get(checkedItemPosition));
            }
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.btnCancel, (dialog, position) -> dialog.dismiss()).show();
    }

}
