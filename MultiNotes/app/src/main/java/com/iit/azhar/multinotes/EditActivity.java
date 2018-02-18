package com.iit.azhar.multinotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteText;
    String prevNoteTitle;
    String preNoteText;

    private Notes note = new Notes();
    public int pos = -1;
    private int RESULT_NO_Title = -99;

    private static final String TAG = "EditActivity";

    DateFormat df = new SimpleDateFormat("EEE MMM d, hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Bind a variable to the screen widgets
        noteTitle = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteText);

        noteText.setMovementMethod(new ScrollingMovementMethod());
        noteText.setTextIsSelectable(true);

        Intent intent = getIntent();
        if(intent.hasExtra("position")){
            pos = intent.getIntExtra("position",-1);
        }
        if (intent.hasExtra("Notes_Object")) {
            note = (Notes) intent.getSerializableExtra("Notes_Object");
            noteTitle.setText(note.getNoteTitle());
            noteText.setText(note.getNoteText());
          //  textView.setText("B_Activity\nOpened from " + text);
        }

         prevNoteTitle = noteTitle.getText().toString();
         preNoteText = noteText.getText().toString();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("noteTitle", noteTitle.getText().toString());
        outState.putString("noteText", noteText.getText().toString());
        outState.putInt("pos", pos);
        outState.putSerializable("note", note);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        noteTitle.setText(savedInstanceState.getString("noteTitle"));
        noteText.setText(savedInstanceState.getString("noteText"));
        pos = savedInstanceState.getInt("pos");
        note = (Notes) savedInstanceState.getSerializable("note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                Intent data = new Intent();

                String title = noteTitle.getText().toString();
                if(title!=null && !title.trim().isEmpty()){
                    note.setLastUpdated(df.format(Calendar.getInstance().getTime()));
                    note.setNoteTitle(title);
                    note.setNoteText(noteText.getText().toString());
                    data.putExtra("Notes_Object", note);
                    data.putExtra("position",pos);
                    setResult(RESULT_OK, data);
                } else{
                    setResult(RESULT_NO_Title,data);
                }

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

        if(!prevNoteTitle.equals(noteTitle.getText().toString()) || !preNoteText.equals(noteText.getText().toString())){

            // Single input value dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setIcon(R.drawable.ic_save_white_48dp);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent data = new Intent();

                    String title = noteTitle.getText().toString();
                    if(title!=null && !title.trim().isEmpty()){
                        note.setLastUpdated(df.format(Calendar.getInstance().getTime()));
                        note.setNoteTitle(title);
                        note.setNoteText(noteText.getText().toString());
                        data.putExtra("Notes_Object", note);
                        data.putExtra("position",pos);
                        setResult(RESULT_OK, data);
                    } else{
                        setResult(RESULT_NO_Title,data);
                    }
                    EditActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditActivity.super.onBackPressed();
                }
            });

            builder.setMessage("Do you want to Save Note?");
            builder.setTitle("Save Note");

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            super.onBackPressed();
        }
    }


}
