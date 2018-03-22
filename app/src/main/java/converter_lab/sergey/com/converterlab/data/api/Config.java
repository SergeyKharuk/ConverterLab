package converter_lab.sergey.com.converterlab.data.api;

import okhttp3.OkHttpClient;

/**
 * Created by Moby on 13.03.2018.
 */

public final class Config {

    static OkHttpClient.Builder getClientBuilderWithCookieAction() {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//        clientBuilder.addNetworkInterceptor(new AddCookiesInterceptor());
//        clientBuilder.addNetworkInterceptor(new ReceivedCookiesInterceptor());

        return clientBuilder;
    }

}
