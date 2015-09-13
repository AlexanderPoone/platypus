package com.mynetgear.dord.platypus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.util.Precision;

import java.util.Locale;
import java.util.Random;

//import android.widget.Spinner;
//import android.app.Dialog;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.os.Handler;
//import java.util.ArrayList;


public class MainActivity extends Activity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.old_layout_main);
        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
        CreateMpThread createMpThread = new CreateMpThread();
        createMpThread.start();
        GenNotifThread genNotifThread = new GenNotifThread();
        genNotifThread.start();
        final Button button = (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotifJumpThread motifJumpThread = new MotifJumpThread();
                motifJumpThread.run();
                Fraction fraction = new Fraction(113, 355); /* 22 / 7 */
                String piString = Double.toString(Precision.round(fraction.reciprocal().doubleValue(), 4));
                TextView textView = (TextView) findViewById(R.id.ver);
                textView.setText(piString);
                int[] ta = getResources().getIntArray(R.array.simpleRainbow);
                int random = ta[new Random().nextInt(ta.length)];
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.backdrop);
                button.getBackground().setTint(random);
//                String s = Integer.toString(random);
//                button.setText(s);
                Typeface mythosFont = Typeface.createFromAsset(getAssets(), "fonts/MythosStd.otf");
                Typeface engagementFont = Typeface.createFromAsset(getAssets(), "fonts/Engagement.ttf");
                Typeface reliqExtraActiveFont = Typeface.createFromAsset(getAssets(), "fonts/ReliqStd-ExtraActive.otf");
                String loc = Locale.getDefault().getLanguage(); //Get Locale
                Log.i("Bear", loc);
                switch (loc) {
                    case "en":
                        textView.setTypeface(mythosFont);
                        break;
                    case "zh":
                        textView.setTypeface(engagementFont);
                        break;
                    default:
                        textView.setTypeface(reliqExtraActiveFont);
                        break;
                }
                BuildDialogThread buildDialogThread = new BuildDialogThread();
                buildDialogThread.run();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        Log.wtf("Fuck you", "Fuck you again!");
//                    }
//                }, 5000);
            }
        });
//        String[] supTxt = getResources().getStringArray(R.array.locale);
//        Spinner spinner = (Spinner) findViewById(R.id.spinSpinSpin);
//        CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, supTxt);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        /* MySQL */
//        try {
//            String link = "loginpost.php";
//            URL url = new URL(link);
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//            String logDat = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode("alexandre", "UTF-8");
//            logDat += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("p00n60970", "UTF-8");
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write(logDat);
//            wr.flush();
//        } catch (Exception e) {
//            Log.w("Exception: ", e.getMessage());
//        }
    }

    public class GenNotifThread extends Thread {
        @Override
        public void run() {
            Notification notification = new Notification.Builder(MainActivity.this)
                    .setContentTitle("Ma notification")
                    .setContentText("J'espère qu'il réussit.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(false)
                    .build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }
    }

    public class CreateMpThread extends Thread {
        @Override
        public void run() {
            mp = MediaPlayer.create(MainActivity.this, R.raw.english_dance);
            mp.start();
        }
    }

    public class MotifJumpThread extends Thread {
        @Override
        public void run() {
            if (!mp.isPlaying()) {
                mp.start();
            }
            mp.seekTo(82000);
        }
    }

    public class BuildDialogThread extends Thread {
        @Override
        public void run() {
            String[] pHues = getResources().getStringArray(R.array.colours);
            ListAdapter b = new HueAdapter(MainActivity.this, android.R.layout.simple_list_item_1, pHues);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.coloursTitle)
                    .setIcon(R.mipmap.ic_color_lens_white_24dp)
                    .setCancelable(false)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setAdapter(b, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
//                        .setItems(R.array.colours, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
                    });
            AlertDialog realDialog = builder.create();
            realDialog.show();
        }
    }

    public class HueAdapter extends ArrayAdapter<String> {

        public HueAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.old_layout_color_dialog, parent, false);

            String[] hues = getResources().getStringArray(R.array.colours);
            int[] code = getResources().getIntArray(R.array.colorSystem);
            String strColor = String.format("#%06X", 0xFFFFFF & code[position]);
            TextView apex = (TextView) row.findViewById(R.id.colorTxt);
            apex.setText(hues[position]);
            TextView submarine = (TextView) row.findViewById(R.id.codeTxt);
            Typeface beaux = Typeface.createFromAsset(getAssets(), "fonts/courbd.ttf");
            submarine.setTypeface(beaux);
            submarine.setText(strColor);
            TextView exp = (TextView) row.findViewById(R.id.exp);
            exp.setTypeface(beaux);

            ImageView hueField = (ImageView) row.findViewById(R.id.colorField);
            Drawable myDrawable = MainActivity.this.getDrawable(R.drawable.color_rects);
            myDrawable.setColorFilter(code[position], PorterDuff.Mode.MULTIPLY);
