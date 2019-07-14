package com.nba.guide;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


public class DashBoardAdapter extends BaseAdapter {


    Holder holder;

    private final Activity context;

    private final List<CategriesModel> list;

    private static LayoutInflater inflater=null;

    Constant constant;

    public DashBoardAdapter(Activity context, List<CategriesModel> itemname) {

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
        TextView title_template,discription;

        ImageView image_view;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

         holder=new Holder();

         final View rowView;

         CategriesModel model = list.get(position);

         rowView = inflater.inflate(R.layout.layout_dash, null);

         holder.title_template =  rowView.findViewById(R.id.title_template);

         holder.discription = rowView.findViewById(R.id.discription);

         holder.image_view = rowView.findViewById(R.id.image_view);

         holder.title_template.setText(model.getTitle());

         System.out.println("HOME" + (model.getTitle()));

         holder.discription.setText(model.getDescription());

        String stingvalue = model.getApp_icon();
        try {
            String afterDecode = URLDecoder.decode(stingvalue, "UTF-8");
            Picasso.get().load(afterDecode).into(holder.image_view);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

         return rowView;

    }
}
