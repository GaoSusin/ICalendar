package com.susin.icalendar.model;


import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class Record extends DataSupport {

    private int id;
    // 是否来大姨妈
    // 0：否，1：是
    private String menstruation = "";
    // 每天习惯
    private String habit = "";

    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(String menstruation) {
        this.menstruation = menstruation;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}