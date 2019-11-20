package com.william.pokekeep.mvp.model;

import com.william.pokekeep.mvp.model.api.ApiServices;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;

import io.reactivex.Observable;
import me.jessyan.art.mvp.IModel;
import me.jessyan.art.mvp.IRepositoryManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by William Chow on 2019-11-18.
 */
public class Repository implements IModel {

    private IRepositoryManager mManager;

    public Repository(IRepositoryManager manager) {
        this.mManager = manager;
    }

    public Observable<Response<BaseResponse<PokeDexObj>>> getAllPokemon(String _auth) {
        return mManager.createRetrofitService(ApiServices.class).getAllPokemon(_auth);
    }

    public Call<PokeDexObj> getPokemon(String _auth, int _num) {
        return mManager.createRetrofitService(ApiServices.class).getPokemon(_auth, _num);
    }

    public Observable<Response<BaseResponse<PokeDexObj>>> addPokemon(String _auth, PokeDexObj _pokeDexObj) {
        return mManager.createRetrofitService(ApiServices.class).addPokemon(_auth, _pokeDexObj);
    }

    public Observable<Response<BaseResponse<PokeDexObj>>> deletePokemon(String _auth, int _num) {
        return mManager.createRetrofitService(ApiServices.class).deletePokemon(_auth, _num);
    }

    public Observable<Response<BaseResponse<PokeDexObj>>> updatePokemon(String _auth, int _num, PokeDexObj _pokeDexObj) {
        return mManager.createRetrofitService(ApiServices.class).updatePokemon(_auth, _num, _pokeDexObj);
    }

    @Override
    public void onDestroy() {

    }
}
