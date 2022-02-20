package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class LanguagePage extends AppCompatActivity {

    Spinner spinner;
    public static final String[] languages ={"Language", "English", "Thai", "Chinese", "Japanese", "Korean"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_page);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = adapterView.getItemAtPosition(i).toString();
                if(selectedLang.equals("Chinese")){
                    setLocal(LanguagePage.this,"zh");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("Thai")){
                    setLocal(LanguagePage.this, "th");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("English")){
                    setLocal(LanguagePage.this,"en");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("Japanese")){
                    setLocal(LanguagePage.this,"ja");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("Korean")){
                    setLocal(LanguagePage.this,"ko");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LanguagePage.this, "Please select a Language", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}
