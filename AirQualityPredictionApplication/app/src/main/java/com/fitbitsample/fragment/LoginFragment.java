package com.fitbitsample.fragment;


import androidx.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fitbitsample.FitbitHR;
import com.fitbitsample.FitbitUser;
import com.fitbitsample.JSONParser;
import com.fitbitsample.WeatherClient;
import com.fitbitsample.activity.AQP;
import com.fitbitsample.activity.MainActivity;
import com.fitbitsample.constant.AppConstants;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.R;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.GetAccessTokenModel;

import org.json.JSONException;

import java.util.Date;


public class LoginFragment extends BaseFragment {


    Boolean haveToken;
    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private CustomTabsClient mClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsIntent customTabsIntent;
    private Button logInButton;
    private Button launchButton;
    private String gender="";
    private String age="";
    private String hr="";
    private String lastSyncVal="";
    private String userID="";
    private int timeStampExpiry;
    private long difference;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
        resources = getResources();
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        haveToken = AppPreference.getInstance().getBoolean(PrefConstants.HAVE_AUTHORIZATION);
        difference=new Date().getTime() - AppPreference.getInstance().getLong(PrefConstants.TIME_STAMP);
        timeStampExpiry = (int) (difference / (1000 * 60 * 60));
        logInButton = view.findViewById(R.id.button);
        launchButton =  view.findViewById(R.id.launchAQP);


        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        speedUpChromeTabs();
        CustomTabsClient.bindCustomTabsService(getActivity(), CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
        customTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(ContextCompat.getColor(getActivity(), R.color.primary))
                .setShowTitle(true)
                .build();
        //Log.i("Auth","inside onActivity"+AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION));
        //Log.i("Auth","inside onActivity havetoken"+AppPreference.getInstance().getString(PrefConstants.HAVE_AUTHORIZATION));

        if(haveToken && timeStampExpiry < 8 ){
            logInButton.setEnabled(false);
            launchButton.setEnabled(true);
        }else{
            logInButton.setEnabled(true);
            launchButton.setEnabled(false);
        }

        /** client_id, which is provided by fitbit while registering app
            redirect_uri, provided by you when register app in fitbit.
        */


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!haveToken) {
                    String url = "https://www.fitbit.com/oauth2/authorize?" +
                            "response_type=code" +
                            "&client_id=" +AppConstants.CLIENT_ID+
                            "&expires_in=31536000" +
                            "&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight" +
                            "&redirect_uri=" + AppConstants.REDIRECT_URI+
                            "&prompt=login";
                    customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
                /*} else {
                    Toast.makeText(getActivity(),
                            "Already logged in. Please go Back", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FitbitAsyncTask().execute(AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION));
            }
        });

        //verifyAuthCode();
    }

    private void verifyAuthCode() {
        if (AppPreference.getInstance().getBoolean(PrefConstants.IS_CODE_RECEIVED) && !haveToken) {
            GetAccessTokenModel accessTokenModel = new GetAccessTokenModel(2);
            accessTokenModel.run(context, null).getData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    if (integer != null && integer > 0) {
                        Trace.i("AccessToken fetching failed");
                    } else {
                        Trace.i("Access Token fetching is done");
                        //showDashboard();
                    }
                }
            });
        }
    }

    private void showDashboard() {
        ((MainActivity) context).showDashboard();
    }

    private void speedUpChromeTabs() {
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                mClient = customTabsClient;
                mClient.warmup(0L);
                mCustomTabsSession = mClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };
    }


    public void resume() {
        if (getUserVisibleHint()) {
            ((MainActivity) context).setTitle(getString(R.string.login));

        }
        if (!haveToken) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        getFragmentManager().popBackStack();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    class FitbitAsyncTask extends AsyncTask<String, Void, String[]> {


        protected String[] doInBackground(String... params)  {

            FitbitHR fitbitHR=new FitbitHR();
            FitbitUser fitbitUser =new FitbitUser();
            String hearRateData = ( (new WeatherClient()).getHeartRateInformation(params[0]));
            String userData = ( (new WeatherClient()).getUserInformation(params[0]));
            String lastSync=( (new WeatherClient()).getLastSyncInformation(params[0]));
            String lastSyncValue="";
            try {

                fitbitHR = JSONParser.getHeartRate(hearRateData);
                fitbitUser = JSONParser.getUserInfo(userData);
                lastSyncValue=JSONParser.getLastSync(lastSync);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new String[]{Integer.toString(fitbitHR.getHeartRate()),fitbitUser.getGender(),
                    Integer.toString(fitbitUser.getAge()),lastSyncValue,fitbitUser.getUserID()};

        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            hr=result[0];
            gender=result[1];
            age=result[2];
            lastSyncVal=result[3];
            userID=result[4];
            startAQP();

        }
    }

    public void startAQP(){

        Intent intent = new Intent(getActivity(), AQP.class);
        intent.putExtra("HR",hr);
        intent.putExtra("GENDER",gender);
        intent.putExtra("AGE",age);
        intent.putExtra("LASTSYNC",lastSyncVal);
        intent.putExtra("USERID",userID);
        startActivity(intent);
    }


}
