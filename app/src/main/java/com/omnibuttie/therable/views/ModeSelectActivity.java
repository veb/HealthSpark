package com.omnibuttie.therable.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.omnibuttie.therable.R;
import com.omnibuttie.therable.TherableApp;
import com.omnibuttie.therable.provider.journalentry.EntryType;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ModeSelectActivity extends Activity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a:
                ((TherableApp) getApplication()).setAppMode(EntryType.FITNESS);
                break;
            case R.id.tv_b:
                ((TherableApp) getApplication()).setAppMode(EntryType.PAIN);
                break;
            case R.id.tv_c:
                ((TherableApp) getApplication()).setAppMode(EntryType.MEDICAL);
                break;
            case R.id.tv_d:
                ((TherableApp) getApplication()).setAppMode(EntryType.CBT);
                break;
        }

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mode_select);

        findViewById(R.id.tv_a).setOnClickListener(this);
        findViewById(R.id.tv_b).setOnClickListener(this);
        findViewById(R.id.tv_c).setOnClickListener(this);
        findViewById(R.id.tv_d).setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mode_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }


}
