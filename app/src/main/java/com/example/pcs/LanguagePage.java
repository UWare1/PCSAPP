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
    View decorView;
    public static final String[] languages ={"Language", "English", "Thai", "Chinese", "Japanese", "Korean", "Arabic", "French", "German", "Portuguese", "Spanish"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_page);

        spinner = findViewById(R.id.spinner);
        decorView = getWindow().getDecorView();

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
                else if(selectedLang.equals("Arabic")){
                    setLocal(LanguagePage.this,"ar");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("French")){
                    setLocal(LanguagePage.this,"fr");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("German")){
                    setLocal(LanguagePage.this,"de");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("Portuguese")){
                    setLocal(LanguagePage.this,"pt");
                    Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(selectedLang.equals("Spanish")){
                    setLocal(LanguagePage.this,"es");
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
