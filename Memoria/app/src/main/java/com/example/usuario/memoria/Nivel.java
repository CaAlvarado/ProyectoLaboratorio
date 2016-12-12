package com.example.usuario.memoria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;

import java.text.SimpleDateFormat;

public class Nivel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button boton = (Button) findViewById(R.id.button);
        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        final String voz = sharedpref.getString("voz_list","f");

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MediaPlayer mp = null;
                if(voz.equals("f")){
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.aros_fem);
                }
                if(voz.equals("m")){
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.aros_masc);
                }
                if(mp != null){
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mp = null;
                        }
                    });
                    mp.start();
                }
            }
        });

        final String tiempo = sharedpref.getString("tiempo_list", "0");
        Long milisegundos = Long.parseLong(tiempo)/60/1000;
        final TextView texto =(TextView) findViewById(R.id.textView);
        if(tiempo.equals("0")){
            texto.setText("Tiempo restante: " + tiempo);
        }else{
            new CountDownTimer(milisegundos, 1000){
                public void onTick(long milisegundosRestantes){
                    texto.setText("Tiempo restante: " + new SimpleDateFormat("mm:ss:SS").format(new Date(milisegundosRestantes)));
                }
                public void onFinish(){
                    texto.setText("Se termino el tiempo!");
                }
            };
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nivel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           Intent ajustes = new Intent(Nivel.this, SettingsActivity.class);
            Nivel.this.startActivity(ajustes);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
