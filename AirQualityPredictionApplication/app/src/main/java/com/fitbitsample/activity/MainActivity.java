package com.fitbitsample.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.fitbitsample.FitbitHR;
import com.fitbitsample.FitbitUser;
import com.fitbitsample.JSONParser;
import com.fitbitsample.WeatherClient;
import com.fitbitsample.databinding.ActivityMainBinding;
import com.fitbitsample.fragment.DashBoardFragment;
import com.fitbitsample.fragment.LoginFragment;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.R;
import com.fitbitsample.network.RestServices;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.FragmentStack;
import com.fitbitsample.util.FragmentStackHandler;
import com.fitbitsample.util.StringUtil;
import com.fitbitsample.viewmodel.response.Alarms;
import com.fitbitsample.viewmodel.response.Device;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.this.getClass().getSimpleName();
    private ActivityMainBinding activityMainBinding;
    private FragmentStackHandler fragmentStackHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentStackHandler = new FragmentStackHandler(getSupportFragmentManager(), new FragmentStack());
        init();

    }

    private void init() {
        checkStatus();
    }

    public void setTitle(String title) {
        activityMainBinding.toolbarTitle.setText(StringUtil.capitalizeFirstLetter(title));
    }

    private void checkStatus() {
        boolean haveToken = AppPreference.getInstance().getBoolean(PrefConstants.HAVE_AUTHORIZATION, false);
        /*if (!haveToken) {
            showLogin();
        } else {
            showDashboard();
        }*/
        showLogin();
    }

    public void showDashboard() {




        if (!(fragmentStackHandler.getLastFragment() instanceof DashBoardFragment)) {
            Fragment fragment = new DashBoardFragment();
            fragmentStackHandler.startAndAddFragmentAndCloseAllLastFragmentInStack(fragment, activityMainBinding.homeContainer.getId());
        }
    }

    private void showLogin() {
        if (!(fragmentStackHandler.getLastFragment() instanceof LoginFragment)) {
            Fragment fragment = new LoginFragment();
            fragmentStackHandler.startAndAddFragmentAndCloseAllLastFragmentInStack(fragment, activityMainBinding.homeContainer.getId());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
