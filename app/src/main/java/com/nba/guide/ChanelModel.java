package com.nba.guide;


public class ChanelModel {

    int id;

    String comapny_name, channel_name;


    public ChanelModel() {

    }

    public ChanelModel(int id, String comapny_name, String channel_name){
        this.comapny_name = comapny_name;
        this.id = id;
        this.channel_name= channel_name;


    }

    public String getComapny_name() {
        return comapny_name;
    }

    public void setComapny_name(String comapny_name) {
        this.comapny_name = comapny_name;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}


