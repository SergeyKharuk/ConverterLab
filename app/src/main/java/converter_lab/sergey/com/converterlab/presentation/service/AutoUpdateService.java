package converter_lab.sergey.com.converterlab.presentation.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import converter_lab.sergey.com.converterlab.Utils.AppConstants;
import converter_lab.sergey.com.converterlab.presentation.service.receiver.MyReceiver_;


/**
 * Created by Moby on 20.03.2018.
 */

@EService
public class AutoUpdateService extends Service {

    @SystemService
    protected AlarmManager manager;

    @Override
    public void onCreate() {
        Log.d(AppConstants.MY_TAG, "Service: " + hashCode() + ". onCreate.");
        Intent intent_ = new Intent(this, MyReceiver_.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 1, intent_, 0);
        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + (10*1000), AlarmManager.INTERVAL_HALF_HOUR, pi);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(AppConstants.MY_TAG, "Service: " + hashCode() + ". onStartCommand.");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(AppConstants.MY_TAG, "Service: " + hashCode() + ". onDestroy.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
