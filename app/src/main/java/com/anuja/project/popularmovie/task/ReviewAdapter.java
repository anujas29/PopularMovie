package com.anuja.project.popularmovie.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuja.project.popularmovie.R;
import com.anuja.project.popularmovie.pojoClass.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJA on 16-03-2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyReviewHolder>{


    private final onReviewCallbacks mReviewCallbacks;
    private final ArrayList<Review> ReviewItemList;

    public interface onReviewCallbacks {
        void read(Review review, int position);
    }

    public ReviewAdapter(ArrayList<Review> reviews, onReviewCallbacks callbacks)
    {
        this.ReviewItemList = reviews;
        this.mReviewCallbacks = callbacks;
    }


    @Override
    public void onBindViewHolder(final MyReviewHolder holder, int position) {

        final Review review = ReviewItemList.get(position);

        holder.mReview = review;
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviewCallbacks.read(review, holder.getAdapterPosition());
            }
        });


    }

    @Override
    public MyReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent, false);
        return new MyReviewHolder(view);

    }

    @Override
    public int getItemCount() {
        return ReviewItemList.size();
    }

    public class MyReviewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        TextView authorTextView;
        TextView contentTextView;
        public Review mReview;

        public MyReviewHolder(View view)
        {
            super(view);
            authorTextView = (TextView)view.findViewById(R.id.textView_author);
            contentTextView = (TextView)view.findViewById(R.id.textView_content);
            mView = view;
        }

    }

    public void add(List<Review> reviews) {
        ReviewItemList.clear();
        ReviewItemList.addAll(reviews);
        notifyDataSetChanged();
    }

    public ArrayList<Review> getReviews() {
        return ReviewItemList;
    }
}
