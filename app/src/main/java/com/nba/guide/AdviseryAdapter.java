package com.nba.guide;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdviseryAdapter extends BaseAdapter {

    String [] result;

    Context context;

    private static LayoutInflater inflater=null;

    public AdviseryAdapter(Advisery advisery, String[] prgmNameList) {

        result = prgmNameList;

        context = advisery ;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.length;
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
        TextView date_ad;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.advisry_list, null);

        holder.date_ad=(TextView) rowView.findViewById(R.id.date_ad);

        holder.date_ad.setText(result[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfView.class);
                context.startActivity(intent);

            }

        });

        return rowView;

    }
}
