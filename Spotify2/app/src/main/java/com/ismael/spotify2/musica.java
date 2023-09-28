package com.ismael.spotify2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class musica extends AppCompatActivity {
    public static musica musica;
    public static String idMusica;
    public static String audioUrl;
    public static String nombreAudio;
    MediaPlayer mediaPlayer;
    public static int contador;

    public void cargarMusica(){ //funcion para cargar una musica de internet donde la url proviene de la base de datos y setear su nombre
        mediaPlayer.stop();
        audioUrl = MainActivity.db.selectMusicaURL(idMusica);
        nombreAudio = MainActivity.db.selectMusicaName(idMusica);
        TextView text = findViewById(R.id.textView);
        text.setText(nombreAudio);
        // Inicializo el mediaplayer
        mediaPlayer = new MediaPlayer();

        // Tipo stream para poder reproducir musicas desde URL
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl); //seteamos la url de la musica
            mediaPlayer.prepare(); //cargamos la musica
            mediaPlayer.start(); //la reproducimos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);
        musica = this;
        mediaPlayer = new MediaPlayer();
        cargarMusica();
        mediaPlayer.setLooping(false);
        getSupportActionBar().hide(); //Se oculta el Navbar

        //RECOGEMOS LAS IMAGEVIEWS
        ImageView play = findViewById(R.id.play);
        ImageView loop = findViewById(R.id.loop);
        ImageView borrar = findViewById(R.id.papelera);
        ImageView atras = findViewById(R.id.atras);
        ImageView next = findViewById(R.id.next);
        ImageView back = findViewById(R.id.back);
        ImageView editar = findViewById(R.id.editar);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){ // SI LA MUSICA ESTA REPRODUCIENDOSE
                    mediaPlayer.pause(); //PAUSAR
                    play.setImageResource(R.drawable.play); //Poner simbolo parado
                }else{
                    mediaPlayer.start(); //Reproducir
                    play.setImageResource(R.drawable.pause);//Poner simbolo pausa
                }
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isLooping()){ //SI ESTA EN BUCLE
                    mediaPlayer.setLooping(false); //QUITAR BUCLE
                    loop.setImageResource(R.drawable.noloop);
                }else{
                    mediaPlayer.setLooping(true); //ACTIVAR BUCLE
                    loop.setImageResource(R.drawable.loop);
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.db.borrar(idMusica); //Borramos de la base de datos la musica
                MainActivity.main.cargarLista(); //recargamos la lista de musicas
                Toast.makeText(MainActivity.main,"Música eliminada correctamente", Toast.LENGTH_SHORT).show(); //notificamos que se ha eliminado
                onBackPressed(); //Volvemos a la anterior pestaña
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //Volvemos a la pestaña anterior
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //poder pasar de musica mientras contador de la musica sea menor al numero de canciones que hay
                if(contador<MainActivity.db.countMusica()-1){
                    contador++;
                    idMusica = MainActivity.items.get(contador);
                    cargarMusica();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //poder pasar de musica mientras contador de la musica sea mayor a 0
                if(contador>0){
                    contador--;
                    idMusica = MainActivity.items.get(contador);
                    cargarMusica();
                }
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(musica.this,modificarmusica.class);
                startActivity(intent); //abrimos la ventana donde se podra modificar la musica
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop(); //Paramos la musica
        mediaPlayer = null;
        this.finish(); // finalizamos esta ventana
    }
}