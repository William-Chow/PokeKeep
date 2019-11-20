package com.william.pokekeep.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.william.pokekeep.R;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by William Chow on 2019-11-18.
 */
public class Utils {

    private static final String REGEX_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    static boolean notBlank(String str) {
        return str != null && !"".equals(str.trim());
    }

    /**
     * @param _activity _activity
     *                  Hide Keyboard
     */
    public static void hideKeyboard(Activity _activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) _activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(_activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
        }
    }

    public static void startLoginFragment(Fragment _fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.memberLoginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void startRegisterFragment(Fragment _fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.memberRegisterFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    static void startMemberInfoFragment(Fragment _fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.memberInfoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    static void startPokeListingFragment(Fragment _fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.pokeListingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void startPokeDetailFragment(Fragment _fragment, int _position) {
        Bundle bundle = new Bundle();
        bundle.putInt("key", _position);
        Constant.pokeDetailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.pokeDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void startPokeEditFragment(Fragment _fragment, boolean _isAdd, int _position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", _isAdd);
        bundle.putInt("key", _position);
        Constant.pokeEditFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(_fragment.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, Constant.pokeEditFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * presenterKitFail
     *
     * @param activity activity
     * @param _title   _title
     * @param _message _message
     * @param _button  _button
     */
    public static void presenterKitFail(Activity activity, String _title, String _message, String _button) {
        PromptAlertDialogKit.commonAlertDialogWithNegativeButton(activity, _title, _message, _button);
    }

    public static int returnType(String _type) {
        if (_type.equalsIgnoreCase("Grass")) {
            return R.drawable.drawable_pokemon_grass;
        } else if (_type.equalsIgnoreCase("Fire")) {
            return R.drawable.drawable_pokemon_fire;
        } else if (_type.equalsIgnoreCase("Water")) {
            return R.drawable.drawable_pokemon_water;
        } else if (_type.equalsIgnoreCase("Fighting")) {
            return R.drawable.drawable_pokemon_fighting;
        } else if (_type.equalsIgnoreCase("Steel")) {
            return R.drawable.drawable_pokemon_steel;
        } else if (_type.equalsIgnoreCase("Poison")) {
            return R.drawable.drawable_pokemon_poison;
        } else if (_type.equalsIgnoreCase("Flying")) {
            return R.drawable.drawable_pokemon_flying;
        } else if (_type.equalsIgnoreCase("Ground")) {
            return R.drawable.drawable_pokemon_ground;
        } else if (_type.equalsIgnoreCase("Psychic")) {
            return R.drawable.drawable_pokemon_psychic;
        } else if (_type.equalsIgnoreCase("Ghost")) {
            return R.drawable.drawable_pokemon_ghost;
        } else if (_type.equalsIgnoreCase("Ice")) {
            return R.drawable.drawable_pokemon_ice;
        } else if (_type.equalsIgnoreCase("Normal")) {
            return R.drawable.drawable_pokemon_normal;
        } else if (_type.equalsIgnoreCase("Rock")) {
            return R.drawable.drawable_pokemon_rock;
        } else if (_type.equalsIgnoreCase("Dark")) {
            return R.drawable.drawable_pokemon_dark;
        } else if (_type.equalsIgnoreCase("Dragon")) {
            return R.drawable.drawable_pokemon_dragon;
        } else if (_type.equalsIgnoreCase("Bug")) {
            return R.drawable.drawable_pokemon_bug;
        } else if (_type.equalsIgnoreCase("Fairy")) {
            return R.drawable.drawable_pokemon_fairy;
        } else if (_type.equalsIgnoreCase("Electric")) {
            return R.drawable.drawable_pokemon_electric;
        } else {
            return R.drawable.drawable_poke_dex;
        }
    }

    public static int returnNumberType(String _type) {
        if (_type.equalsIgnoreCase("Grass")) {
            return 0;
        } else if (_type.equalsIgnoreCase("Fire")) {
            return 1;
        } else if (_type.equalsIgnoreCase("Water")) {
            return 2;
        } else if (_type.equalsIgnoreCase("Fighting")) {
            return 3;
        } else if (_type.equalsIgnoreCase("Steel")) {
            return 4;
        } else if (_type.equalsIgnoreCase("Poison")) {
            return 5;
        } else if (_type.equalsIgnoreCase("Flying")) {
            return 6;
        } else if (_type.equalsIgnoreCase("Ground")) {
            return 7;
        } else if (_type.equalsIgnoreCase("Psychic")) {
            return 8;
        } else if (_type.equalsIgnoreCase("Ghost")) {
            return 9;
        } else if (_type.equalsIgnoreCase("Ice")) {
            return 10;
        } else if (_type.equalsIgnoreCase("Normal")) {
            return 11;
        } else if (_type.equalsIgnoreCase("Rock")) {
            return 12;
        } else if (_type.equalsIgnoreCase("Dark")) {
            return 13;
        } else if (_type.equalsIgnoreCase("Dragon")) {
            return 14;
        } else if (_type.equalsIgnoreCase("Bug")) {
            return 15;
        } else if (_type.equalsIgnoreCase("Fairy")) {
            return 16;
        } else if (_type.equalsIgnoreCase("Electric")) {
            return 17;
        } else {
            return 18;
        }
    }

    public static String returnGeneration(int _generation) {
        if (_generation == 1) {
            return "Pokémon FireRed and LeafGreen";
        } else if (_generation == 2) {
            return "Pokémon Gold and Silver";
        } else if (_generation == 3) {
            return "Pokémon Ruby and Sapphire";
        } else if (_generation == 4) {
            return "Pokémon Diamond and Pearl";
        } else if (_generation == 5) {
            return "Pokémon Black and White";
        } else if (_generation == 6) {
            return "Pokémon X and Y";
        } else {
            return "Unknown";
        }
    }

    public static int returnGenerationNumber(String _generation) {
        if (_generation.equalsIgnoreCase("Pokémon FireRed and LeafGreen")) {
            return 1;
        } else if (_generation.equalsIgnoreCase("Pokémon Gold and Silver")) {
            return 2;
        } else if (_generation.equalsIgnoreCase("Pokémon Ruby and Sapphire")) {
            return 3;
        } else if (_generation.equalsIgnoreCase("Pokémon Diamond and Pearl")) {
            return 4;
        } else if (_generation.equalsIgnoreCase("Pokémon Black and White")) {
            return 5;
        } else if (_generation.equalsIgnoreCase("Pokémon X and Y")) {
            return 6;
        } else {
            return 0;
        }
    }

    public static int returnRankingNumber(int _ranking) {
        if (_ranking == 1) {
            return 0;
        } else if (_ranking == 2) {
            return 1;
        } else if (_ranking == 3) {
            return 2;
        } else {
            return 0;
        }
    }

    public static int returnRankingNumber(String _ranking) {
        if (_ranking.equalsIgnoreCase("Ranking 1")) {
            return 1;
        } else if (_ranking.equalsIgnoreCase("Ranking 2")) {
            return 2;
        } else if (_ranking.equalsIgnoreCase("Ranking 3")) {
            return 3;
        } else {
            return 0;
        }
    }
}
