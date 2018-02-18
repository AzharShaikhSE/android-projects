package com.iit.azhar.multinotes;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Azhar on 10-02-2018.
 */

public class MyAsyncTask extends AsyncTask<String, Integer, String>  {

    private static final String TAG = "MyAsyncTask";
    private MainActivity mainActivity;
    public static boolean running = false;

    public MyAsyncTask(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: ");
        Log.d(TAG, "loadFile: Loading JSON File");
        String noteJson="" ;
        try {
            InputStream is = mainActivity.getApplicationContext().openFileInput(strings[0]);
            JsonReader reader = new JsonReader(new InputStreamReader(is, mainActivity.getString(R.string.encoding)));

            reader.beginObject();
            while (reader.hasNext()) {
                noteJson = reader.nextName();
                if (noteJson.equals("Notes")) {
                    noteJson = reader.nextString();
                }  else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
         //   Load Nothing
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteJson;

    }

    @Override
    protected void onPreExecute() {
        // This method is used infrequently
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    protected void onPostExecute(String string) {
        // This method is almost always used
        super.onPostExecute(string);
        Log.d(TAG, "onPostExecute: " + string);


        try {
            JSONArray jsonArr = new JSONArray(string);
            ArrayList<Notes> notesArrayList = new ArrayList<Notes>();
            for (int i = 0; i < jsonArr.length(); i++) {

                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Notes note = new Notes();

                note.setLastUpdated(jsonObj.getString("lastUpdated"));
                note.setNoteTitle(jsonObj.getString("noteTitle"));
                note.setNoteText(jsonObj.getString("noteText"));

                notesArrayList.add(note);

                mainActivity.getJsonDataFromAsync(notesArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        running = false;
        Log.d(TAG, "onPostExecute: AsyncTask terminating successfully");
    }



}
