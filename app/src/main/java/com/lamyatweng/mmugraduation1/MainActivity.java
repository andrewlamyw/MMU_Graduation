package com.lamyatweng.mmugraduation1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lamyatweng.mmugraduation1.Programme.ProgrammeFragment;
import com.lamyatweng.mmugraduation1.Student.StudentFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_view);

        /** Redirect non-logged in user to LoginActivity */
        final SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // Set up ActionBar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up Open and Close drawer with the App Icon
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,               /* Toolbar to inject into */
                R.string.drawer_open,         /* "open drawer" description */
                R.string.drawer_close         /* "close drawer" description */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        final NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (findViewById(R.id.fragment_container) != null) {

                    if (savedInstanceState != null) {
                        // If we're being restored from a previous state,
                        // then we don't need to do anything and should return or else
                        // we could end up with overlapping fragments.
                        return false;
                    }

                    switch (menuItem.getTitle().toString()) {
                        case "Program":
                            ProgrammeFragment programmeFragment = new ProgrammeFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, programmeFragment).commit();
                            Snackbar.make(view, "Loading", Snackbar.LENGTH_LONG).show();
                            break;
                        case "Profile":
                            ProfileFragment profileFragment = new ProfileFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                            getSupportActionBar().setTitle("Profile");
                            break;
                        case "Student":
                            StudentFragment studentFragment = new StudentFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, studentFragment).commit();
                            break;
                        case "Logout":
                            session.logoutUser();
                            break;
                    }
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    // for Open and Close with the ActionBar Icon purpose
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // if current activity is main/home activity, then clicking the menu/appbar icon will open drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // for Open and Close with the ActionBar Icon purpose
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    // for Open and Close with the ActionBar Icon purpose
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
