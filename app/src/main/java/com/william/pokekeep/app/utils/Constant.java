package com.william.pokekeep.app.utils;

import android.annotation.SuppressLint;

import com.william.pokekeep.mvp.ui.fragment.MemberInfoFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberLoginFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberRegisterFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeDetailFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeEditFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeListingFragment;

/**
 * Created by William Chow on 2019-11-19.
 */
public class Constant {

    public static final int SPLASH_FRAGMENT = 0;
    public static final int LOGIN_FRAGMENT = 1;
    public static final int REGISTER_FRAGMENT = 2;
    public static final int MEMBER_INFO_FRAGMENT = 3;
    public static final int POKE_LIST_FRAGMENT = 4;
    public static final int POKE_DETAIL_FRAGMENT = 5;
    public static final int POKE_EDIT_FRAGMENT = 6;

    @SuppressLint("StaticFieldLeak")
    public static MemberLoginFragment memberLoginFragment = new MemberLoginFragment();
    @SuppressLint("StaticFieldLeak")
    public static MemberRegisterFragment memberRegisterFragment = new MemberRegisterFragment();
    @SuppressLint("StaticFieldLeak")
    public static MemberInfoFragment memberInfoFragment = new MemberInfoFragment();
    @SuppressLint("StaticFieldLeak")
    public static PokeListingFragment pokeListingFragment = new PokeListingFragment();
    @SuppressLint("StaticFieldLeak")
    public static PokeDetailFragment pokeDetailFragment = new PokeDetailFragment();
    @SuppressLint("StaticFieldLeak")
    public static PokeEditFragment pokeEditFragment = new PokeEditFragment();

    public static final String REMEMBER_ME = "REMEMBER_ME";
}
