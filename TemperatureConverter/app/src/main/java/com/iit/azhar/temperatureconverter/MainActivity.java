package com.iit.azhar.temperatureconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    /* private RadioButton conversionSelected;
    private EditText inputTemperature;
    private EditText outputTemperature;
    private Button convert;

    */
    String conversionSelected;
    double outputTemperature;
    private TextView recentOutputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void radioSelected(View v) {
        conversionSelected = ((RadioButton) v).getText().toString();
        Toast.makeText(this, " You Selected Conversion from: " + conversionSelected, Toast.LENGTH_SHORT).show();

    }

    public void convertClicked(View v) {
        Log.d(TAG, "convertClicked: started convert function");
        String inputValue = ((EditText) findViewById(R.id.InputValue)).getText().toString();
        if (!inputValue.isEmpty() ) {
            Double inputTemperature = Double.parseDouble(inputValue);
            Log.d(TAG, "convertClicked: input temperature"+inputTemperature);
            int selectedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
            conversionSelected = ((RadioButton) findViewById(selectedId)).getText().toString();
            Log.d(TAG, "convertClicked: conversion selected: "+conversionSelected);

            if (conversionSelected.equalsIgnoreCase("Fahrenheit to Celsius")) {
                outputTemperature = (inputTemperature - 32.0) * 5.0 / 9.0;
                displayOutput(outputTemperature);
                recentOutputs("Fahrenheit to Celsius", inputTemperature, outputTemperature);
            } else{
                outputTemperature = (inputTemperature * 9.0 / 5.0) + 32.0;
                displayOutput(outputTemperature);
                recentOutputs("Celsius to Fahrenheit", inputTemperature, outputTemperature);
            }
        } else {
            Toast.makeText(this, " Please insert the input temperature !", Toast.LENGTH_LONG).show();
        }
    }

    public void displayOutput(double outputTemperature){
        EditText editText = (EditText)findViewById(R.id.OutputValue);
        editText.setText(String.format("%.1f", outputTemperature), TextView.BufferType.EDITABLE);
    }

    public void recentOutputs(String type, double inputTemperature, double outputTemperature){
        recentOutputs = (TextView) findViewById(R.id.RecentResultText);
        recentOutputs.setMovementMethod(new ScrollingMovementMethod());
        recentOutputs.setText(type+" : "+inputTemperature+" => "+String.format("%.1f", outputTemperature)+"\n"+recentOutputs.getText());
    }

}
