package com.ftpha.keepintouch;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class edit_list extends ActionBarActivity {

    private EditText uName;
    private EditText uPhone;
    private EditText uEmail;
    private int uID;

    private String uriStr;
    private  File ftile = null;
    private String currPhPath;
    private Uri elUri;

    ImageView viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        uName = (EditText) findViewById(R.id.txtUName);
        uPhone = (EditText) findViewById(R.id.txtUPhone);
        uEmail = (EditText) findViewById(R.id.txtUEmail);
        viewImage=(ImageView)findViewById(R.id.uImage);

        Bundle bundle = getIntent().getExtras();

        uID = Integer.parseInt(bundle.getString(ftAdapter.XtraInfo + "usrID"));

        if(!bundle.getString(ftAdapter.XtraInfo + "uName").equals(""))
        {
            uName.setText(bundle.getString(ftAdapter.XtraInfo + "uName"));
        } else {
            uName.setText("");
        }



        if(!bundle.getString(ftAdapter.XtraInfo + "uPhone").equals(""))
        {
            uPhone.setText(bundle.getString(ftAdapter.XtraInfo + "uPhone"));
        } else {
            uPhone.setText("");
        }



        if(!bundle.getString(ftAdapter.XtraInfo + "uEmail").equals(""))
        {
            uEmail.setText(bundle.getString(ftAdapter.XtraInfo + "uEmail"));
        } else {
            uEmail.setText("");
        }



        if(!bundle.getString(ftAdapter.XtraInfo + "uImage").equals(""))
        {
            Uri selectedImage = Uri.parse(bundle.getString(ftAdapter.XtraInfo + "uImage"));
            uriStr = selectedImage.toString();
            Glide.with(this).load(selectedImage).into(viewImage);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        User u = new User();
        u.setUId(uID);
        u.setUName(uName.getText().toString());
        u.setUPhone(uPhone.getText().toString());
        u.setUEmail(uEmail.getText().toString());
        u.setUImge(uriStr);
        u.updateUser(this);
    }

    public void onImgClicked(View view) {

        selectImage();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Glide.with(this).load(elUri).into(viewImage);
                galleryAddPic();

            } else if (requestCode == 2) {



                Uri selectedImage = data.getData();

                uriStr = selectedImage.toString();

                Glide.with(this).load(selectedImage).into(viewImage);

            }
        }

    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currPhPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void selectImage() {

        final CharSequence[] options = {
                getString(R.string.UseTheCamera),
                getString(R.string.fromGallery),
                getString(R.string.Cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(edit_list.this);
        builder.setTitle(getString(R.string.addPhoto));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.UseTheCamera)))
                {
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
                }
                else if (options[item].equals(getString(R.string.fromGallery)))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals(getString(R.string.Cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
}
