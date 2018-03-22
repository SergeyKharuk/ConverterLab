package converter_lab.sergey.com.converterlab.data.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Moby on 13.03.2018.
 */

public class Info implements Serializable {

    //main
    @SerializedName("sourceId")
    private String id;
    private String date;
    private ArrayList<Organization> organizations;

    //helpful
    private LinkedTreeMap<String, String> orgTypes;
    private LinkedTreeMap<String, String> currencies;
    private LinkedTreeMap<String, String> regions;
    private LinkedTreeMap<String, String> cities;

    //getters
    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public LinkedTreeMap<String, String> getOrgTypes() {
        return orgTypes;
    }

    public LinkedTreeMap<String, String> getCurrencies() {
        return currencies;
    }

    public LinkedTreeMap<String, String> getRegions() {
        return regions;
    }

    public LinkedTreeMap<String, String> getCities() {
        return cities;
    }

    //utils
    public String getRegionValue(String key) {
        Set<Map.Entry<String, String>> entries = regions.entrySet();
        for(Map.Entry<String, String> entry : entries){
            if (entry.getKey().equals(key)) return entry.getValue();
        }
        return "no Data";
    }

    public String getCityValue(String key) {
        Set<Map.Entry<String, String>> entries = cities.entrySet();
        for(Map.Entry<String, String> entry : entries){
            if (entry.getKey().equals(key)) return entry.getValue();
        }
        return "no Data";
    }

}
