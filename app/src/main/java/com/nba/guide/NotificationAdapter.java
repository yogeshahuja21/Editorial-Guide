package com.nba.guide;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends BaseAdapter{

    Holder holder;

    private final Activity context;

    private final List<NotificationModel> list;

    private static LayoutInflater inflater=null;

    Constant constant;

    public NotificationAdapter(Activity context, List<NotificationModel> itemname) {

        this.context = context;

        this.list = itemname;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        constant = new Constant(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView noificaton_details,date_ad;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        holder=new Holder();

        final View rowView;

        NotificationModel model = list.get(position);

        rowView = inflater.inflate(R.layout.notification_list, null);

        holder.noificaton_details=(TextView) rowView.findViewById(R.id.noificaton_details);

        holder.date_ad=(TextView) rowView.findViewById(R.id.date_ad);

        holder.noificaton_details.setText(model.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm:ss");

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy hh:mm a");

        Date date =null;

        try {
            date = dateFormat.parse(model.getNotification_date());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date_ad.setText(dateFormat1.format(date));


        return rowView;
    }
}
