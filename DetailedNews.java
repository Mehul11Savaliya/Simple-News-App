package com.ms.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;

public class DetailedNews extends AppCompatActivity {
    TextView title, content, timedate, author, readmore;
    ImageView img;
    private NEWS n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        title = findViewById(R.id.DN_title);
        content = findViewById(R.id.DN_content);
        img = findViewById(R.id.DN_img);
        timedate = findViewById(R.id.DN_time_date);
        author = findViewById(R.id.DN_Author);
        readmore = findViewById(R.id.DN_readmore);

        int nightModeFlags =
                DetailedNews.this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                title.setTextColor(Color.rgb(0, 0, 255));
                author.setTextColor(Color.rgb(0, 0, 255));
                content.setTextColor(Color.rgb(0, 0, 255));
                break;

            case Configuration.UI_MODE_NIGHT_NO:

                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                break;
        }

        init();
    }

    private void init() {
        Intent i = getIntent();
        int indx = i.getIntExtra(NewsAdapter.NEWS_OBJ_INDX, -1);
        n = MainActivity.newss.get(indx);

        title.setText(n.getTitle());
        content.setText(n.getContent());

        //scaling image
        Bitmap zoomed = n.getImg();
        int width = zoomed.getWidth();
        int height = zoomed.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float) (width*3 / (double) width);
        float scaleHeight = (float) (height*4 / (double) height);
        matrix.postScale(scaleWidth, scaleHeight);
        img.setImageBitmap(Bitmap.createBitmap(zoomed,0,0,width,height,matrix,true));
        //scalling image over

        timedate.setText(" " + n.getTime() + "||" + " " + n.getDate());
        author.setText("--" + n.getAuthor());

        //setting link in textview
        readmore.setMovementMethod(LinkMovementMethod.getInstance());
        String link = "<a href=\'" + n.getReadMoreUrl() + "\'> Read More </a>";
        readmore.setText(Html.fromHtml(link));


        //for selectable text
        content.setTextIsSelectable(true);
        author.setTextIsSelectable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Share");
     //   menu.add("Generate Report");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Share":
                //sharing intent
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, n.getReadMoreUrl());
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case "Generate Report":
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private  void genratepdf(String fname){
        try{

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}