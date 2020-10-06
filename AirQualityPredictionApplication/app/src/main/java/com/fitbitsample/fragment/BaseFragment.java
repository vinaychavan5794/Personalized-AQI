package com.fitbitsample.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.fitbitsample.R;
import com.fitbitsample.dialog.LoadingDialog;
import com.fitbitsample.util.Trace;

import java.util.Set;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

public class BaseFragment extends Fragment {
    public Context context;
    public Resources resources;
    public LoadingDialog loadingDialog;
//    public AppDatabase appDatabase;

    private static final int PERMISSION_REQUEST_CODE = 27 << 1;

    public void observeClick(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
        resources = getResources();
        context = getActivity();
//        appDatabase = AppDatabase.getDatabase(context);
        loadingDialog = new LoadingDialog(context, false);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Set<String> keys = bundle.keySet();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IntentDump \n");
            stringBuilder.append("-------------------------------------------------------------\n");

            for (String key : keys) {
                stringBuilder.append(key).append("=").append(bundle.get(key)).append("\n");
            }

            stringBuilder.append("-------------------------------------------------------------\n");
            Trace.i(stringBuilder.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    public void resume() {
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    public void pause() {
    }

    @Override
    public void onStart() {
        super.onStart();
        start();
    }

    public void start() {
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    public void stop() {
    }

    public void destroy() {
    }

    public void showLoading(String... message) {
        loadingDialog.show(message);
    }

    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        destroy();
        loadingDialog.dismiss();
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }

    public static void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void toastView(String msg) {
        hideKeyboard(context);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void snackBarView(String msg) {
        hideKeyboard(context);
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.primary));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setMaxLines(5);
        snackbar.show();
    }

}