package com.nba.guide;


public class PdfDataModel {

    int id,pdf_total;

    String title, description,app_icon,hindi,date_time,category_title;


    public PdfDataModel() {

    }

    public PdfDataModel(int id, int pdf_total, String title, String description, String app_icon, String hindi,String date_time,String category_title){
        this.title = title;
        this.id = id;
        this.description= description;
        this.app_icon = app_icon;
        this.pdf_total = pdf_total;
        this.hindi = hindi;
        this.date_time = date_time;
        this.category_title= category_title;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setHindi(String hindi) {
        this.hindi = hindi;
    }


    public String getHindi() {
        return hindi;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPdf_total() {
        return pdf_total;
    }

    public void setPdf_total(int pdf_total) {
        this.pdf_total = pdf_total;
    }
    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }






}


