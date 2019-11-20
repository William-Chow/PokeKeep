package com.william.pokekeep.app.utils;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.fragment.app.Fragment;


/**
 * Created by William Chow on 24/08/2018.
 */
public class PromptAlertDialogKit {

    public static void commonAlertDialogWithNegativeButton(Activity _activity, String _title, String _message, String _positiveButton) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);
        alertDialog.setTitle(_title).setMessage(_message).setNegativeButton(_positiveButton, (dialog, which) -> dialog.dismiss()).setCancelable(false).show();
    }

    public static void commonAlertDialogWithPositiveButtonGoToLogin(Activity _activity, String _title, String _message, String _positiveButton, Fragment _fragment) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);
        alertDialog.setTitle(_title).setMessage(_message).setPositiveButton(_positiveButton, (dialog, which) -> {
            Utils.startLoginFragment(_fragment);
            dialog.dismiss();
        }).setCancelable(false).show();
    }

    public static void commonAlertDialogWithPositiveButtonGoToMemberInfo(Activity _activity, String _title, String _message, String _positiveButton, Fragment _fragment) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);
        alertDialog.setTitle(_title).setMessage(_message).setPositiveButton(_positiveButton, (dialog, which) -> {
            Utils.startMemberInfoFragment(_fragment);
            dialog.dismiss();
        }).setCancelable(false).show();
    }

    public static void commonAlertDialogWithPositiveButtonGoToPokemonList(Activity _activity, String _title, String _message, String _positiveButton, Fragment _fragment) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);
        alertDialog.setTitle(_title).setMessage(_message).setPositiveButton(_positiveButton, (dialog, which) -> {
            Utils.startPokeListingFragment(_fragment);
            dialog.dismiss();
        }).setCancelable(false).show();
    }

}
