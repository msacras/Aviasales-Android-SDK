package ru.aviasales.template.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;

public class BrowserUtils {
	public static final String REFERER_HEADER = "Referer";
	public static final String HTTP = "http://";

	public static void openExternalBrowser(@Nullable Activity activity, @Nullable String url, @Nullable String host) {
		if (activity == null || url == null) return;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		if (host != null) {
			Bundle bundle = new Bundle();
			bundle.putString(REFERER_HEADER, HTTP + host);
			intent.putExtra(Browser.EXTRA_HEADERS, bundle);
		}
		activity.startActivity(intent);
	}
}
