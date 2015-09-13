package com.mynetgear.dord.platypus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.preference.DialogPreference;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


/**
 * Created by Alexandre Poon on 15.08.12.
 */
public class LocalePrefs extends DialogPreference {


    private SharedPreferences sharedPreferences;
    private static final String SHAREDPREFS_NAME = "settings";
    private static final String SHAREDPREFS_LOCALE = "LOCALE";

    public LocalePrefs(Context context) {
        super(context);
        setTitle(context.getString(R.string.locale)); //Title of the PREFERENCE, NOT dialog!
    }


    private PreferenceScreen gotPreferenceScreen;

    public void setGotPreferenceScreen(PreferenceScreen theScreen) {
        gotPreferenceScreen = theScreen;
    }

    public class LocAdapter extends ArrayAdapter<String> {

        public LocAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            String[] supTxt = getContext().getResources().getStringArray(R.array.locale_native);
            String[] subTxt = getContext().getResources().getStringArray(R.array.locale);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.old_layout_prefs_lang, parent, false);
            ImageView icon = (ImageView) row.findViewById(R.id.flagHolder);
            final Locale selectedLocale;
            Typeface vt = Typeface.createFromAsset(getContext().getAssets(), "fonts/VT323.ttf");
            TextView label = (TextView) row.findViewById(R.id.langTxt0);
            switch (position) {
                case 0:
                    icon.setImageResource(R.drawable.uk_flag);
                    break;
                case 1:
                    icon.setImageResource(R.drawable.us_flag);
                    break;
                case 2:
                    icon.setImageResource(R.drawable.ca_flag);
                    break;
                case 3:
                    icon.setImageResource(R.drawable.de_flag);
                    break;
                case 4:
                    icon.setImageResource(R.drawable.gr_flag);
                    break;
                case 5:
                    icon.setImageResource(R.drawable.es_flag);
                    break;
                case 6:
                    icon.setImageResource(R.drawable.mx_flag);
                    break;
                case 7:
                    icon.setImageResource(R.drawable.eo_flag);
                    break;
                case 8:
                    icon.setImageResource(R.drawable.fr_flag);
                    break;
                case 9:
                    icon.setImageResource(R.drawable.haxor_flag);
                    label.setTypeface(vt); //h4x0r
                    label.setTextSize(25); //h4x0r
                    break;
                case 10:
                    icon.setImageResource(R.drawable.ja_flag);
                    break;
                case 11:
                    icon.setImageResource(R.drawable.ua_flag);
                    break;
                case 12:
                    icon.setImageResource(R.drawable.hk_flag);
                    break;
                case 13:
                    icon.setImageResource(R.drawable.tw_flag);
                    break;
                default:
                    icon.setImageResource(R.drawable.thl_flag);
                    break;
            }
            label.setText(supTxt[position]);
            TextView subLabel = (TextView) row.findViewById(R.id.langTxt1);
            subLabel.setText(subTxt[position]);
//            int Auber = getContext().getResources().getColor(R.color.Aubergine);
//            int Ubu = getContext().getResources().getColor(R.color.UbuntuOrange);
            if (position % 2 == 0) {
                row.setBackgroundColor(getContext().getResources().getColor(R.color.Aubergine));
            } else {
                row.setBackgroundColor(getContext().getResources().getColor(R.color.UbuntuOrange));
            }
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = getContext().getSharedPreferences(SHAREDPREFS_NAME, getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (position) {
                        case 0:
                            editor.putString(SHAREDPREFS_LOCALE, "en-GB");
                            break;
                        case 1:
                            editor.putString(SHAREDPREFS_LOCALE, "en-US");
                            break;
                        case 2:
                            editor.putString(SHAREDPREFS_LOCALE, "ca");
                            break;
                        case 3:
                            editor.putString(SHAREDPREFS_LOCALE, "de-DE");
                            break;
                        case 4:
                            editor.putString(SHAREDPREFS_LOCALE, "el");
                            break;
                        case 5:
                            editor.putString(SHAREDPREFS_LOCALE, "es-ES");
                            break;
                        case 6:
                            editor.putString(SHAREDPREFS_LOCALE, "es-MX");
                            break;
                        case 7:
                            editor.putString(SHAREDPREFS_LOCALE, "eo");
                            break;
                        case 8:
                            editor.putString(SHAREDPREFS_LOCALE, "fr-FR");
                            break;
                        case 9:
                            editor.putString(SHAREDPREFS_LOCALE, "xx-hacker");
                            break;
                        case 10:
                            editor.putString(SHAREDPREFS_LOCALE, "ja");
                            break;
                        case 11:
                            editor.putString(SHAREDPREFS_LOCALE, "uk");
                            break;
                        case 12:
                            editor.putString(SHAREDPREFS_LOCALE, "zh-HK");
                            break;
                        case 13:
                            editor.putString(SHAREDPREFS_LOCALE, "zh-TW");
                            break;
                        default:
                            editor.putString(SHAREDPREFS_LOCALE, "en-GB"); //ROT-13?
                            break;
                    }
                    editor.commit();
//                    Log.i("Test", LocalePrefs.super.toString());
                    Log.i(Integer.toString(position), "<-- Position");
                    Log.i("From LocalePrefs", sharedPreferences.getString(SHAREDPREFS_LOCALE, "mk"));
                    if (SHAREDPREFS_LOCALE != null) {
                        String localCode = sharedPreferences.getString(SHAREDPREFS_LOCALE, "mk");
                        Locale myLocale;
                        if (localCode.length() > 2) {
                            myLocale = new Locale(localCode.substring(0, 2), localCode.substring(3));
                            Log.e(localCode.substring(0, 2), localCode.substring(3));
                        } else {
                            myLocale = new Locale(localCode);
                        }
                        Locale.setDefault(myLocale);
                        Configuration config = new Configuration();
                        config.setLocale(myLocale);
                        config.locale = myLocale;
                        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
                    }
                    gotPreferenceScreen.getPreference(0).setTitle(getContext().getString(R.string.locale));
                    gotPreferenceScreen.getPreference(1).setTitle(getContext().getString(R.string.fontTestPangram));
                    gotPreferenceScreen.getPreference(2).setTitle(getContext().getString(R.string.versionTitle));
                    gotPreferenceScreen.getPreference(3).setTitle(getContext().getString(R.string.changelogTitle));
                    ((Activity) getContext()).setTitle(R.string.action_settings);
//                    gotPreferenceScreen.getPreference(0).setTitle(getContext().getString(R.string.fontTestPangram));
//                    gotPreferenceScreen.getPreference(1).setTitle(getContext().getString(R.string.fontTestPangram));
//                    gotPreferenceScreen.getPreference(2).setTitle(getContext().getString(R.string.fontTestPangram));
//                    gotPreferenceScreen.getPreference(3).setTitle(getContext().getString(R.string.fontTestPangram));
                    ((DialogPreference) gotPreferenceScreen.getPreference(0)).setDialogTitle(getContext().getString(R.string.locale));
                    ((DialogPreference) gotPreferenceScreen.getPreference(0)).setNegativeButtonText(getContext().getString(android.R.string.cancel));
                    getDialog().dismiss();
//                    Locale.setDefault(selectedLocale);
//                    Configuration config = new Configuration();
//                    config.locale = selectedLoccale;
//                    getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
                }

            });
            return row;
        }


//    @Override
//    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
//        super.onPrepareDialogBuilder(builder);
//        builder.setTitle(getContext().getString(R.string.locale));
//        builder.setNegativeButton(getContext().getString(R.string.cancel), null);
//        builder.setCancelable(false);
//    }
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        //super.onPrepareDialogBuilder(builder);
        String[] supTxt = getContext().getResources().getStringArray(R.array.locale_native);
        LocAdapter locAdapter = new LocAdapter(getContext(), android.R.layout.simple_spinner_item, supTxt);
        builder.setAdapter(locAdapter, null);
        builder.setIcon(getContext().getDrawable(R.mipmap.ic_translate_white_24dp));
        builder.setTitle(getContext().getString(R.string.locale)); //Title of the DIALOG, NOT Preference!
        builder.setPositiveButton(null, null);
        //builder.setNegativeButton(getContext().getString(R.string.cancel), null);
        builder.setCancelable(false);
    }

}
