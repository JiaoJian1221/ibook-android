package me.jiaojian.ibook.ui.fragment.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.text.TextUtils;

import me.jiaojian.ibook.R;

/**
 * Created by jiaojian on 2018/1/24.
 */

public class MainPreference extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
    getPreferenceManager().setSharedPreferencesName(getActivity().getPackageName());

    addPreferencesFromResource(R.xml.preferences_main);

  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    bindPreference(findPreference(key));
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    bindPreference(getPreferenceScreen());
  }

  private String getPreferenceValue(Preference preference) {
    return preference.getSharedPreferences().getString(preference.getKey(), "");
  }

  private void bindPreference(Preference preference) {
    if(preference instanceof PreferenceGroup) {
      for(int i=0; i<((PreferenceGroup)preference).getPreferenceCount(); i++) {
        bindPreference(((PreferenceGroup)preference).getPreference(i));
      }
    }
    else if(preference instanceof EditTextPreference) {
      String value = getPreferenceValue(preference);
      if(!TextUtils.isEmpty(value)) {
        preference.setSummary(value);
      }
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
}