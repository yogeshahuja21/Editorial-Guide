package com.nba.guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GuidLineAdapter extends BaseAdapter {

    Holder holder;

    private final Activity context;

    private final List<PdfDataModel> list;

    private static LayoutInflater inflater=null;

    Constant constant;

    String eng ;

    String data;

    String hind,englishurl,hindiurl;

    private WebView webView;


    public GuidLineAdapter(Activity context, List<PdfDataModel> itemname,String data) {

        this.context = context;

        this.list = itemname;

        this.data = data;

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
        TextView guid_date,detail;

        TextView englis_ver,hindi_ver,dateText;

        View view;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder=new Holder();

        final View rowView;

        PdfDataModel pdfDataModel = list.get(position);

        rowView = inflater.inflate(R.layout.guidline_list, null);

        holder.guid_date=(TextView) rowView.findViewById(R.id.guid_date);

        holder.englis_ver =(TextView)rowView.findViewById(R.id.englis_ver);

        holder.hindi_ver =(TextView)rowView.findViewById(R.id.hindi_ver);

        holder.detail=(TextView) rowView.findViewById(R.id.detail_guid);

        holder.dateText=(TextView) rowView.findViewById(R.id.dateText);

        holder.view=(View) rowView.findViewById(R.id.view);

        holder.guid_date.setText(pdfDataModel.getDate_time());

        holder.detail.setText(pdfDataModel.getTitle());

        holder.dateText.setText(parseDateToddMMyyyy(pdfDataModel.getDate_time()));

//        if ( position % 2 == 0){
//            rowView.setBackgroundColor(Color.parseColor("#FCFCFC"));
//        }
//         else{
//            rowView.setBackgroundColor(Color.parseColor("#EDEDED"));
//        }

        System.out.println("hindi"+pdfDataModel.getHindi());
        System.out.println("ENg" + pdfDataModel.getDescription());
        String hindi = pdfDataModel.getHindi();
        String eng = pdfDataModel.getDescription();
        if(!hindi.equalsIgnoreCase("")&& !eng.equalsIgnoreCase("")){
            holder.view.setVisibility(View.VISIBLE);

        }else {
            holder.view.setVisibility(View.GONE);

        }

        if(data.equalsIgnoreCase("2")){
            holder.englis_ver.setVisibility(View.GONE);
            holder.hindi_ver.setVisibility(View.GONE);
        }else {
            holder.englis_ver.setVisibility(View.VISIBLE);
            holder.hindi_ver.setVisibility(View.VISIBLE);
        }

        SpannableString content = new SpannableString("Hindi version");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.hindi_ver.setText(content);

        SpannableString content1 = new SpannableString("English version");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        holder.englis_ver.setText(content1);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PdfDataModel pdfDataModel = list.get(position);

                System.out.println("Language" + pdfDataModel.getDescription() + "Hindi" + pdfDataModel.getHindi());

                Intent intent = new Intent(context,Pdfviewtow.class);
                intent.putExtra("ENG",pdfDataModel.getDescription());
                intent.putExtra("HIND",pdfDataModel.getHindi());
                intent.putExtra("Type" ,"E");
                intent.putExtra("texttit", pdfDataModel.getCategory_title());
                context.startActivity(intent);

            }
        });
        holder.englis_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PdfDataModel pdfDataModel = list.get(position);

                System.out.println("Language" + pdfDataModel.getDescription() + "Hindi" + pdfDataModel.getHindi());

                Intent intent = new Intent(context,Pdfviewtow.class);
                intent.putExtra("ENG",pdfDataModel.getDescription());
                intent.putExtra("HIND",pdfDataModel.getHindi());
                intent.putExtra("Type" ,"E");
                intent.putExtra("texttit", pdfDataModel.getCategory_title());
                context.startActivity(intent);

            }
        });

        holder.hindi_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDataModel pdfDataModel = list.get(position);
                System.out.println("Language" + pdfDataModel.getDescription()+"Hindi"+pdfDataModel.getHindi());
                Intent intent = new Intent(context,Pdfviewtow.class);
                intent.putExtra("HIND",pdfDataModel.getHindi());
                intent.putExtra("ENG",pdfDataModel.getDescription());
                intent.putExtra("Type" ,"H");
                intent.putExtra("texttit",pdfDataModel.getCategory_title());
                context.startActivity(intent);

            }
        });



        return rowView;
    }

    public String parseDateToddMMyyyy(String time) {

        String str = null;

        String inputPattern = "dd MMMM yyyy";

        String outputPattern = "dd\nMMM";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;

        try {
            date = inputFormat.parse(time);

            str = outputFormat.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return str;
    }
}
