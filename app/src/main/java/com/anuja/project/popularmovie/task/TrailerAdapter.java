package com.anuja.project.popularmovie.task;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anuja.project.popularmovie.R;
import com.anuja.project.popularmovie.pojoClass.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJA on 13-03-2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private final static String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private ArrayList<Trailer> TrailerItemList;
    Context mContext;
    private Cursor mCursor;
    private final onTailerCallbacks mCallbacks;


    public interface onTailerCallbacks {
        void watch(Trailer trailer, int position);
    }

    public TrailerAdapter(ArrayList<Trailer> trailers, onTailerCallbacks callbacks) {
        TrailerItemList = trailers;
        mCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Trailer trailer = TrailerItemList.get(position);
        final Context context = holder.mView.getContext();


        holder.mTrailer = trailer;

        String thumbnailUrl = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Log.i(LOG_TAG, "thumbnailUrl -> " + thumbnailUrl);
        //holder.mThumbnailView.setText(trailer.getName());

        Picasso.with(context)
                .load(thumbnailUrl)
                .config(Bitmap.Config.RGB_565)
                .into(holder.mThumbnailView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.watch(trailer, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return TrailerItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        ImageView mThumbnailView;
        public Trailer mTrailer;

        public ViewHolder(View view) {

            super(view);
            mThumbnailView = (ImageView) view.findViewById(R.id.trailer_thumbnail);
            mView = view;

        }


    }

    public void add(List<Trailer> trailers) {
        TrailerItemList.clear();
        //System.out.println("------------------------ add ------------------"+trailers.size());
        TrailerItemList.addAll(trailers);
        notifyDataSetChanged();
    }

    public ArrayList<Trailer> getTrailers() {
        return TrailerItemList;
    }


}

