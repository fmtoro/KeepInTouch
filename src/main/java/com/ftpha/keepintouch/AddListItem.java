package com.ftpha.keepintouch;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;


public class AddListItem extends Activity {

    private ftList ftL;
    private EditText lName;
    private EditText lMssg;
    private long uID;
    private long lID;
    private boolean addMode;
    private int xAddModeX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list);

        lName = (EditText) findViewById(R.id.txtMssgName);
        lMssg = (EditText) findViewById(R.id.txtMessage);
        long tL, tU;


        Bundle bundle = getIntent().getExtras();


        xAddModeX =  bundle.getInt(ftAdapter.XtraInfo + "xAddModeX");

        addMode = (xAddModeX == 466);

        if (addMode) {
            tU = bundle.getLong(ftAdapter.XtraInfo + "userID");
            uID = tU;
        } else {
            tL = bundle.getLong(ftAdapter.XtraInfo + "lID");
            lID = tL;
            populateForm();
        }


    }


    public void XXX(View view) {

        doSave();

    }

    private void populateForm(){
        ftList l = new ftList();

        l.getListItem(lID, AddListItem.this);

        lName.setText(l.getLName());
        lMssg.setText(l.getLText());
        uID = l.getUId();

    }

    private void doSave() {

        ftList l = new ftList();
        l.setLName(lName.getText().toString());
        l.setLText(lMssg.getText().toString());
        l.setUId(uID);

        if (addMode) {
            l = l.createListItem(this);
        } else {
            l.setLId(lID);
            l.updateListItem(this);
        }


        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_list_item, menu);
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

    public void onCancel(View view) {
        finish();
    }
}
