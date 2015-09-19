package com.mynetgear.dord.platypus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexandre Poon on 15.09.03.
 */
public class ActivityNeue extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private SharedPreferences sharedPreferences;
    private static final String SHAREDPREFS_NAME = "settings";
    private static final String SHAREDPREFS_LOCALE = "LOCALE";
    private static final String DEFAULT_LOCALE = "en_UK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHAREDPREFS_NAME, MODE_PRIVATE);
        updateLocale();
        setContentView(R.layout.layout_main);
        setSupportActionBar((Toolbar) findViewById(R.id.support_toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_new);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_view_new);
        setupDrawerContent(navigationView);

        updateComponents();
    }

    private void updateLocale() {
        String localCode = sharedPreferences.getString(SHAREDPREFS_LOCALE, DEFAULT_LOCALE);
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

    private void updateComponents() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        if (viewPager != null) {
            setPagerAdapter(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inflated, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivityPrefs.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public CustomFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private void setPagerAdapter(ViewPager viewPager) {
        CustomFragmentPagerAdapter customFPA = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        customFPA.addFragment(new DesignFragment(), "Pomp");
        customFPA.addFragment(new CocktailMapFragment(), "Map");
        customFPA.addFragment(new CocktailMapFragment(), "Recycler");
        viewPager.setAdapter(customFPA);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class sFpAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public sFpAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
