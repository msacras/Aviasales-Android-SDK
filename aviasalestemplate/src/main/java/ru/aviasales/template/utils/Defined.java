package ru.aviasales.template.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ru.aviasales.core.locale.CountryCodes;
import ru.aviasales.core.locale.LanguageCodes;
import ru.aviasales.core.locale.LocaleUtil;
import ru.aviasales.core.utils.CoreDefined;

public class Defined extends CoreDefined {

	public static final String SEARCH_SERVER_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SEARCH_FORM_DATE_FORMAT = "dd MMMM, yyyy";
	public static final String SEARCH_FORM_WEEK_DAY_FORMAT = "EEEE";

	public static final String AM_PM_RESULTS_TIME_FORMAT = "hh:mma";
	public static final String RESULTS_TIME_FORMAT = "HH:mm";
	public static final String RESULTS_SHORT_DATE_FORMAT = "d MMM";
	public static final String UTC_TIMEZONE = "Etc/UTC";

	public static final String TICKET_FLIGHT_TIME_FORMAT = "HH:mm";
	public static final String AM_PM_TICKET_FLIGHT_TIME_FORMAT = "hh:mma";
	public static final String TICKET_SHORT_DATE_FORMAT = "d MMM, EE";

	public static final String FILTERS_TIME_FORMAT = "HH:mm";
	public static final String AM_PM_FILTERS_TIME_FORMAT = "hh:mma";

	private static final String DEFAULT_CURRENCY = "RUB";
	private static final String MD_DEFAULT_CURRENCY = "MDL";
	private static final String EN_DEFAULT_CURRENCY = "USD";
	private static final String EN_GB_DEFAULT_CURRENCY = "GBP";
	private static final String EN_AU_DEFAULT_CURRENCY = "AUD";
	private static final String EN_IE_DEFAULT_CURRENCY = "IEP";
	private static final String ES_DEFAULT_CURRENCY = "EUR";
	private static final String IT_DEFAULT_CURRENCY = "EUR";
	private static final String DE_DEFAULT_CURRENCY = "EUR";
	private static final String FR_DEFAULT_CURRENCY = "EUR";
	private static final String TH_DEFAULT_CURRENCY = "THB";


	private static Map<String, String> CURRENCY_MAP;

	static {
		Map<String, String> aMap = new LinkedHashMap<String, String>();
		if (LocaleUtil.getLocale().equals(LanguageCodes.RUSSIAN)) {
			aMap.put("MDL", "Молдавский лей");
			aMap.put("EUR", "Евро");
			aMap.put("USD", "Доллар США");
			aMap.put("RUB", "Российский рубль");
			aMap.put("UAH", "Украинская гривна");
			aMap.put("KZT", "Казахстанский тенге");
			aMap.put("ILS", "Израильский шекель");
			aMap.put("CHF", "Швейцарский франк");
			aMap.put("GBP", "Фунт стерлингов");
			aMap.put("AUD", "Австралийский доллар");
			aMap.put("CAD", "Канадский доллар");
			aMap.put("CNY", "Китайский юань");
			aMap.put("JPY", "Японская йена");
			aMap.put("AZN", "Азербайджанский манат");
			aMap.put("AMD", "Армянский драм");
			aMap.put("BYN", "Белорусский рубль");
			aMap.put("KGS", "Киргизский сом");
			aMap.put("TJS", "Таджикский сомони");
			aMap.put("UZS", "Узбекский сум");
			aMap.put("GEL", "Грузинский лари");
			aMap.put("TMT", "Туркменский манат");
		} else if (LocaleUtil.getLocale().equals(LanguageCodes.ENGLISH)) {
			aMap.put("MDL", "Moldovan Leu");
			aMap.put("EUR", "Euro");
			aMap.put("USD", "U.S. dollar");
			aMap.put("RUB", "Russian ruble");
			aMap.put("UAH", "Ukrainian hryvnia");
			aMap.put("KZT", "Kazakhstan tenge");
			aMap.put("ILS", "Israeli shekel");
			aMap.put("CHF", "Swiss frank");
			aMap.put("GBP", "Great Britain pound");
			aMap.put("AUD", "Australian dollar");
			aMap.put("CAD", "Canadian dollar");
			aMap.put("CNY", "Chinese yuan");
			aMap.put("JPY", "Japanese yen");
			aMap.put("AZN", "Azerbaijani manat");
			aMap.put("AMD", "Armenian drams");
			aMap.put("BYN", "The Belarusian ruble");
			aMap.put("KGS", "Kirghiz som");
			aMap.put("TJS", "Tajik somoni");
			aMap.put("UZS", "Uzbek sum");
			aMap.put("GEL", "Georgian lari");
			aMap.put("TMT", "Turkmen manat");
		} else {
			aMap.put("MDL", "Leul moldovenesc");
			aMap.put("EUR", "Euro");
			aMap.put("USD", "Dolarul american");
			aMap.put("RUB", "Ruble rusești");
			aMap.put("UAH", "Hrivna ucraineană");
			aMap.put("KZT", "Tenge kazahstan");
			aMap.put("ILS", "Shekel israeli");
			aMap.put("CHF", "Franc elvețian");
			aMap.put("GBP", "Lire sterline");
			aMap.put("AUD", "Dolarul australian");
			aMap.put("CAD", "Dolarul canadian");
			aMap.put("CNY", "Yuan chinez");
			aMap.put("JPY", "Yenul japonez");
			aMap.put("AZN", "Manat azerbaijan");
			aMap.put("AMD", "Drame armeane");
			aMap.put("BYN", "Rublele belaruse");
			aMap.put("KGS", "Som kirghiz");
			aMap.put("TJS", "Somoni tajik");
			aMap.put("UZS", "Suma uzbekistă");
			aMap.put("GEL", "Lari georgian");
			aMap.put("TMT", "Manat turkmen");
		}
		CURRENCY_MAP = Collections.unmodifiableMap(aMap);
	}

	private static final String AIRLINE_LOGO_TEMPLATE_URL = "https://{SearchUrl}/images/airline/{Width}/{Height}/{IATA}.png";

	public static String getAirlineLogoTemplateUrl() {
		return getUrl(AIRLINE_LOGO_TEMPLATE_URL);
	}

	public static String getDefaultCurrency() {
		String locale = LocaleUtil.getLocale();

		if (locale.equalsIgnoreCase(LanguageCodes.ENGLISH + "_" + CountryCodes.GREAT_BRITAIN)) {
			return EN_GB_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.ENGLISH + "_" + CountryCodes.GREAT_BRITAIN)) {
			return EN_AU_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.ENGLISH + "_" + CountryCodes.GREAT_BRITAIN)) {
			return EN_IE_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.SPANISH)) {
			return ES_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.GERMAN)) {
			return DE_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.ITALIAN)) {
			return IT_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.THAI)) {
			return TH_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.FRENCH)) {
			return FR_DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.RUSSIAN + "_" + LanguageCodes.RUSSIAN)
				|| locale.equalsIgnoreCase(LanguageCodes.RUSSIAN)) {
			return DEFAULT_CURRENCY;
		} else if (locale.equalsIgnoreCase(LanguageCodes.ROMANIAN)) {
			return MD_DEFAULT_CURRENCY;
		} else
			return EN_DEFAULT_CURRENCY;
	}

	public static Map<String, String> getCurrenciesArray() {
		return CURRENCY_MAP;
	}
}
