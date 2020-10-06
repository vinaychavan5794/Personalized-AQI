package com.fitbitsample.dialog;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.fitbitsample.R;
import com.fitbitsample.databinding.DialogLoadingBinding;
import com.fitbitsample.util.StringUtil;


/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

public class LoadingDialog extends BaseDialog {
    private DialogLoadingBinding dialogLoadingBinding;
    private String[] message;

    public LoadingDialog(Context context, boolean cancelable, String... message) {
        super(context, R.style.fullScreenDialog);
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(cancelable);
        this.message = message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setDimAmount(0.8f);
        }
        dialogLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading, null, false);
        setContentView(dialogLoadingBinding.getRoot());
        setMessage(message);
    }

    public void show(String... message) {
        super.show();
        setMessage(message);
    }

    private void setMessage(String... message) {
        if (message.length > 0) {
            this.message = message;
            dialogLoadingBinding.setMessage(StringUtil.multilineString(message));
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}