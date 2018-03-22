package converter_lab.sergey.com.converterlab.data.api;

import java.io.IOException;

/**
 * Created by Moby on 13.03.2018.
 */

public class NoConnectivityException extends IOException {

    public final static String CONNECTION_ERROR = "It`seems you are offline, please check your connection and try again";

    @Override
    public String getMessage() {

        return CONNECTION_ERROR;
    }

}
