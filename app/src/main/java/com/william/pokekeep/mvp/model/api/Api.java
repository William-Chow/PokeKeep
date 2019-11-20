package com.william.pokekeep.mvp.model.api;

/**
 * Created by William Chow on 2019-11-18.
 */
public interface Api {

    String SECRET_KEY = "token bee0e2f3-5f35-4829-8c79-4f1ebbc5a1e8";
    String APP_REST_NAME = "APP_DOMAIN_NAME";
    String APP_REST_URL = "https://jsonbin.org/";

    // Get Pokemon
    String GET_ALL_POKEMON = "me/pokedex/";

    // Select Pokemon
    String GET_POKEMON = "me/pokedex/list/{num}/";

    // Add Pokemon
    String ADD_POKEMON = "me/pokedex/list";

    // Delete Pokemon
    String DELETE_POKEMON = "me/pokedex/list/{num}/";

    // Edit Pokemon
    String UPDATE_POKEMON = "me/pokedex/list/{num}/";
}
