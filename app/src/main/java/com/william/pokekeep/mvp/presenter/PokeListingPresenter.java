package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.CustomPopupMenu;
import com.william.pokekeep.app.utils.ErrorUtils;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.app.utils.Utils;
import com.william.pokekeep.mvp.model.BaseResponse;
import com.william.pokekeep.mvp.model.Repository;
import com.william.pokekeep.mvp.model.api.Api;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;
import com.william.pokekeep.mvp.ui.adapter.PokeListAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import retrofit2.Response;


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
public class PokeListingPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    private PokeListAdapter pokeListAdapter;

    public PokeListingPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    public void setTopBar(TextView tvMiddleText, TextView tvRightText, TextView tvRightText2) {
        tvMiddleText.setText(activity.getResources().getString(R.string.app_name));
        // tvRightText.setText(activity.getResources().getString(R.string.add));
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText2.setVisibility(View.INVISIBLE);
    }

    public void getPokeList(Message msg) {
        IView view = PresenterKit.viewGetTarget(msg);
        mModel.getAllPokemon(Api.SECRET_KEY)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(new ErrorHandleSubscriber<Response<BaseResponse<PokeDexObj>>>(mErrorHandler) {
                    @Override
                    public void onNext(Response<BaseResponse<PokeDexObj>> baseJsonResponse) {
                        if (null != baseJsonResponse && null != baseJsonResponse.body()) {
                            List<PokeDexObj> result = baseJsonResponse.body().getList();
                            if (null != result) {
                                PresenterKit.successObjResult(msg, result, PresenterKit.POKE_LIST);
                            }
                        } else {
                            PresenterKit.systemIssues(msg, PresenterKit.FAILED);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ErrorUtils.onFailureResult(activity, t);
                    }
                });
    }

    public void setupRecyclerView(Message msg, Fragment _fragment, RecyclerView rvPokeList, List<PokeDexObj> result) {
        if (null != rvPokeList) {
            rvPokeList.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            rvPokeList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
            rvPokeList.setItemAnimator(new DefaultItemAnimator());
            rvPokeList.setLayoutManager(linearLayoutManager);

            if (result.size() > 0) {
                if (null != pokeListAdapter) {
                    pokeListAdapter.clear();
                    pokeListAdapter.addAll(result);
                    pokeListAdapter.notifyDataSetChanged();
                } else {
                    pokeListAdapter = new PokeListAdapter(activity, result);
                    pokeListAdapter.setOnClickListener(new PokeListAdapter.AdapterOnClickListener() {
                        @Override
                        public void onClickListener(View view, int _position, List<PokeDexObj> pokeDexObjList) {
                            Utils.startPokeDetailFragment(_fragment, _position);
                        }

                        @Override
                        public void onClickMenuListener(View view, int _position, List<PokeDexObj> pokeDexObjList) {
                            CustomPopupMenu.build(view, activity.getResources().getStringArray(R.array.menuListArray)).show((title, position) -> {
                                if (position == 0) {
                                    Utils.startPokeEditFragment(_fragment, false, _position);
                                } else {
                                    deletePokemonDialog(msg, activity, activity.getResources().getString(R.string.dialogNoticeTitle), activity.getResources().getString(R.string.dialogDeleteMessage), activity.getResources().getString(R.string.btnCancel), activity.getResources().getString(R.string.btnOk), _position);
                                }
                            });
                        }
                    });
                    rvPokeList.setAdapter(pokeListAdapter);
                }
            }
        }
    }

    private void deletePokemonDialog(Message msg, Activity _activity, String _title, String _message, String _negativeButton, String _positiveButton, int _position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);
        alertDialog.setTitle(_title).setMessage(_message).setNegativeButton(_negativeButton, (dialog, which) -> dialog.dismiss()).setPositiveButton(_positiveButton, (dialog, which) -> {
            deletePokeDex(msg, _position);
            dialog.dismiss();
        }).setCancelable(false).show();
    }

    private void deletePokeDex(Message msg, int _position) {
        IView view = PresenterKit.viewGetTarget(msg);
        mModel.deletePokemon(Api.SECRET_KEY, _position)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(new ErrorHandleSubscriber<Response<BaseResponse<PokeDexObj>>>(mErrorHandler) {
                    @Override
                    public void onNext(Response<BaseResponse<PokeDexObj>> baseJsonResponse) {
                        PresenterKit.successResult(msg, PresenterKit.POKE_DELETE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ErrorUtils.onFailureResult(activity, t);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}