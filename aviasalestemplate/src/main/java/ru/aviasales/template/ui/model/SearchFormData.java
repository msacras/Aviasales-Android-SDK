package ru.aviasales.template.ui.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.aviasales.core.http.utils.CoreDateUtils;
import ru.aviasales.core.search.params.Passengers;
import ru.aviasales.core.search.params.SearchParams;
import ru.aviasales.core.search_airports.object.PlaceData;
import ru.aviasales.template.R;
import ru.aviasales.template.utils.DateUtils;
import ru.aviasales.template.utils.Defined;
import ru.aviasales.template.utils.Utils;

public class SearchFormData {

	private static final String SEARCH_PARAM_ORIGIN_NAME = "search_param_origin_name";
	private static final String SEARCH_PARAM_DESTINATION_NAME = "search_param_destination_name";
	private static final String SEARCH_PARAM_DEPART_DATE = "search_param_depart_date";
	private static final String SEARCH_PARAM_RETURN_DATE = "search_param_return_date";

	private static final String EXTRA_RETURN_ENABLED = "extra-return_enabled";
	private static final String COMPLEX_SEARCH_SEGMENTS_COUNT = "open_jaw_segments_count";

	private SimpleSearchParams simpleSearchParams;

	private List<ComplexSearchParamsSegment> complexSearchSegments;
	private String tripClass = SearchParams.TRIP_CLASS_ECONOMY;

	private Passengers passengers;
	private final Context context;

	public SearchFormData(Context context) {

		this.context = context;

		SharedPreferences prefs = Utils.getPreferences(context);

		loadSimpleSearchParams(prefs);
		loadComplexSearchParams(prefs);

		tripClass = prefs.getString(SearchParams.SEARCH_PARAM_TRIP_CLASS, SearchParams.TRIP_CLASS_ECONOMY);

		passengers = new Passengers();

		passengers.setAdults(prefs.getInt(SearchParams.SEARCH_PARAM_ADULTS, 1));
		passengers.setChildren(prefs.getInt(SearchParams.SEARCH_PARAM_CHILDREN, 0));
		passengers.setInfants(prefs.getInt(SearchParams.SEARCH_PARAM_INFANTS, 0));

		checkAndFixDates();
	}

	public SimpleSearchParams getSimpleSearchParams() {
		return simpleSearchParams;
	}

	public List<ComplexSearchParamsSegment> getComplexSearchSegments() {
		return complexSearchSegments;
	}

	private void checkAndFixDates() {
		checkAndFixSimpleSearchDates();
		checkAndFixComplexSearchDates();
	}

	private void checkAndFixSimpleSearchDates() {

		if (simpleSearchParams.getDepartDateString() != null) {

			Date dateWithFirstTimezone = DateUtils.getCurrentDateInGMTMinus11Timezone();
			dateWithFirstTimezone = DateUtils.getCurrentDayMidnight(dateWithFirstTimezone);

			if (simpleSearchParams.getDepartDate().before(dateWithFirstTimezone)) {
				simpleSearchParams.setDepartDate(getTodayDate());
				simpleSearchParams.setReturnDate((String) null);
			}
		} else {
			simpleSearchParams.setDepartDate(getTomorrowDate());
		}
	}

	public void checkAndFixComplexSearchDates() {

		Date minimumSegmentDate = DateUtils.getCurrentDateInGMTMinus11Timezone();
		minimumSegmentDate = DateUtils.getCurrentDayMidnight(minimumSegmentDate);

		for (int i = 0; i < complexSearchSegments.size(); i++) {
			if (complexSearchSegments.get(i).getStringDate() != null) {

				Date currentSegmentDate = getDate(complexSearchSegments.get(i).getStringDate());

				if (currentSegmentDate.before(minimumSegmentDate)) {
					complexSearchSegments.get(i).setDate(DateUtils.convertToString(minimumSegmentDate));
				} else {
					minimumSegmentDate = currentSegmentDate;
				}
			} else {
				if (i == 0) {
					complexSearchSegments.get(i).setDate(getTomorrowDate());
				}
			}
		}

	}

	private void loadComplexSearchParams(SharedPreferences prefs) {
		complexSearchSegments = new ArrayList<>();

		int segmentsSize = prefs.getInt(COMPLEX_SEARCH_SEGMENTS_COUNT, 2);
		complexSearchSegments.addAll(loadSegments(prefs, segmentsSize));
	}

