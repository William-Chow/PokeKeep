package com.william.pokekeep.mvp.model.api;

import com.william.pokekeep.mvp.model.BaseResponse;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.william.pokekeep.mvp.model.api.Api.APP_REST_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * Created by William Chow on 2019-11-18.
 */
public interface ApiServices {

    @Headers({DOMAIN_NAME_HEADER + APP_REST_NAME})
    @GET(Api.GET_ALL_POKEMON)
    Observable<Response<BaseResponse<PokeDexObj>>> getAllPokemon(@Header("Authorization") String _auth);

    @Headers({DOMAIN_NAME_HEADER + APP_REST_NAME})
    @GET(Api.GET_POKEMON)
    Call<PokeDexObj> getPokemon(@Header("Authorization") String _auth, @Path("num") int _num);

    @Headers({DOMAIN_NAME_HEADER + APP_REST_NAME})
    @PATCH(Api.ADD_POKEMON)
    Observable<Response<BaseResponse<PokeDexObj>>> addPokemon(@Header("Authorization") String _auth, @Body PokeDexObj _pokeDexObj);

    @Headers({DOMAIN_NAME_HEADER + APP_REST_NAME})
    @DELETE(Api.DELETE_POKEMON)
    Observable<Response<BaseResponse<PokeDexObj>>> deletePokemon(@Header("Authorization") String _auth, @Path("num") int _num);

    @Headers({DOMAIN_NAME_HEADER + APP_REST_NAME})
    @POST(Api.UPDATE_POKEMON)
    Observable<Response<BaseResponse<PokeDexObj>>> updatePokemon(@Header("Authorization") String _auth, @Path("num") int _num, @Body PokeDexObj _pokeDexObj);
}
