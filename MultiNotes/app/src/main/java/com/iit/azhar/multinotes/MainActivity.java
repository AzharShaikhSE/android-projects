package com.iit.azhar.multinotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, Comparator {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_UPDATE = 2;
    private Notes note;
    private ArrayList<Notes> notesList = new ArrayList<Notes>();
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        notesAdapter = new NotesAdapter(notesList, this);

        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAsyncTask async = new MyAsyncTask(this);
        async.execute(getString(R.string.file_name));

    }

    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Notes n = notesList.get(pos);

        //Toast.makeText(view.getContext(), "SHORT " + n.toString(), Toast.LENGTH_SHORT).show();

        Intent edit_intent = new Intent(this,EditActivity.class);
        edit_intent.putExtra("Notes_Object", n);
        edit_intent.putExtra("position", pos);
        startActivityForResult(edit_intent, REQUEST_CODE_UPDATE);
    }

    @Override
    public boolean onLongClick(View view) {
        final int node_pos = recyclerView.getChildLayoutPosition(view);
        Notes n = notesList.get(node_pos);
        //Toast.makeText(view.getContext(), "LONG " + n.toString(), Toast.LENGTH_SHORT).show();

        // Single input value dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setIcon(R.drawable.ic_save_white_48dp);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                notesList.remove(node_pos);
                notesAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setMessage("Do you want to Delete Note?");
        builder.setTitle("Delete Note");

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_menu_item:
                Intent add_intent = new Intent(this,EditActivity.class);
              //  add_intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
                startActivityForResult(add_intent, REQUEST_CODE);
                return true;
            case R.id.info_menu_item:
                Intent about_intent = new Intent(this,AboutActivity.class);
                startActivity(about_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Notes note = (Notes) data.getSerializableExtra("Notes_Object");
               // userText.setText(text);
                notesList.add(0,note);
                notesAdapter.notifyDataSetChanged();

                Log.d(TAG, "onActivityResult: Return note : " + note);

        } else if(requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK){
                Notes note = (Notes) data.getSerializableExtra("Notes_Object");
                Log.d(TAG, "onActivityResult: Return note : " + note);
                int pos = data.getIntExtra("position", -1);
                notesList.set(pos, note);
                Collections.sort(notesList, new MainActivity());
                notesAdapter.notifyDataSetChanged();

        } else if (resultCode == -99){
            Toast.makeText(this, " Un-titled activity was not saved", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }

    @Override
    protected void onPause() {
       if(!notesList.isEmpty())
            saveNote(notesList);
        super.onPause();
    }

    private void saveNote(ArrayList<Notes> notesList) {

        Log.d(TAG, "saveNote: Saving JSON File");
        try {

            Gson gson = new Gson();
            String notesAsString = gson.toJson(notesList);

            Log.d(TAG, "saveNote: JSON STRING" + notesAsString);

            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("Notes").value(notesAsString);
            writer.endObject();
            writer.close();

            //Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void getJsonDataFromAsync(ArrayList<Notes> notesArrayList) {

        if(!notesArrayList.isEmpty()){
            notesList = notesArrayList;
            recyclerView = (RecyclerView) findViewById(R.id.recycler);

            notesAdapter = new NotesAdapter(notesList, this);

            recyclerView.setAdapter(notesAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable("note_list", notesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        notesList = (ArrayList<Notes>) savedInstanceState.getSerializable("note_list");
    }

    @Override
    public int compare(Object o1, Object o2) {
        Notes n1 = (Notes) o1;
        Notes n2 = (Notes) o2;
        int result = 0;
        DateFormat df = new SimpleDateFormat("EEE MMM d, hh:mm a");

        try {
            result = df.parse(n2.getLastUpdated()).compareTo(df.parse(n1.getLastUpdated()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (result == 0){
            return -1;
        }
        else{
            return result;
        }
    }
}
