package com.william.pokekeep.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.FragmentTransaction;

import com.william.pokekeep.mvp.model.Repository;
import com.william.pokekeep.mvp.ui.fragment.MemberInfoFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberLoginFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberRegisterFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeDetailFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeEditFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeListingFragment;
import com.william.pokekeep.mvp.ui.fragment.SplashFragment;

import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by William Chow on 2019-11-18.
 */
public class MainPresenter extends BasePresenter<Repository> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private Activity activity;

    public MainPresenter(AppComponent appComponent, Application _mApplication, Activity _activity) {
        super(appComponent.repositoryManager().createRepository(Repository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mApplication = _mApplication;
        this.activity = _activity;
    }

    public void getPokemon(Message msg) {
//        IView view = PresenterKit.viewGetTarget(msg);
//        mModel.getAllPokemon("token bee0e2f3-5f35-4829-8c79-4f1ebbc5a1e8")
//                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))
//                .doOnSubscribe(disposable -> view.showLoading())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(view::hideLoading)
//                .subscribe(new ErrorHandleSubscriber<Response<BaseResponse<PokeDexObj>>>(mErrorHandler) {
//                    @Override
//                    public void onNext(Response<BaseResponse<PokeDexObj>> baseJsonResponse) {
//                        List<PokeDexObj> result = Objects.requireNonNull(baseJsonResponse.body()).getList();
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        super.onError(t);
//                    }
//                });

//        mModel.getPokemon("token bee0e2f3-5f35-4829-8c79-4f1ebbc5a1e8", 0).enqueue(new Callback<PokeDexObj>() {
//            @Override
//            public void onResponse(Call<PokeDexObj> call, Response<PokeDexObj> response) {
//                PokeDexObj result = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<PokeDexObj> call, Throwable t) {
//
//            }
//        });
    }

    public void hideFragment(FragmentTransaction ft, SplashFragment splashFragment, MemberLoginFragment memberLoginFragment, MemberRegisterFragment memberRegisterFragment, MemberInfoFragment memberInfoFragment, PokeListingFragment pokeListingFragment, PokeDetailFragment pokeDetailFragment, PokeEditFragment pokeEditFragment) {
        // 如果不为空，就先隐藏起来
        if (splashFragment != null) {
            ft.hide(splashFragment);
        }
        if (memberLoginFragment != null) {
            ft.hide(memberLoginFragment);
        }
        if (memberRegisterFragment != null) {
            ft.hide(memberRegisterFragment);
        }
        if (memberInfoFragment != null) {
            ft.hide(memberInfoFragment);
        }
        if (pokeListingFragment != null) {
            ft.hide(pokeListingFragment);
        }
        if (pokeDetailFragment != null) {
            ft.hide(pokeDetailFragment);
        }
        if (pokeEditFragment != null) {
            ft.hide(pokeEditFragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
