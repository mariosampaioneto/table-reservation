package com.mfactory.tablereservation.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DialogUtils {

    @Inject
    public DialogUtils() {

    }

    public MaterialDialog showChooseOptionDialog(Context context,
                                                 String title,
                                                 String message,
                                                 String positiveText,
                                                 MaterialDialog.SingleButtonCallback positiveCallback,
                                                 String negativeText,
                                                 MaterialDialog.SingleButtonCallback negativeCallback,
                                                 boolean cancelable) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .onPositive(positiveCallback)
                .onNegative(negativeCallback)
                .cancelable(cancelable)
                .show();
    }

    public MaterialDialog showSimpleDialog(Context context,
                                           String title,
                                           String message,
                                           String btnText) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(btnText)
                .show();
    }
}
