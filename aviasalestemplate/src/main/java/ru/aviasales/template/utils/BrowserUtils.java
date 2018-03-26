package ru.aviasales.template.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;

import ru.aviasales.template.ui.fragment.BrowserFragment;

public class BrowserUtils {

	public static void openExternalBrowser(@Nullable Activity activity, @Nullable String url, @Nullable String host) {
		if (activity == null || url == null) return;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		if (host != null) {
			Bundle bundle = new Bundle();
			bundle.putString(BrowserFragment.REFERER_HEADER, BrowserFragment.HTTP + host);
			intent.putExtra(Browser.EXTRA_HEADERS, bundle);
		}
		activity.startActivity(intent);
	}
}
