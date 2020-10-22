package com.example.customlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customlistview.R;
import com.example.customlistview.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private ArrayList<Contact> arrContact;

    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource= resource;
        this.arrContact=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_item_contact,parent,false);
        TextView tv_color = (TextView) convertView.findViewById(R.id.tv_color);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_number);
        Contact contact = arrContact.get(position);
        tv_color.setBackgroundColor(contact.getmColor());
        tv_color.setText((position+1)+"");
        tv_name.setText(contact.getmName());
        tv_number.setText(contact.getmNumber());

        return convertView;
    }
    public  void  removeItem(ArrayList<Contact> items)
    {
        for (Contact item : items)
        {
            arrContact.remove(item);
        }
        notifyDataSetChanged();
    }
}
