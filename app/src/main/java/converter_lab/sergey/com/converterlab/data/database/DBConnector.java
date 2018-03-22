package converter_lab.sergey.com.converterlab.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import converter_lab.sergey.com.converterlab.data.models.Info;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.data.sharedPref.SharedPref_;
import converter_lab.sergey.com.converterlab.presentation.screens.Detail.DetailContract;
import converter_lab.sergey.com.converterlab.presentation.screens.main.MainContract;

/**
 * Created by Moby on 15.03.2018.
 */

@EBean(scope = EBean.Scope.Singleton)
public class DBConnector implements MainContract.Model, DetailContract.Model {

    @Pref
    protected SharedPref_ myPref;

    private final DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBConnector(Context context) {
        dbHelper  = new DBHelper(context);
    }

    /**
     * Condition: 'response' must be the source (without format regionValue and cityValue fields)!;
     */
    @Override
    public void putData(Info response) {
        if (!myPref.lastUpdate().get().equals(response.getDate())) {
            ArrayList<Organization> oldDataOrganizations = getOrganizations();
            database = getDatabase();
            database.beginTransaction();
            try {
                //put organizations
                database.delete(DBConstants.TABLE_ORGANIZATIONS, null, null);
                for (Organization organization : response.getOrganizations()) {
                    ContentValues cv = new ContentValues();
                    cv.put(DBConstants.KEY_ID_ORGANIZATIONS, organization.getId());
                    cv.put(DBConstants.KEY_TITLE_ORGANIZATIONS, organization.getTitle());
                    cv.put(DBConstants.KEY_REGION_ID_ORGANIZATIONS, organization.getRegionId());
                    cv.put(DBConstants.KEY_REGION_VALUE_ORGANIZATIONS, response.getRegionValue(organization.getRegionId()));
                    cv.put(DBConstants.KEY_CITY_ID_ORGANIZATIONS, organization.getCityId());
                    cv.put(DBConstants.KEY_CITY_VALUE_ORGANIZATIONS, response.getCityValue(organization.getCityId()));
                    cv.put(DBConstants.KEY_PHONE_ORGANIZATIONS, organization.getPhone());
                    cv.put(DBConstants.KEY_ADDRESS_ORGANIZATIONS, organization.getAddress());
                    cv.put(DBConstants.KEY_LINK_ORGANIZATIONS, organization.getLink());

                    //compare price
                    if(oldDataOrganizations.size() != 0) {
                        for (Organization oldOrganization : oldDataOrganizations) {
                            if (organization.getId().equals(oldOrganization.getId())) {

                                Set<Map.Entry<String, LinkedTreeMap<String, String>>> newEntries = organization.getCurrencies().entrySet();
                                Set<Map.Entry<String, LinkedTreeMap<String, String>>> oldEntries = oldOrganization.getCurrencies().entrySet();

                                for (Map.Entry<String, LinkedTreeMap<String, String>> newEntry : newEntries) {
                                    for (Map.Entry<String, LinkedTreeMap<String, String>> oldEntry : oldEntries) {
                                        if (newEntry.getKey().equals(oldEntry.getKey())) {
                                            organization.getCurrencies().get(newEntry.getKey()).put("isRisenAsk", String.valueOf(Float.valueOf(newEntry.getValue().get("ask")) > Float.valueOf(oldEntry.getValue().get("ask"))));
                                            organization.getCurrencies().get(newEntry.getKey()).put("isRisenBid", String.valueOf(Float.valueOf(newEntry.getValue().get("bid")) > Float.valueOf(oldEntry.getValue().get("bid"))));
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }

                    cv.put(DBConstants.KEY_CURRENCIES_JSON, new Gson().toJson(organization.getCurrencies()));

                    database.insert(DBConstants.TABLE_ORGANIZATIONS, null, cv);
                }

                //put org types
                database.delete(DBConstants.TABLE_ORG_TYPES, null, null);
                Set<Map.Entry<String, String>> orgTypeEntries = response.getOrgTypes().entrySet();
                for (Map.Entry<String, String> entry : orgTypeEntries) {
                    ContentValues cv = new ContentValues();
                    cv.put(DBConstants.KEY_ORG_TYPE, entry.getKey());
                    cv.put(DBConstants.KEY_ORG_TYPE_VALUE, entry.getValue());
                    database.insert(DBConstants.TABLE_ORG_TYPES, null, cv);
                }

                //put currencies
                database.delete(DBConstants.TABLE_CURRENCIES, null, null);
                Set<Map.Entry<String, String>> currencyEntries = response.getCurrencies().entrySet();
                for (Map.Entry<String, String> entry : currencyEntries) {
                    ContentValues cv = new ContentValues();
                    cv.put(DBConstants.KEY_CURRENCY_ABBREVIATION, entry.getKey());
                    cv.put(DBConstants.KEY_CURRENCY_VALUE, entry.getValue());
                    database.insert(DBConstants.TABLE_CURRENCIES, null, cv);
                }

                //put regions
                database.delete(DBConstants.TABLE_REGIONS, null, null);
                Set<Map.Entry<String, String>> regionEntries = response.getRegions().entrySet();
                for (Map.Entry<String, String> entry : regionEntries) {
                    ContentValues cv = new ContentValues();
                    cv.put(DBConstants.KEY_REGION_ID, entry.getKey());
                    cv.put(DBConstants.KEY_REGION_VALUE, entry.getValue());
                    database.insert(DBConstants.TABLE_REGIONS, null, cv);
                }

                //put cities
                database.delete(DBConstants.TABLE_CITIES, null, null);
                Set<Map.Entry<String, String>> cityEntries = response.getOrgTypes().entrySet();
                for (Map.Entry<String, String> entry : cityEntries) {
                    ContentValues cv = new ContentValues();
                    cv.put(DBConstants.KEY_CITY_ID, entry.getKey());
                    cv.put(DBConstants.KEY_CITY_VALUE, entry.getValue());
                    database.insert(DBConstants.TABLE_CITIES, null, cv);
                }

                database.setTransactionSuccessful();
                myPref.lastUpdate().put(response.getDate());
            } finally {
                database.endTransaction();
                closeDatabase();
            }

        }
    }

    @Override
    public ArrayList<Organization> getOrganizations() {
        ArrayList<Organization> list = new ArrayList<>();
        database = getDatabase();
        Cursor cursor = database.query(DBConstants.TABLE_ORGANIZATIONS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBConstants.KEY_ID_ORGANIZATIONS);
            int titleIndex = cursor.getColumnIndex(DBConstants.KEY_TITLE_ORGANIZATIONS);
            int regionIdIndex = cursor.getColumnIndex(DBConstants.KEY_REGION_ID_ORGANIZATIONS);
            int regionValueIndex = cursor.getColumnIndex(DBConstants.KEY_REGION_VALUE_ORGANIZATIONS);
            int cityIdIndex = cursor.getColumnIndex(DBConstants.KEY_CITY_ID_ORGANIZATIONS);
            int cityValueIndex = cursor.getColumnIndex(DBConstants.KEY_CITY_VALUE_ORGANIZATIONS);
            int phoneIndex = cursor.getColumnIndex(DBConstants.KEY_PHONE_ORGANIZATIONS);
            int addressIndex = cursor.getColumnIndex(DBConstants.KEY_ADDRESS_ORGANIZATIONS);
            int linkIndex = cursor.getColumnIndex(DBConstants.KEY_LINK_ORGANIZATIONS);
            int currenciesIndex = cursor.getColumnIndex(DBConstants.KEY_CURRENCIES_JSON);

            do {
                Organization organization = new Organization();
                organization.setId(cursor.getString(idIndex));
                organization.setTitle(cursor.getString(titleIndex));
                organization.setRegionId(cursor.getString(regionIdIndex));
                organization.setRegionValue(cursor.getString(regionValueIndex));
                organization.setCityId(cursor.getString(cityIdIndex));
                organization.setCityValue(cursor.getString(cityValueIndex));
                organization.setPhone(cursor.getString(phoneIndex));
                organization.setAddress(cursor.getString(addressIndex));
                organization.setLink(cursor.getString(linkIndex));
//                Class<LinkedTreeMap<String,Price>> classWithNarrowedType = (Class<LinkedTreeMap<String, Price>>)(Class<?>)LinkedTreeMap.class;
                organization.setCurrencies(new Gson().fromJson(cursor.getString(currenciesIndex), LinkedTreeMap.class));

                list.add(organization);
            } while (cursor.moveToNext());
        }

        cursor.close();
        closeDatabase();
        return list;
    }

    @Override
    public Organization getBankById(String id) {
        Organization organization = new Organization();
        database = getDatabase();
        Cursor cursor = database.query(DBConstants.TABLE_ORGANIZATIONS,
                null,
                DBConstants.KEY_ID_ORGANIZATIONS + "= ?", new String[]{id},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBConstants.KEY_ID_ORGANIZATIONS);
            int titleIndex = cursor.getColumnIndex(DBConstants.KEY_TITLE_ORGANIZATIONS);
            int regionIdIndex = cursor.getColumnIndex(DBConstants.KEY_REGION_ID_ORGANIZATIONS);
            int regionValueIndex = cursor.getColumnIndex(DBConstants.KEY_REGION_VALUE_ORGANIZATIONS);
            int cityIdIndex = cursor.getColumnIndex(DBConstants.KEY_CITY_ID_ORGANIZATIONS);
            int cityValueIndex = cursor.getColumnIndex(DBConstants.KEY_CITY_VALUE_ORGANIZATIONS);
            int phoneIndex = cursor.getColumnIndex(DBConstants.KEY_PHONE_ORGANIZATIONS);
            int addressIndex = cursor.getColumnIndex(DBConstants.KEY_ADDRESS_ORGANIZATIONS);
            int linkIndex = cursor.getColumnIndex(DBConstants.KEY_LINK_ORGANIZATIONS);
            int currenciesIndex = cursor.getColumnIndex(DBConstants.KEY_CURRENCIES_JSON);

            organization.setId(cursor.getString(idIndex));
            organization.setTitle(cursor.getString(titleIndex));
            organization.setRegionId(cursor.getString(regionIdIndex));
            organization.setRegionValue(cursor.getString(regionValueIndex));
            organization.setCityId(cursor.getString(cityIdIndex));
            organization.setCityValue(cursor.getString(cityValueIndex));
            organization.setPhone(cursor.getString(phoneIndex));
            organization.setAddress(cursor.getString(addressIndex));
            organization.setLink(cursor.getString(linkIndex));

            organization.setCurrencies(new Gson().fromJson(cursor.getString(currenciesIndex), LinkedTreeMap.class));

        }

        cursor.close();
        closeDatabase();
        return organization;
    }

    public SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

}
