package com.iit.azhar.quicknotes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView lastUpdated;
    private EditText userNotes;
    private Notes note;

    private static final String TAG = "MainActivity";

    DateFormat df = new SimpleDateFormat("EEE MMM d, hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind a variable to the screen widgets
        lastUpdated = findViewById(R.id.dateTime);
        userNotes = findViewById(R.id.userNotes);

        userNotes.setMovementMethod(new ScrollingMovementMethod());
        userNotes.setTextIsSelectable(true);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("LastUpdated", lastUpdated.getText().toString());
        outState.putString("UserNotes", userNotes.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        lastUpdated.setText(savedInstanceState.getString("LastUpdated"));
        userNotes.setText(savedInstanceState.getString("UserNotes"));
    }

    @Override
    protected void onResume() {
        note = loadFile();  // Load the JSON containing the product data - if it exists
        if (note.getLastUpdated() != null) { // null means no file was loaded
            lastUpdated.setText(note.getLastUpdated());
            userNotes.setText(note.getNotesText());
        }
        else {
            lastUpdated.setText(df.format(Calendar.getInstance().getTime()));
        }
        super.onResume();
    }

    private Notes loadFile() {

        Log.d(TAG, "loadFile: Loading JSON File");
        note = new Notes();
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            JsonReader reader = new JsonReader(new InputStreamReader(is, getString(R.string.encoding)));

            reader.beginObject();
            while (reader.hasNext()) {
                String notes = reader.nextName();
                if (notes.equals("lastUpdated")) {
                    note.setLastUpdated(reader.nextString());
                } else if (notes.equals("notesText")) {
                    note.setNotesText(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }

    @Override
    protected void onStop() {
        note.setLastUpdated(df.format(Calendar.getInstance().getTime()));
        note.setNotesText(userNotes.getText().toString());
        saveNote();
        super.onStop();
    }

    private void saveNote() {

        Log.d(TAG, "saveNote: Saving JSON File");
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("lastUpdated").value(note.getLastUpdated());
            writer.name("notesText").value(note.getNotesText());
            writer.endObject();
            writer.close();

            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
