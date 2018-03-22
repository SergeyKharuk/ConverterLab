package converter_lab.sergey.com.converterlab.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import converter_lab.sergey.com.converterlab.data.models.Info;

/**
 * Created by Moby on 13.03.2018.
 */


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBConstants.TABLE_ORGANIZATIONS
                + "(" + DBConstants.KEY_ID_ORGANIZATIONS + " text primary key,"
                + DBConstants.KEY_TITLE_ORGANIZATIONS + " text,"
                + DBConstants.KEY_REGION_ID_ORGANIZATIONS + " text,"
                + DBConstants.KEY_REGION_VALUE_ORGANIZATIONS + " text,"
                + DBConstants.KEY_CITY_ID_ORGANIZATIONS + " text,"
                + DBConstants.KEY_CITY_VALUE_ORGANIZATIONS + " text,"
                + DBConstants.KEY_PHONE_ORGANIZATIONS + " text,"
                + DBConstants.KEY_ADDRESS_ORGANIZATIONS + " text,"
                + DBConstants.KEY_LINK_ORGANIZATIONS + " text,"
                + DBConstants.KEY_CURRENCIES_JSON + " text)");

        db.execSQL("create table " + DBConstants.TABLE_ORG_TYPES
                + "(" + DBConstants.KEY_ORG_TYPE + " text,"
                + DBConstants.KEY_ORG_TYPE_VALUE + " text)");

        db.execSQL("create table " + DBConstants.TABLE_CURRENCIES
                + "(" + DBConstants.KEY_CURRENCY_ABBREVIATION + " text,"
                + DBConstants.KEY_CURRENCY_VALUE + " text)");

        db.execSQL("create table " + DBConstants.TABLE_REGIONS
                + "(" + DBConstants.KEY_REGION_ID + " text,"
                + DBConstants.KEY_REGION_VALUE + " text)");

        db.execSQL("create table " + DBConstants.TABLE_CITIES
                + "(" + DBConstants.KEY_CITY_ID + " text,"
                + DBConstants.KEY_CITY_VALUE + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void put(Info response) {
        SQLiteDatabase database = getWritableDatabase();
    }
}
