package com.iit.azhar.multinotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Azhar on 10-02-2018.
 */

public class NotesViewHolder extends RecyclerView.ViewHolder  {

    public TextView note_title;
    public TextView last_updated;
    public TextView note_text;


    public NotesViewHolder(View view) {
        super(view);
        note_title = (TextView) view.findViewById(R.id.title);
        last_updated = (TextView) view.findViewById(R.id.date_modified);
        note_text = (TextView) view.findViewById(R.id.note_text);

    }

}
