package me.jiaojian.ibook.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.jiaojian.ibook.R;
import me.jiaojian.ibook.ui.fragment.setting.AboutPhonePreference;
import me.jiaojian.ibook.ui.fragment.setting.DatetimePreference;
import me.jiaojian.ibook.ui.fragment.setting.DeveloperPreference;
import me.jiaojian.ibook.ui.fragment.setting.MainPreference;


public class SettingsActivity extends AppCompatActivity implements PreferenceFragment.OnPreferenceStartFragmentCallback {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setActionBar();

    addFragment(new MainPreference());

    //addPreferencesFromResource(R.xml.preferences);
  }

  private void addFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, fragment);
    fragmentTransaction.commit();
  }

  private void pushFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.addToBackStack(fragment.getClass().getName());
    fragmentTransaction.replace(R.id.fragment_container, fragment);
    fragmentTransaction.commit();
  }

  private void setActionBar() {
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      if(getFragmentManager().getBackStackEntryCount() > 0) {
        getFragmentManager().popBackStack();
        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
    String fragment = pref.getFragment();
    if(fragment.equals(AboutPhonePreference.class.getName())) {
      pushFragment(new AboutPhonePreference());
    }
    if(fragment.equals(DatetimePreference.class.getName())) {
      pushFragment(new DatetimePreference());
    }
    if(fragment.equals(DeveloperPreference.class.getName())) {
      pushFragment(new DeveloperPreference());
    }
    return false;
  }
}
