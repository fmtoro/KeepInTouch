package com.ftpha.keepintouch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

/**
 * Created by Fernando on 2015-05-13.
 * Originally created as part of: Keep in Touch
 * You will love this code and be awed by it's magnificence
 */
public class ftListAdapter extends RecyclerView.Adapter<ftListAdapter.FtViewH> {

    private LayoutInflater inflater;
    private List<ftList> lists = Collections.emptyList();
    private Context contx;

    public final static String XtraInfo = "com.ftpha.KIT.MESSAGE";

    public ftListAdapter(Context context, List<ftList> lists){
        inflater= LayoutInflater.from(context);
        this.lists = lists;
        contx = context;
    }


    @Override
    public FtViewH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_one_row, viewGroup, false);
        FtViewH holder = new FtViewH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FtViewH vHolder, int i) {
        ftList current = lists.get(i);
        vHolder.lName.setText(current.getLName());
        vHolder.lId.setText(String.valueOf(current.getLId()));
        vHolder.lMessage.setText(current.getLText());
        
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class FtViewH extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        TextView lName;
        TextView lId;
        TextView lMessage;
        public FtViewH(View itemView) {
            super(itemView);
            lId = (TextView) itemView.findViewById(R.id.txtListId);
            lName = (TextView) itemView.findViewById(R.id.itemName);
            lMessage = (TextView) itemView.findViewById(R.id.txtFullMsg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            long tmpL = Long.valueOf(lId.getText().toString());

            final Intent listEditIntent = new Intent(v.getContext(), AddListItem.class);
            listEditIntent.putExtra(XtraInfo + "xAddModeX", 746);
            listEditIntent.putExtra(XtraInfo + "lID", tmpL);

            v.getContext().startActivity(listEditIntent);



        }
    }

}
