package com.omnibuttie.therable.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.omnibuttie.therable.R;
import com.omnibuttie.therable.adapters.DrawerAdapter;
import com.omnibuttie.therable.adapters.RowItem;
import com.omnibuttie.therable.views.cards.EntryCard;
import com.omnibuttie.therable.views.fragments.CalendarFragment;
import com.omnibuttie.therable.views.fragments.JournalCards;
import com.omnibuttie.therable.views.fragments.PerEmotionTrackFragment;
import com.omnibuttie.therable.views.fragments.RadarChartFragment;
import com.omnibuttie.therable.views.fragments.WeeklyChartFragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity implements
        CalendarFragment.OnFragmentInteractionListener,
        WeeklyChartFragment.OnFragmentInteractionListener,
        RadarChartFragment.OnFragmentInteractionListener,
        PerEmotionTrackFragment.OnFragmentInteractionListener,
        JournalCards.OnFragmentInteractionListener {
    final int WRITEREQUESTCODE = 101;
    String[] menuTitles;
    TypedArray menuIcons;
    private CharSequence drawerTitle;
    private CharSequence title;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private List<RowItem> rowItems;
    private DrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        setContentView(R.layout.activity_main);

        title = drawerTitle = getTitle();

        menuTitles = getResources().getStringArray(R.array.menuTitles);
        menuIcons = getResources().obtainTypedArray(R.array.menuIcons);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.slider_list);

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < menuTitles.length; i++) {
            RowItem item = new RowItem(menuTitles[i], menuIcons.getResourceId(i, -1));
            rowItems.add(item);
        }

        menuIcons.recycle();

        adapter = new DrawerAdapter(getApplicationContext(), rowItems);
        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            updateDisplay(0);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.write_settings) {
            openComposer(-1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openComposer(long journalEntryID) {
        Intent intent = new Intent(this, Composer_alternate.class);
        intent.putExtra("JournalID", journalEntryID);
        startActivityForResult(intent, WRITEREQUESTCODE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.write_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void updateDisplay(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = JournalCards.newInstance(EntryCard.VIEW_ALL, null);
                break;
            case 1:
                fragment = JournalCards.newInstance(EntryCard.VIEW_ARCHIVE, null);
                break;
            case 2:
                fragment = WeeklyChartFragment.newInstance();
                break;
            case 3:
                fragment = RadarChartFragment.newInstance();
                break;
            case 4:
                fragment = CalendarFragment.newInstance();
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            setTitle(menuTitles[position]);
            drawerLayout.closeDrawer(drawerList);
        } else {
            Log.e("MainActivity", "Error creating fragment");
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            updateDisplay(position);
        }
    }

}
