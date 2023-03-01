package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    //Nguồn dữ liệu cho adapter
    private ArrayList<Contact> data;
    //Ngữ cảnh ứng dụng
    private Activity context;
    //Đối tượng phân tích layout
    private LayoutInflater inflater;

    public Adapter(){

    }

    public Adapter(ArrayList<Contact> data, Activity activity) {
        this.data = data;
        this.context = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null)
            v = inflater.inflate(R.layout.contact_layout, null);
        ImageView imgprofile = v.findViewById(R.id.imageView);
        TextView tvname = v.findViewById(R.id.ID_EDITHT);
        tvname.setText(data.get(i).getName());
        TextView tvphone = v.findViewById(R.id.ID_PHONE);
        tvphone.setText(data.get(i).getPhone());
        //Checkbox
        CheckBox checkbox = v.findViewById(R.id.ID_CHECKBOX);
        //Sự kiện thay đổi checkbox
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox.isChecked())
                {
                    data.get(i).setCheck(true);
                }
                else
                {
                    data.get(i).setCheck(false);
                }
            }
        });
        //Sự kiện điện thoại
        ImageView imgphone = v.findViewById(R.id.ID_IMGPHONE);
        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + data.get(i).getPhone()));
                context.startActivity(intentCall);
            }
        });

        //Sự kiện SMS
        ImageView imgsms = v.findViewById(R.id.ID_IMGSMS);
        imgsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSMS = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto: " + data.get(i).getPhone()));
                context.startActivity(intentSMS);
            }
        });

        return v;
    }
}
