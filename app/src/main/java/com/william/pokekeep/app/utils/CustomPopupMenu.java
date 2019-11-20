package com.william.pokekeep.app.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;

/**
 * Created by William Chow on 2019-11-20.
 */
public class CustomPopupMenu {
    private PopupMenu popup;
    private Typeface font;


    public interface OnItemClickListener {

        void onMenuItemClick(String title, int position);
    }


    private CustomPopupMenu(View fromView) {
        // Context context = new android.view.ContextThemeWrapper(fromView.getContext(), R.style.popup);
        // popup = new PopupMenu(context, fromView);
        popup = new PopupMenu(fromView.getContext(), fromView);
    }

    static public CustomPopupMenu build(View fromView, String[] items) {
        CustomPopupMenu popupMenu = new CustomPopupMenu(fromView);
        int i = 0;
        for (String item : items) {
            popupMenu.popup.getMenu().add(0, i, i, item);
            i++;
        }

        return popupMenu;
    }

    static public CustomPopupMenu build(View fromView, int[] items) {
        CustomPopupMenu popupMenu = new CustomPopupMenu(fromView);

        int i = 0;
        for (int item : items) {
            popupMenu.popup.getMenu().add(0, i, i, item);
            i++;
        }

        return popupMenu;
    }

    static public CustomPopupMenu build(View fromView, String[] items, Typeface font) {
        CustomPopupMenu popupMenu = new CustomPopupMenu(fromView);
        popupMenu.font = font;
        int i = 0;
        for (String item : items) {
            popupMenu.popup.getMenu().add(0, i, i, item);
            i++;
        }

        Menu menu = popupMenu.popup.getMenu();
        for (int j = 0; j < menu.size(); j++) {
            MenuItem mi = menu.getItem(j);
            popupMenu.applyFontToMenuItem(mi);
        }
        return popupMenu;
    }

    static public CustomPopupMenu build(View fromView, int[] items, Typeface font) {
        CustomPopupMenu popupMenu = new CustomPopupMenu(fromView);
        popupMenu.font = font;

        int i = 0;
        for (int item : items) {
            popupMenu.popup.getMenu().add(0, i, i, item);
            i++;
        }

        Menu menu = popupMenu.popup.getMenu();
        for (int j = 0; j < menu.size(); j++) {
            MenuItem mi = menu.getItem(j);
            popupMenu.applyFontToMenuItem(mi);
        }

        return popupMenu;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeFaceSpan("", font, Color.WHITE), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void show(final OnItemClickListener listener) {
        popup.setOnMenuItemClickListener(item -> {
            listener.onMenuItemClick(item.getTitle().toString(), item.getItemId());
            return true;
        });

        popup.show();
    }

}
