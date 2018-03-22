package converter_lab.sergey.com.converterlab.data.service;

import converter_lab.sergey.com.converterlab.data.models.Info;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Moby on 13.03.2018.
 */

public interface ICurrencyAPI {

    @GET("currency-cash.json")
    Observable<Info> getCurrencyList();

}
