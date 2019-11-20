package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.william.pokekeep.app.utils.ErrorUtils;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.mvp.model.Repository;
import com.william.pokekeep.mvp.model.api.Api;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;

import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
public class PokeDetailPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public PokeDetailPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    public void callPokemonDetail(Message msg, int position) {
        IView view = PresenterKit.viewGetTarget(msg);
        mModel.getPokemon(Api.SECRET_KEY, position).enqueue(new Callback<PokeDexObj>() {
            @Override
            public void onResponse(Call<PokeDexObj> call, Response<PokeDexObj> response) {
                if (null != response && null != response.body()) {
                    PokeDexObj result = response.body();
                    PresenterKit.successObjResult(msg, result, PresenterKit.POKE_DETAIL);
                } else {
                    PresenterKit.systemIssues(msg, PresenterKit.FAILED);
                }
            }

            @Override
            public void onFailure(Call<PokeDexObj> call, Throwable t) {
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