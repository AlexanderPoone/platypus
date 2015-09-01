package com.mynetgear.dord.platypus;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Alexandre Poon on 15.08.11.
 */
public class PrefsClass extends PreferenceActivity {

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
        context = this;
        setTitle(R.string.action_settings); //Switch to changed locale for 2nd+ launch
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setPreferenceScreen(createScreen());
//            addPreferencesFromResource(R.xml.prefs);
        }

        private PreferenceScreen createScreen() {
            PreferenceScreen mainScreen = getPreferenceManager().createPreferenceScreen(getActivity());
            LocalePrefs localePrefs = new LocalePrefs(context);
//            Log.wtf("CONTEXT",context.toString());
            localePrefs.setKey("LOCALE");
            localePrefs.setGotPreferenceScreen(mainScreen);
//            Log.wtf("KEY", localePrefs.getKey());
            mainScreen.addPreference(localePrefs);
            RingtonePreference ringtonePreference = new RingtonePreference(context);
            ringtonePreference.setTitle(context.getString(R.string.ringtone_test));
            ringtonePreference.setSummary("Ringtones by Google, not by CyanogenMod!");
            ringtonePreference.setIcon(android.R.drawable.ic_search_category_default);
            mainScreen.addPreference(ringtonePreference);
            Preference appVersion = new Preference(context);
            appVersion.setTitle(context.getString(R.string.versionTitle));
            appVersion.setSummary("α 0.314 (Schwarzwälder Kirschtorte)");
            appVersion.setSelectable(false);
            mainScreen.addPreference(appVersion);
            Preference showChangelog = new Preference(context);
            showChangelog.setTitle(context.getString(R.string.changelogTitle));
            showChangelog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().finish();
                    return false;
                }
            });
//            showChangelog.setEnabled(false);
            mainScreen.addPreference(showChangelog);
            return mainScreen;
        }

//        public void changeStrings (PreferenceScreen s) {
//            getPreferenceScreen().getPreference(2).setTitle("Hey!");
//            s.findPreference("LOCALE");
//        }
    }

}