	private void loadSimpleSearchParams(SharedPreferences prefs) {
		simpleSearchParams = new SimpleSearchParams();

		String defaultCity = "{\"airport_name\":\"Chisinau International Airport\",\"city_name\":\"Chisinau\",\"coordinates\":[\"46.935444\",\"28.934967\"],\"country_name\":\"Moldova\",\"iata\":\"KIV\",\"index_strings\":[\"Sân bay quốc tế Chişinău\",\"キシナウ\",\"基希訥烏\",\"Chişinău\",\"Moldova\",\"มอลโดวา\",\"สนามบินคีชีเนา\",\"Republik Moldau\",\"كيشيناو\",\"Chisinau\",\"摩尔多瓦\",\"Kiszyniów\",\"몰도바\",\"키시나우\",\"مطار كيشيناو الدولي\",\"基希讷乌\",\"Chisinau International\",\"摩爾多瓦\",\"Mołdawia\",\"키시너우 국제공항\",\"Chișinău\",\"基希訥烏國際機場\",\"Moldavia\",\"Chisinau International Airport\",\"Moldavie\",\"مولدافيا\",\"基辛那乌机场\",\"キシナウ国際空港\",\"คีชีเนา\",\"Moldávia\",\"Кишинёв\",\"モルドバ\",\"Kisinau\",\"Kişinev\",\"Bandar Udara Internasional Kishinev\",\"Молдавия\",\"基辛那乌\"],\"name\":\"Chisinau, Moldova\"}";

		simpleSearchParams.setOrigin(PlaceData.create(prefs.getString(SEARCH_PARAM_ORIGIN_NAME, defaultCity)));
		simpleSearchParams.setDestination(PlaceData.create(prefs.getString(SEARCH_PARAM_DESTINATION_NAME, null)));

		simpleSearchParams.setDepartDate(prefs.getString(SEARCH_PARAM_DEPART_DATE, null));
		simpleSearchParams.setReturnDate(prefs.getString(SEARCH_PARAM_RETURN_DATE, null));

		simpleSearchParams.setReturnEnabled(prefs.getBoolean(EXTRA_RETURN_ENABLED, false));

	}

	private List<ComplexSearchParamsSegment> loadSegments(SharedPreferences prefs, int segmentsSize) {
		List<ComplexSearchParamsSegment> segments = new ArrayList<>(segmentsSize);
		for (int i = 0; i < segmentsSize; i++) {
			String indexString = Integer.toString(i);
			segments.add(i, new ComplexSearchParamsSegment(
					PlaceData.create(prefs.getString(SearchParams.SEARCH_PARAM_ORIGIN_IATA.replace(SearchParams.SEGMENT_NUMBER,
							indexString), null)),
					PlaceData.create(prefs.getString(SearchParams.SEARCH_PARAM_DESTINATION_IATA.replace(SearchParams.SEGMENT_NUMBER,
							indexString), null)),
					prefs.getString(SearchParams.SEARCH_PARAM_DATE.replace(SearchParams.SEGMENT_NUMBER,
							indexString), null)
			));
		}
		return segments;
	}

