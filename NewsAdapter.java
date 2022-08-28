package com.ms.news;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
public final static  String NEWS_OBJ_INDX=" com.ms.news.localdataset.news";
    private static ArrayList<NEWS> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView img;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.newsad_ttl);
            img = view.findViewById(R.id.newsad_ttl_img);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 changeActivity(textView,getAdapterPosition());
                }
            });

           img.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 changeActivity(img,getAdapterPosition());
               }
           });
        }

        public TextView getTextView() {
            return textView;
        }

        private void changeActivity(View v,int indx){
            Intent i = new Intent(v.getContext(),DetailedNews.class);
            i.putExtra(NewsAdapter.NEWS_OBJ_INDX,indx);
            v.getContext().startActivity(i);
        }
    }


    public NewsAdapter(ArrayList<NEWS> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_news_adapter, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
                   viewHolder.textView.setText(Integer.toString(position+1)+" -> "+localDataSet.get(position).getTitle());
                  viewHolder.img.setImageBitmap(localDataSet.get(position).getImg());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
