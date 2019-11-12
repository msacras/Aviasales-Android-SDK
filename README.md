## DEPRICATED. Use https://github.com/travelpayouts/travel-app-android

Aviasales/JetRadar Android SDK 2.0
=================

Aviasales/JetRadar Android SDK is a framework integrating flight search engine into your app. When your customer books a flight, we pay you a [commission fee](https://www.travelpayouts.com). Framework is based on leading flight search engines [Aviasales](http://www.aviasales.ru) and [JetRadar](http://www.jetradar.com).

SDK supports all Android devices with Android API 16 and higher.

The framework consists of:
* search API library for search server interaction;
* user interface [template project](https://github.com/KosyanMedia/Aviasales-Android-SDK/wiki/Template-project-screens);
* [demo application](https://github.com/KosyanMedia/Aviasales-Android-SDK/tree/master/simple_demo) based on template project.

![][1]

Learn more and complete integration with [Aviasales Android SDK Documentation](https://github.com/KosyanMedia/Aviasales-Android-SDK/wiki/Aviasales-Android-SDK-API-documentation).
<br>To get your API key, track statistics and payments please sign up to [Travelpayouts Travel Affiliate Network](https://www.travelpayouts.com/?utm_source=github&utm_medium=android_sdk).
<br>Learn more about earnings in [Travelpayouts FAQ](https://support.travelpayouts.com/hc/en-us/articles/203955613-Commission-and-payments).
<br>Video [instruction] (https://www.youtube.com/watch?v=dQw4w9WgXcQ) 

More languages: [RUS] [Документация Aviasaels Android SDK](https://github.com/KosyanMedia/Aviasales-Android-SDK/wiki/О-SDK).

## What's new in 2.1.17
- Added default privacy policy (you should change it to your own). Set it through `PrivacyPolicyUrl.setUrl("MY_PRIVACY_POLICY_URL");`

## Privacy Policy

Privacy Policy is **REQUIRED** for your app. If it is missing, your app may be removed from the Google Play at anytime.  
We've included a default Privacy Policy in the template app, but you should create YOUR OWN.  
(you can use online services like [privacypolicytemplate.net](https://privacypolicytemplate.net) or [App Privacy Policy Generator](https://app-privacy-policy-generator.firebaseapp.com)).  
NEXT STEPS:
1) Upload privacy policy to your website and copy a link to it.
2) Use this link for a Privacy Policy field in Google Play Console.
3) Inside your app call PrivacyPolicyUrl.setUrl("MY_PRIVACY_POLICY_URL") with a link to your own Privacy Policy.

## Installation

### Add gradle dependencies 

To add Aviasales SDK Library to your project use gradle:

```gradle
repositories {
    maven { url 'http://android.aviasales.ru/repositories/' }
}

dependencies {
    compile 'ru.aviasales:aviasalesSdk:2.1.16-sdk'
}
```

If you want to use complete Aviasales SDK Template, you can add it like this  :

```gradle
repositories {
    maven { url 'http://android.aviasales.ru/repositories/' }
}

dependencies {
    compile 'ru.aviasales.template:aviasalesSdkTemplate:2.1.17'
}
```

### Initialization of Aviasales SDK

Before any interaction with Aviasales SDK or Aviasales Template you should initialize it 
```java
  AviasalesSDK.getInstance().init(this, new SdkConfig(TRAVEL_PAYOUTS_MARKER, TRAVEL_PAYOUTS_TOKEN, SDK_HOST)); 
```

Change `TRAVEL_PAYOUTS_MARKER` and `TRAVEL_PAYOUTS_TOKEN` to your marker and token params. You can find them at [Travelpayouts.com](https://www.travelpayouts.com/developers/api).

`SDK_HOST` is a main endpoint of Aviasales SDK. You can set `www.travel-api.pw`as your default endpoint, but we strongly recommend to change it to your [WhiteLabel host](https://support.travelpayouts.com/hc/en-us/categories/115000474487). 

## Example of adding Aviasales Template to your project 

### Add `AviasalesFragment` to your activity 

Add to main activity layout `activity_main.xml` the `FrameLayout` for fragments 

```xml
 	<FrameLayout
		android:id="@+id/fragment_place"
		android:layout_width="match_parent"
		android:layout_height="match_parent"/>
```

Add fragment to `MainActivity`

```java	
  public class MainActivity extends AppCompatActivity {
  	//Replace these variables on your TravelPayouts marker and token
  	private final static String TRAVEL_PAYOUTS_MARKER = "your_travel_payouts_marker";
	private final static String TRAVEL_PAYOUTS_TOKEN = "your_travel_payouts_token";
	private final static String SDK_HOST = "www.travel-api.pw";
  	private AviasalesFragment aviasalesFragment;
    ...
  
  	@Override
  	protected void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);
  
   		// Initialization of AviasalesSDK. 
		AviasalesSDK.getInstance().init(this, new SdkParams(TRAVEL_PAYOUTS_MARKER, TRAVEL_PAYOUTS_TOKEN,SDK_HOST));
  		setContentView(R.layout.activity_main);
     
  		initFragment();
 	}
      ...
  
  	private void initFragment() {
  		FragmentManager fm = getSupportFragmentManager();
  
  		aviasalesFragment = (AviasalesFragment) fm.findFragmentByTag(AviasalesFragment.TAG); // finding fragment by tag
  
  
  		if (aviasalesFragment == null) { 
  			aviasalesFragment = (AviasalesFragment) AviasalesFragment.newInstance();
  		}
  
  		FragmentTransaction fragmentTransaction = fm.beginTransaction(); // adding fragment to fragment manager
  		fragmentTransaction.replace(R.id.fragment_place, aviasalesFragment, AviasalesFragment.TAG);
  		fragmentTransaction.commit();
  	}
}
```

### Specify permissions

Don't forget to specify permissions `INTERNET` and `ACCESS_NETWORK_STATE` by adding `<uses-permission>` elements as children of the `<manifest>` element. 

```xml
	<uses-permission android:name="android.permission.INTERNET"/>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```


### Adding onBackPressed 

For proper back navigation between aviasales child fragments add fragment `onBackPressed` inside of activity `onBackPressed` 

```java
	@Override
	public void onBackPressed() {

    ...
		if (!aviasalesFragment.onBackPressed()) {
			super.onBackPressed();
		}
		...
		
	}
```

### Customization

For proper customization of Aviasales Template use `AviasalesTemplateTheme` or extend your theme from it. For example, in `AndroidManifest.xml` specify your theme

```xml    
    <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            ...
```

and in `styles.xml` extend your theme from `AviasalesTemplateTheme`

```xml
	<style name="AppTheme" parent="AviasalesTemplateTheme">
            ...
	</style>
```

To change colors of your app override `colorAsPrimary`, `colorAsPrimaryDark` and `colorAviasalesMain`  in `colors.xml`. This is main Aviasales Template colors

```xml
    <color name="colorAsPrimary">#3F51B5</color>
    <color name="colorAsPrimaryDark">#3F51B5</color>
    <color name="colorAviasalesMain">#3F51B5</color>
```

Also you can change background and price tag colors
```xml
	<color name="aviasales_results_background">@color/grey_background</color>
	<color name="aviasales_search_form_background">@color/white</color>
	<color name="aviasales_filters_background">@color/white</color>

	<color name="aviasales_select_airport_background">@color/white</color>
	<color name="aviasales_ticket_background">@color/grey_background</color>

	<color name="aviasales_results_card_color">@color/white</color>
	<color name="aviasales_price_color">@color/yellow_FDCC50</color>
```

For more information see the [demo project](https://github.com/KosyanMedia/Aviasales-Android-SDK/tree/master/simple_demo)

### Appodeal ads 
You can implement [Appodeal ads](http://www.appodeal.ru/) in your app and and get revenue from them (available from Aviasales SDK v.2.1.3) 

![][2]

To add Appodeal Ads to your project just add additional maven dependency:

```gradle
dependencies {
    compile 'ru.aviasales.template:appodeallib:2.1.17'
}
```

And then initialize it 

```java
		AppodealAds ads = new AppodealAds(); 
		ads.setStartAdsEnabled(SHOW_ADS_ON_START); // ads on start (true/false)
		ads.setWaitingScreenAdsEnabled(SHOW_ADS_ON_WAITING_SCREEN); // ads on waiting screen (true/false)
		ads.setResultsAdsEnabled(SHOW_ADS_ON_SEARCH_RESULTS); // ads on results (true/false)
		ads.init(this, APPODEAL_APP_KEY);  // your appodeal key
		AdsImplKeeper.getInstance().setCustomAdsInterfaceImpl(ads); // assign your appodeal
```
### Admob support
If you use play services version 17 and upper you must add <meta-data> tag to Android manifest file. 
```xml
<manifest>
    <application>
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="[ADMOB_APP_ID]"/>
    </application>
</manifest>
```
You can get ADMOB_APP_ID here:
![][3]	


For more information about appodal ads see the [ads demo project](https://github.com/KosyanMedia/Aviasales-Android-SDK/tree/master/ads_simple_demo)


### [Aviasales Android API](https://github.com/KosyanMedia/Aviasales-Android-SDK/wiki/Aviasales-Android-SDK-API-documentation)
### [Template project screens](https://github.com/KosyanMedia/Aviasales-Android-SDK/wiki/Template-project-screens)


[1]: /screenshots/screen.gif "Screenshot1"
[2]: /screenshots/Screenshot_ads1.png "Screenshot2"
[3]: https://appodeal-uploads.s3.amazonaws.com/server/production/docs_editor/0e60aa4a-f664-4fe8-8db0-54e37debd08e_de_AdMob_2018-10-11_11-16-33.png
