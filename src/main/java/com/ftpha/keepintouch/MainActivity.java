package com.ftpha.keepintouch;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.List;



public class MainActivity extends Activity {


    private static final String LOGTAG = "ftpha - sca c r ";


    dsKit dS;

    private String Mach5; //todo: dar nombre logico Este es el Tlf. al que se manda el SMS
    private String elMelange;//aqui: dar nombre logico Este es el mensaje mismo

    private ActionButton flotR;

    public MainActivity() {
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setupRecyclerView();

        flotR.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_bar);


        setupRecyclerView();
        flotR = (ActionButton) findViewById(R.id.floater);
        flotR.setShadowRadius(6.0f);
        flotR.setButtonColorPressed(R.color.accent_dark);
        addListenerOnButton();

        // This with the Divider class give division line between items.
        //ftRV.addItemDecoration(
        //        new DividerItemDecoration(
        //                getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        //ftRV.setItemAnimator(new DefaultItemAnimator());

//        ftRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//
//            @Override
//            public void onTouchEvent(RecyclerView recycler, MotionEvent event) {
//
//            }
//
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recycler, MotionEvent event) {
//                return false;
//            }
//        });


    }

    private void setupRecyclerView() {
        dS = new dsKit(this);
        dS.Open();
        List<User> users = dS.findAllUsers();


        RecyclerView ftRV = (RecyclerView) findViewById(R.id.ftRV);

        ftRV.setHasFixedSize(true);

        ftRV.setLayoutManager(new LinearLayoutManager(this));

        ftAdapter adapter = new ftAdapter(this, users);
        ftRV.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void SendSMS() {


        //EditText etPh = (EditText) findViewById(R.id.txtPhone);// ToDo: Esto hay que pasarlo a una variable
        //EditText etMsg = (EditText) findViewById(R.id.txtMessage);// ToDo: Esto hay que pasarlo a una variable

        //int elNo = Integer.parseInt(tLg.getText().toString());

        try {
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 1; i < (2); i++) {
                smsManager.sendTextMessage(
                        Mach5,
                        null,
                        elMelange + "\n" + i,
                        null,
                        null);

            }
            Toast.makeText(
                    MainActivity.this,
                    "Was sent",
                    Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            Log.i(LOGTAG, e.getMessage());
            Toast.makeText(
                    MainActivity.this,
                    "No way",
                    Toast.LENGTH_LONG)
                    .show();

        }





    }


    public void addListenerOnButton() {


        flotR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                flotR.hide();

                final Intent addtent = new Intent(v.getContext(), AddUser.class);


                Thread t1 = new Thread(){
                    public void run(){
                        try{
                            sleep(400);
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        finally{
                            startActivity(addtent);
                        }
                    }
                };

                t1.start();


            }

        });

    }



}