	private String getTomorrowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return formatData(calendar);
	}

	private String getTodayDate() {
		Calendar calendar = Calendar.getInstance();
		return formatData(calendar);
	}

	public Passengers getPassengers() {
		return passengers;
	}


	private Date getDate(String date) {
		if (date == null) return null;
		return CoreDateUtils.parseDateString(date, Defined.SEARCH_SERVER_DATE_FORMAT);
	}

	private String formatData(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat(Defined.SEARCH_SERVER_DATE_FORMAT);
		return sdf.format(calendar.getTime());
	}

	public String getTripClass() {
		return tripClass;
	}

	public String getTripClassName() {
		switch (tripClass) {
			case SearchParams.TRIP_CLASS_ECONOMY:
				return Utils.capitalizeFirstLetter(context.getString(R.string.trip_class_economy));
			case SearchParams.TRIP_CLASS_BUSINESS:
				return Utils.capitalizeFirstLetter(context.getString(R.string.trip_class_business));
		}
		return null;
	}

	public SearchParams createSearchParams(boolean isComplexSearch) {
		SearchParams params = new SearchParams();


		params.setPassengers(passengers);
		params.setTripClass(tripClass);
		params.setContext(context.getApplicationContext());

		if (isComplexSearch) {
			params = createComplexSearchParams(params);
		} else {
			params = createSimpleSearchParams(params);
		}

		return params;
	}

	private SearchParams createComplexSearchParams(SearchParams params) {

		for (ComplexSearchParamsSegment paramsSegment : complexSearchSegments) {
			params.addSegment(paramsSegment.toSearchSegment());
		}

		return params;
	}

	private SearchParams createSimpleSearchParams(SearchParams params) {

		params.setSegments(simpleSearchParams.createSegments());
		return params;
	}

	public void saveState() {

		SharedPreferences.Editor editor = Utils.getPreferences(context).edit();
		putSimpleSearchParams(editor);
		putComplexSearchParams(editor);

		editor.putString(SearchParams.SEARCH_PARAM_TRIP_CLASS, tripClass)
				.putInt(SearchParams.SEARCH_PARAM_ADULTS, passengers.getAdults())
				.putInt(SearchParams.SEARCH_PARAM_CHILDREN, passengers.getChildren())
				.putInt(SearchParams.SEARCH_PARAM_INFANTS, passengers.getInfants())
				.putBoolean(EXTRA_RETURN_ENABLED, simpleSearchParams.isReturnEnabled())
				.apply();
	}

	private void putComplexSearchParams(SharedPreferences.Editor editor) {
		int i = 0;
		for (ComplexSearchParamsSegment complexSegment : complexSearchSegments) {
			if (complexSegment.getOrigin() != null) {
				editor.putString(SearchParams.SEARCH_PARAM_ORIGIN_IATA.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)), complexSegment.getOrigin().serialize());
			} else {
				editor.remove(SearchParams.SEARCH_PARAM_ORIGIN_IATA.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)));
			}
			if (complexSegment.getDestination() != null) {
				editor.putString(SearchParams.SEARCH_PARAM_DESTINATION_IATA.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)), complexSegment.getDestination().serialize());
			} else {
				editor.remove(SearchParams.SEARCH_PARAM_DESTINATION_IATA.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)));
			}
			if (complexSegment.getStringDate() != null) {
				editor.putString(SearchParams.SEARCH_PARAM_DATE.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)), complexSegment.getStringDate());
			} else {
				editor.remove(SearchParams.SEARCH_PARAM_DATE.replace(SearchParams.SEGMENT_NUMBER,
						Integer.toString(i)));
			}
			i++;
		}
		editor.putInt(COMPLEX_SEARCH_SEGMENTS_COUNT, i);
	}

	private void putSimpleSearchParams(SharedPreferences.Editor editor) {
		String originSerialized = null;
		String destinationSerialized = null;

		if (simpleSearchParams.getOrigin() != null) {
			originSerialized = simpleSearchParams.getOrigin().serialize();
		}

		if (simpleSearchParams.getDestination() != null) {
			destinationSerialized = simpleSearchParams.getDestination().serialize();
		}
		editor.putString(SEARCH_PARAM_ORIGIN_NAME, originSerialized)
				.putString(SEARCH_PARAM_DESTINATION_NAME, destinationSerialized)
				.putString(SEARCH_PARAM_DEPART_DATE, simpleSearchParams.getDepartDateString())
				.putString(SEARCH_PARAM_RETURN_DATE, simpleSearchParams.getReturnDateString());

	}


	private boolean isSomeComplexSearchDatePassed() {
		for (ComplexSearchParamsSegment segment : complexSearchSegments) {
			if (DateUtils.isDateBeforeDateShiftLine(segment.getDate())) {
				return true;
			}
		}
		return false;
	}


	public void setTripClass(String tripClass) {
		this.tripClass = tripClass;
	}

	public void setPassengers(Passengers passengers) {
		this.passengers = passengers;
	}

	public boolean areDestinationsEqual(boolean isComplexSearchSelected) {

		if (isComplexSearchSelected) {
			for (ComplexSearchParamsSegment segment : complexSearchSegments) {
				if (segment.getOrigin().getIata().equals(segment.getDestination().getIata()) ||
						segment.getOrigin().getCityName().equals(segment.getDestination().getCityName()))
					return true;
			}
			return false;
		} else {
			return simpleSearchParams.areDestinationsEqual();
		}
	}

	public boolean areDestinationsSet(boolean isComplexSearch) {
		if (isComplexSearch) {
			for (ComplexSearchParamsSegment segment : complexSearchSegments) {
				if (segment.getOrigin() == null || segment.getDestination() == null) return true;
			}
			return false;
		} else {
			return simpleSearchParams.areDestinationsSet();
		}
	}

	public boolean isDepartureDateNotSet(boolean isComplexSearchSelected) {
		if (isComplexSearchSelected) {
			return areComplexParamsSomeDateNotSet();
		} else {
			return simpleSearchParams.getDepartDateString() == null;
		}

	}

	private boolean areComplexParamsSomeDateNotSet() {
		for (ComplexSearchParamsSegment segment : complexSearchSegments) {
			if (segment.getStringDate() == null) {
				return true;
			}
		}
		return false;
	}

	public boolean isDepartDatePassed(boolean isComplexSearchSelected) {
		if (isComplexSearchSelected) {
			return isSomeComplexSearchDatePassed();
		} else {
			return DateUtils.isDateBeforeDateShiftLine(simpleSearchParams.getDepartDate());
		}
	}

	public boolean isSimpleParamsNoReturnDateSet() {
		return simpleSearchParams.getReturnDateString() == null;
	}

	public boolean isSimpleSearchReturnDatePassed() {
		return DateUtils.isDateBeforeDateShiftLine(simpleSearchParams.getReturnDateString());
	}

	public boolean isSimpleSearchReturnEarlierThanDeparture() {
		return DateUtils.isFirstDateBeforeSecondDateWithDayAccuracy(getDate(simpleSearchParams.getReturnDateString()),
				simpleSearchParams.getDepartDate());
	}

	public boolean isSimpleSearchDatedMoreThanYearAhead() {
		return DateUtils.isDateMoreThanOneYearAfterToday(simpleSearchParams.getDepartDate()) ||
				DateUtils.isDateMoreThanOneYearAfterToday(simpleSearchParams.getReturnDate());
	}

}
