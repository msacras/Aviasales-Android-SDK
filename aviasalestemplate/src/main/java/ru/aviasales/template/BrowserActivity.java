package ru.aviasales.template;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import ru.aviasales.template.ui.fragment.BrowserFragment;

public class BrowserActivity extends AppCompatActivity {
	public static final String SHOW_LOADING_DIALOG = "show_loading_dialog";
	public static final String HOST = "HOST";
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aviasales_fragment_layout);
		initFragment();
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_child_place, BrowserFragment.newInstance(needToShowLoadingDialog(), getHost()), null);
		fragmentTransaction.commit();
	}

	private boolean needToShowLoadingDialog() {
		Intent intent = getIntent();
		return intent != null && intent.getExtras() != null && intent.getExtras().getBoolean(SHOW_LOADING_DIALOG, false);
	}

	private String getHost() {
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			return intent.getExtras().getString(HOST);
		}
		return null;
	}
}
