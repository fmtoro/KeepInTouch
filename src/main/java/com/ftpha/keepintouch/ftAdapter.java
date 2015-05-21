package com.ftpha.keepintouch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by Fernando on 2015-05-13.
 * Originally created as part of: Keep in Touch
 * You will love this code and be awed by it's magnificence
 */
public class ftAdapter extends RecyclerView.Adapter<ftAdapter.FtViewH> {

    private LayoutInflater inflater;
    private List<User> users = Collections.emptyList();
    private Context contx;

    public final static String XtraInfo = "com.ftpha.KIT.MESSAGE";

    public ftAdapter(Context context, List<User> users){
        inflater= LayoutInflater.from(context);
        this.users = users;
        contx = context;
    }


    @Override
    public FtViewH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.one_row, viewGroup, false);
        FtViewH holder = new FtViewH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FtViewH vHolder, int i) {
        User current = users.get(i);
        vHolder.uName.setText(current.getUName());
        vHolder.uPhone.setText(current.getUPhone());
        vHolder.uEmail.setText(current.getUEmail());
        vHolder.txtId.setText(String.valueOf(current.getUId()));
        vHolder.uImgStr.setText(current.getUImge());
        Uri selectedImage = Uri.parse(current.getUImge());
        if (!current.getUImge().equals("")) {
            Glide.with(contx).load(selectedImage).into(vHolder.uImg);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class FtViewH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView uImg;
        TextView uName;
        TextView uPhone;
        TextView uEmail;
        TextView txtId;
        TextView uImgStr;
        public FtViewH(View itemView) {
            super(itemView);
            uImg = (ImageView) itemView.findViewById(R.id.uImg);
            uName = (TextView) itemView.findViewById(R.id.uName);
            uPhone = (TextView) itemView.findViewById(R.id.uPhone);
            uEmail = (TextView) itemView.findViewById(R.id.uEmail);
            txtId = (TextView) itemView.findViewById(R.id.txtID);
            uImgStr = (TextView) itemView.findViewById(R.id.uImgStr);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            final Intent usrEditIntent = new Intent(v.getContext(), edit_user.class);
            usrEditIntent.putExtra(XtraInfo + "uName", uName.getText());
            usrEditIntent.putExtra(XtraInfo + "uPhone", uPhone.getText());
            usrEditIntent.putExtra(XtraInfo + "uEmail", uEmail.getText());
            usrEditIntent.putExtra(XtraInfo + "usrID", txtId.getText());
            usrEditIntent.putExtra(XtraInfo + "uImage", uImgStr.getText());

            v.getContext().startActivity(usrEditIntent);



        }
    }

}
