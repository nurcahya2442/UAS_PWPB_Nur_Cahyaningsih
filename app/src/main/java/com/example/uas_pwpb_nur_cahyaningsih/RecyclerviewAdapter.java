package com.example.uas_pwpb_nur_cahyaningsih;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {
    Context context;
    OnUserClickListener listener;
    List<DataNotes> listNoteInfo;

    public RecyclerviewAdapter(Context context, List<DataNotes> listDataInfo, OnUserClickListener listener){
        this.context = context;
        this.listNoteInfo = listDataInfo;
        this.listener = listener;
    }

    public interface OnUserClickListener {
        void onNoteClick(DataNotes currentNote, String action);

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return  userViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final DataNotes currentNote = listNoteInfo.get(position);
        holder.ctxtDate.setText(currentNote.getDate());
        holder.ctxtJudul.setText(currentNote.getJudul());
        holder.ctxtDeskripsi.setText(currentNote.getDeskripsi());


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClick(currentNote, "Edit");
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClick(currentNote, "Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNoteInfo.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView ctxtJudul, ctxtDeskripsi, ctxtDate;
        ImageView imgDelete, imgEdit;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ctxtDate = itemView.findViewById(R.id.ctxtDate);
            ctxtJudul = itemView.findViewById(R.id.ctxtJudul);
            ctxtDeskripsi = itemView.findViewById(R.id.ctxtDeskripsi);
            imgDelete = itemView.findViewById(R.id.imgdelete);
            imgEdit = itemView.findViewById(R.id.imgedit);
        }

    }
}

