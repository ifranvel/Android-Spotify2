package com.ismael.spotify2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class creacion_musica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_musica);
        getSupportActionBar().hide(); //Se oculta el Navbar
        VideoView video=(VideoView) findViewById(R.id.videoView); //Recogemos el videoview que se va a utilizar
        String path = "android.resource://" + getPackageName() + "/" + R.raw.messijapones; //Indicamos la ruta donde se encuentra el video
        video.setVideoURI(Uri.parse(path)); //Al videoview recogido anteriormente le seteamos el video de messi
        video.start(); //Reproducimos el video
        //Video loop - Funcion para hacer que el video se este en bucle
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video.start(); //Si detecta que termina el video lo vuelve a reproducir
            }
        });

        Button crear = findViewById(R.id.button2); //Guardamos el boton de crear la musica
        crear.setOnClickListener(new View.OnClickListener() { //Al pulsar el boton
            @Override
            public void onClick(View view) {
                TextView nombre = findViewById(R.id.nombreEdit); //recogemos el nuevo nombre
                TextView url = findViewById(R.id.urlEdit); //recogemos la nueva direccion
                MainActivity.db.insertMusica(nombre.getText().toString(), url.getText().toString()); //insertamos la musica a la base de datos
                MainActivity.main.cargarLista(); //decimos al mainactivity que recargue la lista de musicas
                Toast.makeText(MainActivity.main,"Música añadida correctamente", Toast.LENGTH_SHORT).show(); //notificacion de que se ha añadido la musica
                finish(); //Terminamos con la ventana actual
            }
        });
    }
}