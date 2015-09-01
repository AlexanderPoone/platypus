package com.mynetgear.dord.platypus;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
//import android.content.Context;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
//import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.mynetgear.dord.platypus.db.DBDataSource;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class Atrium extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private SharedPreferences sharedPreferences;
    private static final String SHAREDPREFS_NAME = "settings";
    private static final String SHAREDPREFS_LOCALE = "LOCALE";
    private int shelf_position = 0;
    private Menu optionsMenu;
    private static DBDataSource dbDataSource;
    private static ArrayList<ArrayList<Double>> entries;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "platypus");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
        sharedPreferences = getSharedPreferences(SHAREDPREFS_NAME, MODE_PRIVATE);
        updateLocale();
        dbDataSource = new DBDataSource(this);
        mp = MediaPlayer.create(this, R.raw.english_dance);
        setContentView(R.layout.activity_atrium);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
//        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbDataSource.open();
        dbDataSource.createSailAway();
        entries = dbDataSource.findAllLatLng();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateLocale();

        /* Hard coded */
        switch (shelf_position) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            default:
                mTitle = getString(R.string.app_name);
                break;
        }
        /* Hard coded */
        restoreActionBar();
        onNavigationDrawerItemSelected(shelf_position);
        String buttonName = getString(R.string.action_example);
        Log.d(optionsMenu.toString(), "LALALA");
        if (optionsMenu != null) {
            try {
                optionsMenu.findItem(R.id.action_example).setTitle(buttonName);
            } catch (Throwable throwable) {
                Log.wtf("ERROR", throwable.getMessage());
            }
        }
    }

    protected void updateLocale() {
        Log.i("From Atrium", sharedPreferences.getString(SHAREDPREFS_LOCALE, "mk"));
        if (SHAREDPREFS_LOCALE != null) {
            String localCode = sharedPreferences.getString(SHAREDPREFS_LOCALE, "mk");
            Locale myLocale;
            if (localCode.length() > 2) {
                myLocale = new Locale(localCode.substring(0, 2), localCode.substring(3));
            } else {
                myLocale = new Locale(localCode);
            }
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.setLocale(myLocale);
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.transition_startnew, R.anim.transition_pausemain);
        dbDataSource.close();
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        shelf_position = position;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.atrium, menu);
            restoreActionBar();
            if (optionsMenu == null) {
                optionsMenu = menu;
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PrefsClass.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    if (mp.isPlaying()) {
                        mp.pause();
                    }
                    rootView = inflater.inflate(R.layout.fragment_atrium, container, false);
                    break;
                case 3:
                    rootView = typeThree(inflater, container);
                    break;
                default:
                    rootView = typeTwo(inflater, container);
                    break;
            }
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Atrium) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        public class PatternThread extends Thread {
            private View rootView;

            public PatternThread(View view) {
                rootView = view;
            }

            @Override
            public void run() {
                Drawable d = getActivity().getResources().getDrawable(R.drawable.polka_dots);
                Bitmap src = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true); //Must copy the original to make it mutable
                for (int x = 0; x < src.getWidth(); x++) {
                    for (int y = 0; y < src.getHeight(); y++) {
                        if (Color.red(src.getPixel(x, y)) == 0x32 &&
                                Color.green(src.getPixel(x, y)) == 0x34 &&
                                Color.green(src.getPixel(x, y)) == 0x34) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Azure));
                        } else if (Color.red(src.getPixel(x, y)) == 0x66 &&
                                Color.green(src.getPixel(x, y)) == 0x67 &&
                                Color.green(src.getPixel(x, y)) == 0x67) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Violet));
                        } else if (Color.red(src.getPixel(x, y)) == 0x99 &&
                                Color.green(src.getPixel(x, y)) == 0x99 &&
                                Color.green(src.getPixel(x, y)) == 0x99) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Goldenrod));
                        } else {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.ForestGreen));
                        }

                    }
                }
                BitmapDrawable backdrop = new BitmapDrawable(getActivity().getResources(), src);
                backdrop.setAntiAlias(true);
                backdrop.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT); //Look, it's the BitmapDrawable, not rootView
                rootView.setBackground(backdrop);
            }
        }

        public class PatternCloudsThread extends Thread {
            private View rootView;

            public PatternCloudsThread(View view) {
                rootView = view;
            }

            @Override
            public void run() {
                Drawable d = getActivity().getResources().getDrawable(R.drawable.clouds);
                Bitmap src = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true); //Must copy the original to make it mutable
                for (int x = 0; x < src.getWidth(); x++) {
                    for (int y = 0; y < src.getHeight(); y++) {
                        if (Color.red(src.getPixel(x, y)) == 0x08 &&
                                Color.green(src.getPixel(x, y)) == 0x04 &&
                                Color.green(src.getPixel(x, y)) == 0x04) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Azure));
                        } else if (Color.red(src.getPixel(x, y)) == 0x32 &&
                                Color.green(src.getPixel(x, y)) == 0x33 &&
                                Color.green(src.getPixel(x, y)) == 0x33) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Goldenrod));
                        } else if (Color.red(src.getPixel(x, y)) == 0x66 &&
                                Color.green(src.getPixel(x, y)) == 0x67 &&
                                Color.green(src.getPixel(x, y)) == 0x67) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.UbuntuOrange));
                        } else if (Color.red(src.getPixel(x, y)) == 0x99 &&
                                    Color.green(src.getPixel(x, y)) == 0x99 &&
                                    Color.green(src.getPixel(x, y)) == 0x99) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.DodgerBlue));
                        } else {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.ForestGreen));
                        }

                    }
                }
                BitmapDrawable backdrop = new BitmapDrawable(getActivity().getResources(), src);
                backdrop.setAntiAlias(true);
                backdrop.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT); //Look, it's the BitmapDrawable, not rootView
                rootView.setBackground(backdrop);
            }
        }

        public class TypeTwoThread extends Thread {
            public TypeTwoThread(final View rootView) {
                //                    FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.mindBlownFrame);
//                    frameLayout.addView(new MindBlown(getActivity()));
                Button button = (Button) rootView.findViewById(R.id.butt);
                String[] pHues = getActivity().getResources().getStringArray(R.array.colours);
                final TextView pholderText = (TextView) rootView.findViewById(R.id.ver);
                final ColorArrayAdapter listAdapter = new ColorArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, pHues);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.coloursTitle)
                        .setIcon(R.mipmap.ic_color_lens_white_24dp)
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setAdapter(listAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pholderText.setTextColor(listAdapter.getCode(which));
                                dialog.cancel();
                            }
                        });
                final Dialog realDialog = builder.create();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        realDialog.show();
                    }
                });
                //
                Button anotherButton = (Button) rootView.findViewById(R.id.spinButt);
                final FontLinearLayout daLayout = new FontLinearLayout(new ContextThemeWrapper(getActivity(), R.style.Bear_Light));
                AlertDialog.Builder anotherBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Material_Light_Dialog));
                anotherBuilder.setView(daLayout)
                        .setTitle(R.string.font)
                        .setIcon(R.mipmap.ic_font_download_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Typeface typ = daLayout.getSelectedTypeface();
                                pholderText.setTypeface(typ);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                final Dialog fontDialog = anotherBuilder.create();
                anotherButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fontDialog.show();
                    }
                });
                Button patternButt = (Button) rootView.findViewById(R.id.patternButt);
                patternButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PatternThread patternThread=new PatternThread(rootView);
                        patternThread.start();
                    }
                });
            }
        }

        public View typeTwo(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            PatternCloudsThread patternThread = new PatternCloudsThread(rootView);
            TypeTwoThread typeTwoThread = new TypeTwoThread(rootView);
            StartNotificationThread startNotificationThread = new StartNotificationThread();
            CreateMpThread createMpThread = new CreateMpThread();
            patternThread.start();
            typeTwoThread.start();
            startNotificationThread.start();
            createMpThread.start();
            try {
                patternThread.join();
                typeTwoThread.join();
//                startNotificationThread.join();
//                createMpThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }

        public View typeThree(LayoutInflater inflater, ViewGroup container) {
            final View rootView = inflater.inflate(R.layout.map, container, false);
            MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(CENTRE_LAT_LANG, 0.2f);
            mMap.moveCamera(cameraUpdate);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);
            PolygonOptions polygonOptions = new PolygonOptions()
                    .fillColor(0x66A4C639)
                    .strokeWidth(8)
                    .strokeColor(getActivity().getResources().getColor(R.color.OliveDrab));
            for (int found = 0; found < entries.size(); found++) {
//                        final int foundFinal = found; /**For using for loop to wrap the Adapter inner method*/
                LatLng coordinates = new LatLng(entries.get(found).get(0), entries.get(found).get(1));
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(coordinates)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pointer_hub));
                mMap.addMarker(markerOptions);
                polygonOptions.add(coordinates);
            }
            mMap.addPolygon(polygonOptions);
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
//                            Log.i("Lat", Double.toString(marker.getPosition().latitude));
//                            Log.i("Lng", Double.toString(marker.getPosition().longitude));
                    ArrayList<Object> details = dbDataSource.findOthersFromLatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                    View v = getActivity().getLayoutInflater().inflate(R.layout.snippet, null);
                    TextView plcName = (TextView) v.findViewById(R.id.placeName);
                    plcName.setText((String) details.get(0));
                    TextView plcCity = (TextView) v.findViewById(R.id.placeOwner);
                    plcCity.setText((String) details.get(1));
                    StringBuilder Coors = new StringBuilder();
                    //Break
                    double latDou = marker.getPosition().latitude;
                    int latDec = Math.abs((int) latDou);
                    Coors.append(latDec);
                    Coors.append("°");
                    int latMin = (int) ((Math.abs(latDou) - latDec) * 60);
                    Coors.append(latMin);
                    Coors.append("'");
                    int latSec = (int) (((Math.abs(latDou) - latDec) * 60 - latMin) * 60);
                    Coors.append(latSec);
                    Coors.append("\"");
                    if (latDou >= 0) {
                        Coors.append("N ");
                    } else {
                        Coors.append("S ");
                    }
                    //Break
                    double lngDou = marker.getPosition().longitude;
                    int lngDec = Math.abs((int) lngDou);
                    Coors.append(lngDec);
                    Coors.append("°");
                    int lngMin = (int) ((Math.abs(lngDou) - lngDec) * 60);
                    Coors.append(lngMin);
                    Coors.append("'");
                    int lngSec = (int) (((Math.abs(lngDou) - lngDec) * 60 - lngMin) * 60);
                    Coors.append(lngSec);
                    Coors.append("\"");
                    if (lngDou >= 0) {
                        Coors.append("E");
                    } else {
                        Coors.append("W");
                    }
                    //Break
                    TextView plcLl = (TextView) v.findViewById(R.id.placeLat);
                    plcLl.setText(Coors);
                    RatingBar ratingBar = (RatingBar) v.findViewById(R.id.awesomeness);
                    ratingBar.setRating((Float) details.get(2));
                    TextView ratingText = (TextView) v.findViewById(R.id.awesomenessText);
                    ratingText.setText(details.get(2).toString());
                    ImageView plcPic = (ImageView) v.findViewById(R.id.sneakPeek);
                    int res = getActivity().getResources().getIdentifier(details.get(3).toString(), "drawable", getActivity().getPackageName());
                    plcPic.setImageResource(res);
                    TextView plcAmenities = (TextView) v.findViewById(R.id.placeLng);
                    plcAmenities.setText(details.get(4).toString());
                    TextView plcDesc = (TextView) v.findViewById(R.id.placeDesc);
                    plcDesc.setText(details.get(5).toString());
                    ImageView plcFlag = (ImageView) v.findViewById(R.id.flagSprite);
                    int flagicon = getActivity().getResources().getIdentifier(details.get(6).toString(), "drawable", getActivity().getPackageName());
                    plcFlag.setImageResource(flagicon);
                    return v;
                }
            });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String found = dbDataSource.findArticleFromLatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                    TextView articleView = (TextView) rootView.findViewById(R.id.introduction);
                    articleView.setText(Html.fromHtml(found));
                    return false;
                }
            });
            return rootView;
        }

        public class StartNotificationThread extends Thread {
            @Override
            public void run() {
                if (!NotiStarted) {
                    Notification notification = new Notification.Builder(getActivity())
                            .setContentTitle("Ma notification")
                            .setContentText("J'espère qu'il réussit.")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(false)
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    NotiStarted = false;
                }
            }

        }

        public class CreateMpThread extends Thread {
            @Override
            public void run() {
                if (!mp.isPlaying()) {
                    mp.start();
                }
            }
        }
        //End of territory of Fragment
    }

    private static final LatLng CENTRE_LAT_LANG = new LatLng(34.591307, -29.369960);
    private static boolean NotiStarted = false;
    private static MediaPlayer mp;
    private static GoogleMap mMap;
}