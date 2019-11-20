package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.widget.EditText;
import android.widget.TextView;

import com.william.pokekeep.app.utils.ErrorUtils;
import com.william.pokekeep.app.utils.PresenterKit;
import com.william.pokekeep.mvp.model.BaseResponse;
import com.william.pokekeep.mvp.model.Repository;
import com.william.pokekeep.mvp.model.api.Api;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;
import com.william.pokekeep.mvp.model.entity.PokeDexTypeObj;

import java.util.ArrayList;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
public class PokeEditPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public PokeEditPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
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

    public void callEndPoint(Message msg, boolean isAdd, int pokemonPosition, PokeDexObj _pokeDexObj, EditText etEditPokemonName, int positionPokemonGeneration, TextView tvPokemonTypeMain, TextView tvPokemonTypeSecond, EditText etEditPokemonDescription, int positionRanking, EditText etEditPokemonAddress) {
        IView view = PresenterKit.viewGetTarget(msg);
        if (isAdd) {
            // Add new Pokemon
            PokeDexObj pokeDexObj = new PokeDexObj();
            pokeDexObj.setName(etEditPokemonName.getText().toString().trim());
            pokeDexObj.setGeneration(positionPokemonGeneration);
            pokeDexObj.setDescription(etEditPokemonDescription.getText().toString().trim());
            List<PokeDexTypeObj> type = new ArrayList<>();
            PokeDexTypeObj pokeDexTypeObj = new PokeDexTypeObj();
            pokeDexTypeObj.setMain(tvPokemonTypeMain.getText().toString().trim());
            pokeDexTypeObj.setSecond(tvPokemonTypeSecond.getText().toString().trim());
            type.add(pokeDexTypeObj);
            pokeDexObj.setType(type);
            pokeDexObj.setImage("");
            List<String> power = new ArrayList<>();
            pokeDexObj.setPower(power);
            pokeDexObj.setRanking(positionRanking);
            pokeDexObj.setAddress(etEditPokemonAddress.getText().toString().trim());

            mModel.addPokemon(Api.SECRET_KEY, pokeDexObj)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> view.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(view::hideLoading)
                    .subscribe(new ErrorHandleSubscriber<Response<BaseResponse<PokeDexObj>>>(mErrorHandler) {
                        @Override
                        public void onNext(Response<BaseResponse<PokeDexObj>> baseJsonResponse) {
                            PresenterKit.successResult(msg, PresenterKit.POKE_ADD);
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            ErrorUtils.onFailureResult(activity, t);
                        }
                    });
        } else {
            // Edit an Pokemon
            PokeDexObj pokeDexObj = new PokeDexObj();
            pokeDexObj.setName(etEditPokemonName.getText().toString().trim());
            pokeDexObj.setGeneration(positionPokemonGeneration);
            pokeDexObj.setDescription(etEditPokemonDescription.getText().toString().trim());
            List<PokeDexTypeObj> type = new ArrayList<>();
            PokeDexTypeObj pokeDexTypeObj = new PokeDexTypeObj();
            pokeDexTypeObj.setMain(tvPokemonTypeMain.getText().toString().trim());
            pokeDexTypeObj.setSecond(tvPokemonTypeSecond.getText().toString().trim());
            type.add(pokeDexTypeObj);
            pokeDexObj.setType(type);
            pokeDexObj.setImage(_pokeDexObj.getImage());
            pokeDexObj.setPower(_pokeDexObj.getPower());
            pokeDexObj.setRanking(positionRanking);
            pokeDexObj.setAddress(etEditPokemonAddress.getText().toString().trim());

            mModel.updatePokemon(Api.SECRET_KEY, pokemonPosition, pokeDexObj)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> view.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(view::hideLoading)
                    .subscribe(new ErrorHandleSubscriber<Response<BaseResponse<PokeDexObj>>>(mErrorHandler) {
                        @Override
                        public void onNext(Response<BaseResponse<PokeDexObj>> baseJsonResponse) {
                            PresenterKit.successResult(msg, PresenterKit.POKE_EDIT);
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            ErrorUtils.onFailureResult(activity, t);
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}