package com.fast.ui.fast_ui.indexview.utils;

/**
 * Created by apple on 2017/8/9.
 * 这个城市是我认为一个city应该包含有的字段
 * 除了城市名字，会有城市的id，id是唯一的，名字并不是唯一的
 */

public class City {

    private String name;
    private String pinyin;
    private String id;
    private String longitude;
    private String latitude;


    public City(String name, String id, String longitude, String latitude) {
        this.name = name;
        this.pinyin = PinYinUtils.getCityQuanPin(name);
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
