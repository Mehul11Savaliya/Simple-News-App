package com.ms.news;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class NEWS implements java.io.Serializable
{
    static int i=0;
    private   RequestQueue rq;
    private   JSONObject data;
    private Bitmap img;
    private  String imageUrl,date,mainurl,author,content,id,readMoreUrl,time,title,url;
    public NEWS(JSONObject data){

        this.data = data;
        mapjobjtoval(data);
    }

    public static int getI() {
        return i;
    }

    public RequestQueue getRq() {
        return rq;
    }

    public JSONObject getData() {
        return data;
    }

   public Bitmap getImg() {
        return img;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getMainurl() {
        return mainurl;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getReadMoreUrl() {
        return readMoreUrl;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    private  void mapjobjtoval(JSONObject data){
        try {
            this.title = data.getString("title");
            this.time = data.getString("time");
            this.url = data.getString("url");
            this.imageUrl = data.getString("imageUrl");
            this.readMoreUrl = data.getString("readMoreUrl");
            this.content = data.getString("content");
            this.author = data.getString("author");
            //    this.mainurl = data.getString("mainurl");
            this.date = data.getString("date");
            this.id = data.getString("id");
            getImageFromUrl(imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error",e.getMessage());
        }
    }

    private void getImageFromUrl(String url){
        //for networking on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
  Thread t = new Thread(new Runnable() {
      @Override
      public void run() {

          try{
              img = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
          } catch (IOException e) {
              e.printStackTrace();
              // Log.e("Error",e.getMessage());
          };
      }
  });
  t.start();


       // File root = Environment.getExternalStorageDirectory();
        //File dir = new File(root.getAbsolutePath(),"NEWS");
        //dir.mkdirs();
       // File f = new File(dir,Integer.toString(i++)+".png");
        //try {
          //  FileOutputStream fos = new FileOutputStream(f);
           // img.compress(Bitmap.CompressFormat.PNG,100,fos);
        //} catch (FileNotFoundException e) {
          //  e.printStackTrace();
       // }
    }

    @NonNull
    @Override
    public String toString() {
        return "NEWS{"+imageUrl+","+date+","+author+","+content+","+id+","+readMoreUrl+","+time+","+title+","+url+"}";
    }
}
