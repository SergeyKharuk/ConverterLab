package converter_lab.sergey.com.converterlab.data.models;

import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;

/**
 * Created by Moby on 14.03.2018.
 */

public class Organization implements Serializable {

    private String id;
    private String title;
    private String regionId;
    private String regionValue;
    private String cityId;
    private String cityValue;
    private String phone;
    private String address;
    private String link;
    private LinkedTreeMap<String, LinkedTreeMap<String, String>> currencies;

    //getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public String getRegionValue() {
        return regionValue;
    }

    public String getCityValue() {
        return cityValue;
    }

    public LinkedTreeMap<String, LinkedTreeMap<String, String>> getCurrencies() {
        return currencies;
    }

    //setters
    public void setRegionValue(String regionValue) {
        this.regionValue = regionValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCurrencies(LinkedTreeMap<String, LinkedTreeMap<String, String>> currencies) {
        this.currencies = currencies;
    }
}

