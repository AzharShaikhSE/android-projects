package com.iit.azhar.multinotes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Created by Azhar on 10-02-2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private static final String TAG = "NotesAdapter";
    private ArrayList<Notes> notesList;
    private MainActivity mainAct;

    public NotesAdapter(ArrayList<Notes> notesList, MainActivity ma) {
        this.notesList = notesList;
        mainAct = ma;
    }
    
    @Override
    public NotesViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Creating New NotesView");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);


        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        Notes note = notesList.get(position);
        holder.note_title.setText(note.getNoteTitle());
        holder.last_updated.setText(note.getLastUpdated());
        String text = note.getNoteText();
        if(text.length() > 80){
            text = text.substring(0,79);
            text = text + "...";
        }
        holder.note_text.setText(text);

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
