package com.ftpha.keepintouch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import java.util.ArrayList;
//import java.util.List;

public class AddUser extends Activity {


    private static final String LOGTAG = "ftpha - sca c r ";
    private static final int CONTACT_PICKER_RESULT = 1001;

    private EditText uName;
    private EditText uPhone;
    private EditText uEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        uName = (EditText) findViewById(R.id.txtUNameA);
        uPhone = (EditText) findViewById(R.id.txtUPhoneA);
        uEmail = (EditText) findViewById(R.id.txtUEmailA);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    Cursor cursorEm;
                    String uNameA;
                    String email;
                    String phoneNumber = "";
                    //List<String> allNumbers = new ArrayList<String>();
                    try {
                        Uri result = data.getData();
                        Log.i(LOGTAG, "Got a contact result: "
                                + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        cursor = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                                new String[] { id },
                                null);


                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                        // let's just get the first phone
                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {
                                phoneNumber = cursor.getString(phoneIdx);
                                //allNumbers.add(phoneNumber);
                                cursor.moveToNext();
                            }
                        }


                        cursorEm = getContentResolver().query(Email.CONTENT_URI,
                                null, Email.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int emailIdx = cursor.getColumnIndex(Email.DATA);

                        // let's just get the first email
                        if (cursorEm.moveToFirst()) {
                            email = cursorEm.getString(emailIdx);
                            uNameA = cursorEm.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            //Toast.makeText(MainActivity.this, "Got email: " + email, Toast.LENGTH_LONG).show();
                            uEmail.setText(email);
                            uName.setText(uNameA);
                        } else {
                            Log.i(LOGTAG, "No results");
                        }

                    } catch (Exception e) {
                        Log.e(LOGTAG, "Failed to get phone data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }

                        uPhone.setText(phoneNumber);
                        if (phoneNumber.length() == 0) {
                            Toast.makeText(this, "No phone found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }

        } else {
            Log.i(LOGTAG, "Warning: activity result not ok");
        }
    }



    public void onSave(View view) {

        User u = new User();
        u.setUName(uName.getText().toString());
        u.setUPhone(uPhone.getText().toString());
        u.setUEmail(uEmail.getText().toString());
        u = u.createUser(u, this);

        finish();

    }
    public void onGetContact(View view) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

}
