package converter_lab.sergey.com.converterlab.data.models;

import java.io.Serializable;

/**
 * Created by Moby on 14.03.2018.
 */

public class Price implements Serializable {

    //покупка
    private String ask;
    private boolean isRisenAsk = true;
    //продажа
    private String bid;
    private boolean isRisenBid = true;

    //getters
    public String getAsk() {
        return ask;
    }

    public String getBid() {
        return bid;
    }

    public boolean isRisenAsk() {
        return isRisenAsk;
    }

    public boolean isRisenBid() {
        return isRisenBid;
    }

    //setters

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public void setRisenAsk(boolean risenAsk) {
        isRisenAsk = risenAsk;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setRisenBid(boolean risenBid) {
        isRisenBid = risenBid;
    }
}
