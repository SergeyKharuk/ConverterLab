package converter_lab.sergey.com.converterlab.data.database;

/**
 * Created by Moby on 14.03.2018.
 */

public class DBConstants {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME                                   = "CurrencyDB";


    public static final String TABLE_ORGANIZATIONS                             = "Organizations";
    public static final String KEY_ID_ORGANIZATIONS                            = "_id";
    public static final String KEY_TITLE_ORGANIZATIONS                         = "title";
    public static final String KEY_REGION_ID_ORGANIZATIONS                     = "regionId";
    public static final String KEY_REGION_VALUE_ORGANIZATIONS                  = "regionValue";
    public static final String KEY_CITY_ID_ORGANIZATIONS                       = "cityId";
    public static final String KEY_CITY_VALUE_ORGANIZATIONS                    = "cityValue";
    public static final String KEY_PHONE_ORGANIZATIONS                         = "phone";
    public static final String KEY_ADDRESS_ORGANIZATIONS                       = "address";
    public static final String KEY_LINK_ORGANIZATIONS                          = "link";
    public static final String KEY_CURRENCIES_JSON                             = "currencies_";


    public static final String TABLE_ORG_TYPES                                 = "OrgTypes";
    public static final String KEY_ORG_TYPE                                    = "OrgType";
    public static final String KEY_ORG_TYPE_VALUE                              = "OrgTypeValue";


    public static final String TABLE_CURRENCIES                                = "Currencies";
    public static final String KEY_CURRENCY_ABBREVIATION                       = "CurrencyAbbreviation";
    public static final String KEY_CURRENCY_VALUE                              = "CurrencyValue";


    public static final String TABLE_REGIONS                                   = "Regions";
    public static final String KEY_REGION_ID                                   = "RegionId";
    public static final String KEY_REGION_VALUE                                = "RegionValue";


    public static final String TABLE_CITIES                                    = "Cities";
    public static final String KEY_CITY_ID                                     = "CityId";
    public static final String KEY_CITY_VALUE                                  = "CityValue";
}