//            switch (position) {
//                case 0:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.UbuntuOrange), PorterDuff.Mode.MULTIPLY);
//                    //hueField.setBackgroundColor(getResources().getColor(R.color.UbuntuOrange));
//                    break;
//                case 1:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.Aubergine), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 2:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.AndroidGreen), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 3:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.LazyBearBrown), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 4:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 5:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.Ivory), PorterDuff.Mode.MULTIPLY);
//                    //hueField.setImageResource(R.drawable.de_flag);
//                    break;
//                case 6:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.LightYellow), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 7:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.Yellow), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 8:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.Snow), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 9:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.FloralWhite), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 10:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.LemonChiffon), PorterDuff.Mode.MULTIPLY);
//                    break;
//                case 11:
//                    myDrawable.setColorFilter(getResources().getColor(R.color.Cornsilk), PorterDuff.Mode.MULTIPLY);
//                    break;
//                default:
//                    hueField.setImageResource(R.drawable.thl_flag);
//                    break;
//            }
            hueField.setImageDrawable(myDrawable);
            //Log.i("Position: ", Integer.toString(position));
            return row;
        }
    }

//    public class CustomAdapter extends ArrayAdapter<String> {
//
//        public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
//            super(context, textViewResourceId, objects);
//        }
//
//        @Override
//        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//            return getCustomView(position, convertView, parent);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return getCustomView(position, convertView, parent);
//        }
//
//        public View getCustomView(int position, View convertView, ViewGroup parent) {
//            String[] supTxt = getResources().getStringArray(R.array.locale_native);
//            String[] subTxt = getResources().getStringArray(R.array.locale);
//            LayoutInflater inflater = getLayoutInflater();
//            View row = inflater.inflate(R.layout.old_layout_prefs_lang, parent, false);
//
//            ImageView icon = (ImageView) row.findViewById(R.id.flagHolder);
//            Locale locale;
//            switch (position) {
//                case 0:
//                    icon.setImageResource(R.drawable.uk_flag);
//                    locale = new Locale("en", "GB");
//                    break;
//                case 1:
//                    icon.setImageResource(R.drawable.us_flag);
//                    locale = new Locale("en", "US");
//                    break;
//                case 2:
//                    icon.setImageResource(R.drawable.ca_flag);
//                    locale = new Locale("ca");
//                    break;
//                case 3:
//                    icon.setImageResource(R.drawable.de_flag);
//                    locale = new Locale("de", "DE");
//                    break;
//                case 4:
//                    icon.setImageResource(R.drawable.gr_flag);
//                    locale = new Locale("el");
//                    break;
//                case 5:
//                    icon.setImageResource(R.drawable.es_flag);
//                    locale = new Locale("es", "ES");
//                    break;
//                case 6:
//                    icon.setImageResource(R.drawable.mx_flag);
//                    locale = new Locale("es", "MX");
//                    break;
//                case 7:
//                    icon.setImageResource(R.drawable.eo_flag);
//                    locale = new Locale("eo");
//                    break;
//                case 8:
//                    icon.setImageResource(R.drawable.fr_flag);
//                    locale = new Locale("fr", "FR");
//                    break;
//                case 9:
//                    icon.setImageResource(R.drawable.haxor_flag);
//                    locale = new Locale("io");
//                    break;
//                case 10:
//                    icon.setImageResource(R.drawable.ja_flag);
//                    locale = new Locale("ja");
//                    break;
//                case 11:
//                    icon.setImageResource(R.drawable.ua_flag);
//                    locale = new Locale("uk");
//                    break;
//                case 12:
//                    icon.setImageResource(R.drawable.hk_flag);
//                    locale = new Locale("zh", "HK");
//                    break;
//                case 13:
//                    icon.setImageResource(R.drawable.tw_flag);
//                    locale = new Locale("zh", "TW");
//                    break;
//                default:
//                    icon.setImageResource(R.drawable.thl_flag);
//                    locale = new Locale("en");
//                    break;
//            }
//            TextView label = (TextView) row.findViewById(R.id.langTxt0);
//            label.setText(supTxt[position]);
//            TextView subLabel = (TextView) row.findViewById(R.id.langTxt1);
//            subLabel.setText(subTxt[position]);
//            Log.i("Position: ", Integer.toString(position));
//            return row;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.old_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i(getString(android.R.string.copy), "wow");
            Intent intent = new Intent(this, PrefsClass.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
        //The animation override must be in onPause() for some reason
    }
}
