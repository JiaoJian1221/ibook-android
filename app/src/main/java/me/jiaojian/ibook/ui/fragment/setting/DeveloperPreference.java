package me.jiaojian.ibook.ui.fragment.setting;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import me.jiaojian.ibook.R;

/**
 * Created by jiaojian on 2018/1/24.
 */

public class DeveloperPreference extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preferences_developer_options);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if ("setting_wifi".equals(key) || "setting_bluetouh".equals(key) || "charge_lock_screen".equals(key) || "never_sleep".equals(key)) {
      Toast.makeText(getActivity(), key + " : change to " + sharedPreferences.getBoolean(key, true), Toast.LENGTH_SHORT).show();
    }
    else if("setting_timezone".equals(key)) {
      findPreference("setting_timezone").setSummary(sharedPreferences.getString(key,"GMY - 02:00"));
    }
  }

  private void setTitle() {
    ActionBar actionBar = getActivity().getActionBar();
    if(actionBar != null) {
      actionBar.setTitle(getPreferenceScreen().getTitle());
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    setTitle();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }


  @Override
  public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
}