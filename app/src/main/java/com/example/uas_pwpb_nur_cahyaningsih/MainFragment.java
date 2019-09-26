package com.example.uas_pwpb_nur_cahyaningsih;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener, RecyclerviewAdapter.OnUserClickListener {

    RecyclerView recyclerView;
    EditText edtJudul, edtDeskripsi;
    Button btnSubmit;
    RecyclerView.LayoutManager layoutManager;
    Context context;

    List<DataNotes> listNoteInfo;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        edtJudul = view.findViewById(R.id.edtjudul);
        edtDeskripsi = view.findViewById(R.id.edtdeskripsi);
        btnSubmit = view.findViewById(R.id.btnsubmit);
        btnSubmit.setOnClickListener(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(context);
        listNoteInfo = db.selectNoteData();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(context,listNoteInfo, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnsubmit) {
            DatabaseHelper db = new DatabaseHelper(context);
            DataNotes currentNote = new DataNotes();
            String btnStatus = btnSubmit.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ' ' HH:mm:ss");

            if (btnStatus.equals("Submit")) {
                currentNote.setJudul(edtJudul.getText().toString());
                currentNote.setDeskripsi(edtDeskripsi.getText().toString());
                currentNote.setDate(sdf.format(new Date()));
                db.insert(currentNote);
            }
            if (btnStatus.equals("Update")) {
                currentNote.setJudul(edtJudul.getText().toString());
                currentNote.setDeskripsi(edtDeskripsi.getText().toString());
                db.update(currentNote);
            }
            setupRecyclerView();
            edtJudul.setText("");
            edtDeskripsi.setText("");
            btnSubmit.setText("Submit");
        }

    }

    @Override
    public void onNoteClick(DataNotes currentNote, String action) {

        if (action.equals("Edit")) {
            edtJudul.setText(currentNote.getJudul());
            edtDeskripsi.setText(currentNote.getDeskripsi());
            btnSubmit.setText("Update");
        }
        if (action.equals("Delete")) {
            DatabaseHelper db = new DatabaseHelper(context);
            db.delete(currentNote.getJudul());
            setupRecyclerView();
        }

    }
}
