package com.nba.guide;


public class NotificationModel {

    String id;
    String remove;
    String notification_date, description;


    public NotificationModel() {

    }

    public NotificationModel(String id,  String notification_date, String description,String remove){
        this.notification_date = notification_date;
        this.id = id;
        this.description= description;
        this.remove = remove;



    }

    public String getId() {
        return id;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }

    public String getRemove() {
        return remove;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}


