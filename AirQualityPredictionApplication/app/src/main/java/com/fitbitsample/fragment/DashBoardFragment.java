package com.fitbitsample.fragment;

import androidx.lifecycle.Observer;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.fitbitsample.FitbitHR;
import com.fitbitsample.FitbitUser;
import com.fitbitsample.JSONParser;
import com.fitbitsample.PM;
import com.fitbitsample.R;
import com.fitbitsample.WeatherClient;
import com.fitbitsample.WeatherData;
import com.fitbitsample.activity.AQP;
import com.fitbitsample.activity.MainActivity;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.databinding.FragmentDashboardBinding;
import com.fitbitsample.db.paper.PaperConstants;
import com.fitbitsample.db.paper.PaperDB;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.DateUtil;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.GetActivityModel;
import com.fitbitsample.viewmodel.GetDeviceModel;
import com.fitbitsample.viewmodel.GetHrModel;
import com.fitbitsample.viewmodel.GetSleepModel;
import com.fitbitsample.viewmodel.GetUserModel;
import com.fitbitsample.viewmodel.response.Device;
import com.fitbitsample.viewmodel.response.HeartRate;
import com.fitbitsample.viewmodel.response.Steps.ActivityInfo;
import com.fitbitsample.viewmodel.response.UserInfo;
import com.fitbitsample.viewmodel.response.sleep.SleepInfo;

import org.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashBoardFragment extends BaseFragment {

    private FragmentDashboardBinding dashboardBinding;


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
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        return dashboardBinding.getRoot();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    public void resume() {
        if (getUserVisibleHint()) {
            ((MainActivity) context).setTitle(getString(R.string.dashboard));
            getUserProfile();
            getDevices();
            getActivityInfo();
            getHeartRate();
            getSleep();
        }
    }

    private void showDevicesDialog() {
        final List<Device> myDevices = PaperDB.getInstance().get().read(PaperConstants.DEVICES, null);
        if (myDevices != null) {
            final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(context);
            for (Device device : myDevices) {
                adapter.add(new MaterialSimpleListItem.Builder(context)
                        .content(device.getDeviceVersion())
                        .backgroundColor(Color.WHITE)
                        .build());
            }

            new MaterialDialog.Builder(context)
                    .title("Choose your Device")
                    .adapter(adapter, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            AppPreference.getInstance().putBoolean(PrefConstants.HAVE_DEVICE_ID, true);
                            AppPreference.getInstance().putString(PrefConstants.MY_DEVICE_ID, myDevices.get(which).getId());
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void getUserProfile() {
        GetUserModel getUserModel = new GetUserModel(1);
        getUserModel.run(context, null).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get userInfo failed");
                } else {
                    Trace.i("userInfo fetching is done");
                    updateUi();
                }
            }
        });

        /*try {
            Log.i("Autho",": "+AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION));
            (new FitbitAsyncTask().execute(AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION))).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/




    }

    private void getHeartRate() {
        GetHrModel hrModel = new GetHrModel(1);
        hrModel.run(context,DateUtil.convertDateFormat(new Date()), "1d").getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Log.i("Integer Code",Integer.toString(integer));
                    Trace.i("get Hr failed");
                } else {
                    Trace.i("Hr fetching is done");
                    updateHr();
                }
            }
        });
    }

    private void getSleep() {
        GetSleepModel sleepModel = new GetSleepModel(1);
        sleepModel.run(context, DateUtil.convertDateFormat(new Date())).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get sleep failed");
                } else {
                    Trace.i("sleep fetching is done");
                    updateSleep();
                }
            }
        });
    }

    private void getActivityInfo() {
        GetActivityModel activityModel = new GetActivityModel(1);
        activityModel.run(context,DateUtil.convertDateFormat(new Date())).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("get sleep failed");
                } else {
                    Trace.i("sleep fetching is done");
                    updateActivities();
                }
            }
        });
    }

    private void getDevices() {
        GetDeviceModel getDeviceModel = new GetDeviceModel(1);
        getDeviceModel.run(context, null).getData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null && integer > 0) {
                    Trace.i("Device fetching failed");
                } else {
                    Trace.i("Device fetching is done");
                    showDevicesDialog();
                }
            }
        });
    }

    private void updateUi() {
        /*Intent intent = new Intent(getActivity(), AQP.class);
        startActivity(intent);*/
        UserInfo userInfo = PaperDB.getInstance().get().read(PaperConstants.PROFILE, null);
        if (userInfo != null) {
            dashboardBinding.setUser(userInfo.toString());
        }
    }

    private void updateSleep() {
        SleepInfo sleepInfo = PaperDB.getInstance().get().read(PaperConstants.SLEEP, null);
        if (sleepInfo != null) {
            dashboardBinding.setSleep(sleepInfo.toString());
        }
    }

    private void updateHr() {
        HeartRate heartRate = PaperDB.getInstance().get().read(PaperConstants.HEART_RATE, null);
        if (heartRate != null) {
            dashboardBinding.setHr(heartRate.toString());
        }
    }

    private void updateActivities() {
        ActivityInfo activityInfo = PaperDB.getInstance().get().read(PaperConstants.ACTIVITY_INFO, null);
        if (activityInfo != null) {
            dashboardBinding.setActivity(activityInfo.toString());
        }
    }


}
