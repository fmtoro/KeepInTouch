package com.ftpha.keepintouch;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import java.util.ArrayList;
//import java.util.List;

public class AddUser extends Activity {


    private static final String LOGTAG = "ftpha - sca c r ";
    private static final int CONTACT_PICKER_RESULT = 1001;

    private EditText uName;
    private EditText uPhone;
    private EditText uEmail;

    private File ftile;

    private String currPhPath;
    private Uri elUri;
    private String uriStr;
    private ImageView viewImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        uName = (EditText) findViewById(R.id.txtUNameA);
        uPhone = (EditText) findViewById(R.id.txtUPhoneA);
        uEmail = (EditText) findViewById(R.id.txtUEmailA);
        viewImage = (ImageView) findViewById(R.id.addUserImage);

        doGetContact();

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
                    getContactResult(data);
                    break;
                case 1:
                    takePictResult();
                    break;
                case 2:
                    SelectImgResult(data);
                    break;
            }

        } else {
            Log.i(LOGTAG, "Warning: activity result not ok");
        }
    }

    private void getContactResult(Intent data) {
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
                uNameA = cursorEm.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
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
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currPhPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void selectImage() {

        final CharSequence[] options = {
                getString(R.string.getContact),
                getString(R.string.UseTheCamera),
                getString(R.string.fromGallery),
                getString(R.string.Cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
        builder.setTitle(getString(R.string.importData) )
        .setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.getContact))) {
                    doGetContact();
                }
                if (options[item].equals(getString(R.string.UseTheCamera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                    try {
                        ftile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    elUri = Uri.fromFile(ftile);
                    uriStr = elUri.toString();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, elUri);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals(getString(R.string.fromGallery))) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals(getString(R.string.Cancel))) {
                    dialog.dismiss();
                }
            }
        }).show();
    }

    private void takePictResult() {
        Glide.with(this).load(elUri).into(viewImage);
        //galleryAddPic();
    }

    private void SelectImgResult(Intent data) {
        Uri selectedImage = data.getData();

        uriStr = selectedImage.toString();

        Glide.with(this).load(selectedImage).into(viewImage);
    }

    public void onSave(View view) {

        User u = new User();
        u.setUName(uName.getText().toString());
        u.setUPhone(uPhone.getText().toString());
        u.setUEmail(uEmail.getText().toString());

        if (uriStr == null) {uriStr = "";}
        u.setUImge(uriStr);
        u.setUFrom("1");
        u.setUTo("5");
        u.setUUnit("Week");
        u.setUUseSMS("Yes");
        u.setUUseEmail("No");
        u.setUJustMF("Yes");
        u.setUActive("Yes");

        u = u.createUser(u, this);
        finish();

    }

    private  void doGetContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    public void onGetContact(View view) {
        doGetContact();
    }

    public void onImgClick(View view) {
        selectImage();
    }
}
