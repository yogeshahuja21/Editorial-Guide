package com.nba.guide;


public class CategriesModel {

    int id,pdf_total;

    String title, description,app_icon;


    public CategriesModel() {

    }

    public CategriesModel(int id,int pdf_total, String title, String description, String app_icon){
        this.title = title;
        this.id = id;
        this.description= description;
        this.app_icon = app_icon;
        this.pdf_total = pdf_total;


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



}


