package com.ismael.spotify2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class modificarmusica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarmusica);
        getSupportActionBar().hide(); //se oculta el navbar
        VideoView video=(VideoView) findViewById(R.id.videoView); //Recogemos el videoview
        String path = "android.resource://" + getPackageName() + "/" + R.raw.messijapones; //obtenemos la ruta de messi
        video.setVideoURI(Uri.parse(path)); //seteamos el video de messi
        video.start(); //reproducimos el video
        //Video loop - Funcion para hacer que el video se este en bucle
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video.start(); //Si se detecta que el video ha terminado, se vuelve a reproducir
            }
        });

        Button crear = findViewById(R.id.button2); //recogemos el boton de crear
        EditText nombre = findViewById(R.id.nombreEdit); //recogemos los campos donde se introducira el nuevo nombre
        EditText url = findViewById(R.id.urlEdit); //recogemos los campos donde se introducira la nueva direccion

        crear.setOnClickListener(new View.OnClickListener() { //al pulsar el boton de crear
            @Override
            public void onClick(View view) {
                //se modifica el nombre y la direccion la musica
                MainActivity.db.modificarMusica(musica.idMusica, nombre.getText().toString() , url.getText().toString());
                //se carga la nueva musica modificada
                musica.musica.cargarMusica();
                //se recarga la lista de musicas
                MainActivity.main.cargarLista();
                //notificacion de que la musica se ha modificado
                Toast.makeText(MainActivity.main,"Música modificada correctamente", Toast.LENGTH_SHORT).show();
                finish(); //se termina con la ventana actual
            }
        });
    }
}