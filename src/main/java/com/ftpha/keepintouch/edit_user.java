package com.ftpha.keepintouch;


import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class edit_user extends ActionBarActivity {
//**************************  The views
    private int uID;
    private EditText uName;
    private EditText uPhone;
    private EditText uEmail;
    private EditText uFrom;
    private EditText uTo;
    private RadioButton rbDay;
    private RadioButton rbWeek;
    private RadioButton rbMonth;
    private CheckBox ckbxOffHours;
    private CheckBox ckbxMtF;
    //*************************************
    private String uriStr;
    private  File ftile = null;
    private String currPhPath;
    private Uri elUri;

    dsKit dS;

    public final static String XtraInfo = "com.ftpha.KIT.MESSAGE";

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

        User u = new User();
        u.getUser(uID, this);



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

        setupRecyclerView();

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Aqui after this point I get a crash ??? I think before getting to the calling activity???
    }

    private void setupRecyclerView() {  //Aqui pasar a List
        dS = new dsKit(this);
        dS.Open();
        List<ftList> ftLs = dS.findListForUser(uID);


        RecyclerView ftRV = (RecyclerView) findViewById(R.id.Lists);

        ftRV.setHasFixedSize(true);

        ftRV.setLayoutManager(new LinearLayoutManager(this));

        ftListAdapter adapter = new ftListAdapter(this, ftLs);
        ftRV.setAdapter(adapter);
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

                takePictResult();

            } else if (requestCode == 2) {


                SelectImgResult(data);

            }
        }

    }

    private void SelectImgResult(Intent data) {
        Uri selectedImage = data.getData();

        uriStr = selectedImage.toString();

        Glide.with(this).load(selectedImage).into(viewImage);
    }

    private void takePictResult() {
        Glide.with(this).load(elUri).into(viewImage);
        galleryAddPic();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(edit_user.this);
        builder.setTitle(getString(R.string.addPhoto));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

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
        });
        builder.show();
    }

    public void onSave(View view) {

        super.onPause();
        User u = new User();
        u.setUId(uID);
        u.setUName(uName.getText().toString());
        u.setUPhone(uPhone.getText().toString());
        u.setUEmail(uEmail.getText().toString());
        if (uriStr == null) {uriStr = "";}
        u.setUImge(uriStr);




        u.updateUser(this);

        finish();

    }

    public void onBack(View view) {
        finish();
    }

    public void onAddListItem(View view) {

        final Intent  addLItemIntent = new Intent(view.getContext(), AddListItem.class);

        addLItemIntent.putExtra(XtraInfo + "userID", uID);

        view.getContext().startActivity(addLItemIntent);

    }



}
