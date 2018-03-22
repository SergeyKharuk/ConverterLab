package converter_lab.sergey.com.converterlab.data.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.EBean;

import java.util.concurrent.TimeUnit;

import converter_lab.sergey.com.converterlab.data.service.ICurrencyAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Moby on 13.03.2018.
 */

@EBean(scope = EBean.Scope.Singleton)
public class Connector {

    private final Retrofit mRetrofit;
    private static Connector    INSTANCE_CONNECTOR;
    private Context context;


    /**
     * Configure Retrofit instance with and GsonConverter
     */
    protected Connector(Context context) {
        final OkHttpClient.Builder clientBuilder = Config.getClientBuilderWithCookieAction();

        this.context = context;
        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);

        final OkHttpClient client = clientBuilder
                .addInterceptor(new ConnectivityInterceptor(context))
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    /**
     * @return retrofit rest interface with pet shit related methods
     */

    public ICurrencyAPI getCurrencyAPI(){
        return mRetrofit.create(ICurrencyAPI.class);
    }


}
