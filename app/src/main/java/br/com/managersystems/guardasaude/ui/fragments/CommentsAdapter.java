package br.com.managersystems.guardasaude.ui.fragments;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Comment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    List<Comment> data = Collections.emptyList();
    Context context;
    RecyclerView recyclerView;

    public CommentsAdapter(List<Comment> data, Context context, RecyclerView recyclerView) {
        this.data = data;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        CommentsViewHolder holder = new CommentsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.user.setText(data.get(position).getAuthorName());
        holder.date.setText(data.get(position).getDate());
        holder.comment.setText(data.get(position).getCommentText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<Comment> comments) {
        data.addAll(comments);
        notifyDataSetChanged();
    }

    public void onDataChanged(RecyclerView recyclerView) {
        ViewGroup.LayoutParams layoutParams= recyclerView.getLayoutParams();
        switch (getItemCount()) {
            case (0) : layoutParams.height = 0;
                break;
            case (1) : layoutParams.height = 125;
                break;
            case (2) : layoutParams.height = 250;
                break;
            case (3) : layoutParams.height = 375;
                break;
            default: layoutParams.height = 500;
        }
        recyclerView.setLayoutParams(layoutParams);
    }


    public void add(int position, Comment comment) {
        data.add(position, comment);
        notifyItemInserted(position);
    }

    public void removeAll() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position, Comment comment) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView date;
        TextView comment;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.gs_exam_comment_username);
            date = (TextView) itemView.findViewById(R.id.gs_exam_comment_time_posted);
            comment = (TextView) itemView.findViewById(R.id.gs_exam_comment_text);
        }
    }


    @Override
    public void onViewRecycled(CommentsViewHolder holder) {
        ViewGroup.LayoutParams layoutParams= recyclerView.getLayoutParams();
        switch (getItemCount()) {
            case (0) : layoutParams.height = 60;
                break;
            case (1) : layoutParams.height = 100;
                break;
            case (2) : layoutParams.height = 140;
                break;
            case (3) : layoutParams.height = 180;
                break;
            default: layoutParams.height = 220;
        }
        recyclerView.setLayoutParams(layoutParams);
    }
}
