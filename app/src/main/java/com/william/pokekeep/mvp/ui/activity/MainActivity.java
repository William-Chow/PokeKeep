package com.william.pokekeep.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.william.pokekeep.R;
import com.william.pokekeep.app.utils.Constant;
import com.william.pokekeep.mvp.presenter.MainPresenter;
import com.william.pokekeep.mvp.ui.fragment.MemberInfoFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberLoginFragment;
import com.william.pokekeep.mvp.ui.fragment.MemberRegisterFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeDetailFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeEditFragment;
import com.william.pokekeep.mvp.ui.fragment.PokeListingFragment;
import com.william.pokekeep.mvp.ui.fragment.SplashFragment;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

public class MainActivity extends BaseActivity<MainPresenter> implements IView {

    @BindView(R.id.bnvPokeKeep)
    BottomNavigationView bnvPokeKeep;

    private SplashFragment splashFragment;
    private MemberLoginFragment memberLoginFragment;
    private MemberRegisterFragment memberRegisterFragment;
    private MemberInfoFragment memberInfoFragment;
    private PokeListingFragment pokeListingFragment;
    private PokeDetailFragment pokeDetailFragment;
    private PokeEditFragment pokeEditFragment;

    // Creating FirebaseAuth.
    private FirebaseAuth firebaseAuth;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    public void initData(@Nullable Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        setupNavigation();
        // 显示首页
        showFragment(Constant.SPLASH_FRAGMENT);
    }

    @Nullable
    @Override
    public MainPresenter obtainPresenter() {
        return new MainPresenter(ArtUtils.obtainAppComponentFromContext(this), MainActivity.this.getApplication(), MainActivity.this);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void handleMessage(@NonNull Message message) {

    }

    private void setupNavigation() {
        bnvPokeKeep.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_profile:
                    if (firebaseAuth.getCurrentUser() != null) {
                        showFragment(Constant.MEMBER_INFO_FRAGMENT);
                    } else {
                        ArtUtils.snackbarText(MainActivity.this.getResources().getString(R.string.dialogLoginMessage));
                    }
                    break;
                case R.id.menu_pokeDex:
                    if (firebaseAuth.getCurrentUser() != null) {
                        showFragment(Constant.POKE_LIST_FRAGMENT);
                    } else {
                        ArtUtils.snackbarText(MainActivity.this.getResources().getString(R.string.dialogLoginMessage));
                    }
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    /**
     * @param index index
     */
    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (null != mPresenter) {
            mPresenter.hideFragment(ft, splashFragment, memberLoginFragment, memberRegisterFragment, memberInfoFragment, pokeListingFragment, pokeDetailFragment, pokeEditFragment);
        }
        switch (index) {
            case Constant.SPLASH_FRAGMENT:
                if (splashFragment == null) {
                    splashFragment = new SplashFragment();
                    ft.add(R.id.flContainer, splashFragment, SplashFragment.class.getName());
                } else {
                    ft.show(splashFragment);
                }
                break;
            case Constant.LOGIN_FRAGMENT:
                if (memberLoginFragment == null) {
                    memberLoginFragment = new MemberLoginFragment();
                    ft.add(R.id.flContainer, memberLoginFragment, MemberLoginFragment.class.getName());
                } else {
                    ft.show(memberLoginFragment);
                }
                break;
            case Constant.REGISTER_FRAGMENT:
                if (memberRegisterFragment == null) {
                    memberRegisterFragment = new MemberRegisterFragment();
                    ft.add(R.id.flContainer, memberRegisterFragment, MemberRegisterFragment.class.getName());
                } else {
                    ft.show(memberRegisterFragment);
                }
                break;
            case Constant.MEMBER_INFO_FRAGMENT:
                if (memberInfoFragment == null) {
                    memberInfoFragment = new MemberInfoFragment();
                    ft.add(R.id.flContainer, memberInfoFragment, MemberInfoFragment.class.getName());
                } else {
                    ft.show(memberInfoFragment);
                }
                break;
            case Constant.POKE_LIST_FRAGMENT:
                if (Constant.memberInfoFragment != null) {
                    ft.hide(Constant.memberInfoFragment);
                }
                if (pokeListingFragment == null) {
                    pokeListingFragment = new PokeListingFragment();
                    ft.add(R.id.flContainer, pokeListingFragment, PokeListingFragment.class.getName());
                } else {
                    ft.show(pokeListingFragment);
                }
                break;
            case Constant.POKE_DETAIL_FRAGMENT:
                if (pokeDetailFragment == null) {
                    pokeDetailFragment = new PokeDetailFragment();
                    ft.add(R.id.flContainer, pokeDetailFragment, PokeDetailFragment.class.getName());
                } else {
                    ft.show(pokeDetailFragment);
                }
                break;
            case Constant.POKE_EDIT_FRAGMENT:
                if (pokeEditFragment == null) {
                    pokeEditFragment = new PokeEditFragment();
                    ft.add(R.id.flContainer, pokeEditFragment, PokeEditFragment.class.getName());
                } else {
                    ft.show(pokeEditFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (Constant.memberLoginFragment.isVisible()) {
            Constant.memberLoginFragment.onBackPressed();
        } else if (Constant.memberRegisterFragment.isVisible()) {
            Constant.memberRegisterFragment.onBackPressed();
        } else if (Constant.memberInfoFragment.isVisible()) {
            Constant.memberInfoFragment.onBackPressed();
        } else if (Constant.pokeListingFragment.isVisible()) {
            Constant.pokeListingFragment.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
