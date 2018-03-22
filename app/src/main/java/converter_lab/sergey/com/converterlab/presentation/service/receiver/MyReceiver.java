package converter_lab.sergey.com.converterlab.presentation.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;

import converter_lab.sergey.com.converterlab.Utils.AppConstants;
import converter_lab.sergey.com.converterlab.data.api.Connector;
import converter_lab.sergey.com.converterlab.data.database.DBConnector;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Moby on 20.03.2018.
 */

@EReceiver
public class MyReceiver extends BroadcastReceiver {

    @Bean
    protected Connector connector;

    @Bean
    protected DBConnector dbConnector;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(AppConstants.MY_TAG, "This is alarm. HashCode = " + hashCode() + ". Connector = " + connector.hashCode()  + ". DBConnector = " + dbConnector.hashCode());
        connector
                .getCurrencyAPI()
                .getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    dbConnector.putData(response);
                    Log.d(AppConstants.MY_TAG, "PUT DATA SUCCESS!");
                }, error -> Log.d(AppConstants.MY_TAG, "PUT DATA ERROR!!!!!!!!"));
    }
}
