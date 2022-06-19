package com.furkanbalci.mesat.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertUtil {

    public static void showAlert(Context context, int titleId, int messageId, DialogListener negativeListener, DialogListener positiveListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setNegativeButton(negativeListener.getButtonTextId(), negativeListener.getListener());
        builder.setPositiveButton(positiveListener.getButtonTextId(), positiveListener.getListener());
        builder.show();

    }

    public static class DialogListener {

        private int buttonTextId;
        private DialogInterface.OnClickListener listener;

        public DialogListener(int buttonTextId, DialogInterface.OnClickListener listener) {
            this.buttonTextId = buttonTextId;
            this.listener = listener;
        }

        public int getButtonTextId() {
            return buttonTextId;
        }

        public void setButtonTextId(int buttonTextId) {
            this.buttonTextId = buttonTextId;
        }

        public DialogInterface.OnClickListener getListener() {
            return listener;
        }

        public void setListener(DialogInterface.OnClickListener listener) {
            this.listener = listener;
        }
    }

}
