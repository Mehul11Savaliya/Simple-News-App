package com.ms.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String NEWS_GET = "https://inshorts.deta.dev/news?category=";
    public static RequestQueue RQ;
    static ArrayList<NEWS> newss;
    private View vi;
    private RecyclerView news_list;
    private NewsAdapter na;
    private JsonObjectRequest ar;
    private Thread hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vi = findViewById(R.id.mainlay);
        news_list = findViewById(R.id.news_list);
        news_list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        newss = new ArrayList<>();
        na = new NewsAdapter(newss);
        news_list.setAdapter(na);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RQ = Volley.newRequestQueue(MainActivity.this);
        setNews("all", RQ, na);

        Snackbar.make(vi, "Touch On Three Dot For More Option..", Snackbar.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("All");
        menu.add("India");
        menu.add("Business");
        menu.add("Sports");
        menu.add("Politics");
        menu.add("Technology");
        menu.add("Startup");
        menu.add("Entertainment");
        menu.add("Miscellaneous");
        menu.add("Hatke");
        menu.add("Science");
        menu.add("Automobile");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getTitle().toString()) {
            case "All":
                setTitle("All");
                setNews("all", RQ, na);
                break;
            case "India":
                setTitle("India");
                setNews("india", RQ, na);
                break;
            case "Business":
                setTitle("Business");
                setNews("business", RQ, na);
                break;
            case "Sports":
                setTitle("Sports");
                setNews("sports", RQ, na);
                break;
            case "Politics":
                setTitle("Politics");
                setNews("politics", RQ, na);
                break;
            case "Technology":
                setTitle("Technology");
                setNews("technology", RQ, na);
                break;
            case "Startup":
                setTitle("Startup");
                setNews("startup", RQ, na);
                break;
            case "Entertainment":
                setTitle("Entertainment");
                setNews("entertainment", RQ, na);
                break;
            case "Miscellaneous":
                setTitle("Miscellaneous");
                setNews("miscellaneous", RQ, na);
                break;
            case "Hatke":
                setTitle("Hatke");
                setNews("hatke", RQ, na);
                break;
            case "Science":
                setTitle("Science");
                setNews("science", RQ, na);
                break;
            case "Automobile":
                setTitle("Automobile");
                setNews("automobile", RQ, na);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNews(String newstype, RequestQueue rq, NewsAdapter na) {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading " + newstype + " News..");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();
        pd.setCancelable(false);
        newss.clear();
        na.notifyDataSetChanged();

        hand = new Thread(new Runnable() {
            @Override
            public void run() {
                pd.setMessage("Fetching "+newstype.toUpperCase(Locale.ROOT)+ " News..");
                ar = new JsonObjectRequest(Request.Method.GET, NEWS_GET + newstype, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            pd.setMessage("Generating News..");

                            JSONArray ja = response.getJSONArray("data");
                            int len = ja.length();
                            pd.setMax(len);
                            for (int i = 0; i < len; i++) {
                                newss.add(new NEWS(ja.getJSONObject(i)));
                                pd.setProgress(i);
                                na.notifyItemInserted(i);

                                Log.d("output", newss.get(i).toString());
                            }
                            pd.cancel();
                            Toast.makeText(MainActivity.this, Integer.toString(newss.size())+" news..", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //      Log.e("requesterror",error.getMessage());
                        Toast.makeText(MainActivity.this, "Error In Loading News.." + error.getCause(), Toast.LENGTH_SHORT).show();
                    }
                });
                RQ.add(ar);
            }
        });
        hand.start();

        // RequestManagerSingleTon.getInstance(MainActivity.this).addToRequestQue(ar);
    }
}