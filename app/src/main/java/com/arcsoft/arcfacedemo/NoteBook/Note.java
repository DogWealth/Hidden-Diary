package com.arcsoft.arcfacedemo.NoteBook;

import android.util.Log;

public class Note {
    private long id;//依据id区分每一条笔记,创建数据库时设置为自增
    private String content;
    private String time;//最后编辑的时间或者创建时间
    private String create_time;
    private int tag;//笔记的标签（类型：运动，学习。。。）
    private static final String TAG = "NOTE";

    public Note(){
    }
    public Note(String content, String time, int tag){
        this.content = content;
        this.tag = tag;
        this.time = time;
    }
    public Note(String content, String time, String create_time,int tag){
        Log.i(TAG,"create_time = "+create_time +", time = "+time+"tag= "+ tag + " content = "+ content);
        this.content = content;
        this.tag = tag;
        this.time = time;
        this.create_time = create_time;
    }

    public long getId(){return id;}
    public String getContent(){return content;}
    public String getTime(){return time;}
    public String getCreteTime(){return create_time;}
    public int getTag(){return tag;}
    public void setId(long id){this.id = id;}
    public void setTime(String time){this.time = time;}
    public void setCreateTime(String create_time){this.create_time = create_time;}
    public void  setContent(String content){this.content = content;}
    public  void setTag(int tag){this.tag = tag;}

    @Override
    public String toString(){return  content + "\n" + time.substring(5,16) + "" + id;}
}
