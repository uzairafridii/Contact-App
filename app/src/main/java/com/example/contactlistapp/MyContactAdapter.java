package com.example.contactlistapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.MyViewHolder>
{
    private Context mContext;
    private ArrayList<MyContact> mArrayList;

    public MyContactAdapter(Context mContext, ArrayList<MyContact> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View myView = LayoutInflater.from(mContext).inflate(R.layout.design_for_contact_recycler , null);
        MyViewHolder holder = new MyViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final MyContact myContact = mArrayList.get(i);

        myViewHolder.setUserName(myContact.getName());
        myViewHolder.setUserNumber(myContact.getNumber());

        myViewHolder.msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent msgIntent = new Intent(Intent.ACTION_SENDTO);
                msgIntent.setData(Uri.parse("smsto:" + myContact.getNumber()));
                mContext.startActivity(msgIntent);
            }
        });

        myViewHolder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + myContact.getNumber()));
                mContext.startActivity(callIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder
    {
        private TextView mUserName , mUserNumber;
        private View mView;
        private ImageButton msgButton , callButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            msgButton = mView.findViewById(R.id.messageButton);
            callButton = mView.findViewById(R.id.callButton);
        }

        public void setUserName(String userName)
        {
            mUserName = mView.findViewById(R.id.name);
            mUserName.setText(userName);
        }

        public void setUserNumber(String userNumber)
        {
            mUserNumber = mView.findViewById(R.id.contactNumber);
            mUserNumber.setText(userNumber);
        }




    }
}
