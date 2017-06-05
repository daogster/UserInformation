package com.example.axis_inside.tf_exp_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static int batLabel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.registerReceiver(this.powerBroadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        this.unregisterReceiver(this.powerBroadcast);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.powerBroadcast);

    }

    private BroadcastReceiver powerBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            batLabel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        }
    };

    public void getSystemPowerDetails(View view){
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            batLabel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }else{

        }
        Toast.makeText(this, "Current Battery Label is "+batLabel + "%", Toast.LENGTH_SHORT).show();
    }

    public void getSmsDetails(View view){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("TYPE","SMS");
        startActivity(intent);
    }

    public void getCallLogs(View view){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("TYPE","CALLS");
        startActivity(intent);
    }

    public void getBrowsingData(View view){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("TYPE","BROWSER");
        startActivity(intent);
    }

    public void getOtherInstalledApps(View view){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("TYPE","APPS");
        startActivity(intent);
    }

    public void getContactsDetails(View view){
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("TYPE","CONTACT");
        startActivity(intent);
    }
}
