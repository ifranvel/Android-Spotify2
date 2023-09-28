package com.ismael.spotify2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity main; //Guardamos el Activity actual
    public static DBHelper db; //Guardamos la database con la que se trabajará
    public static ArrayList<String> items; //array que contendran las musicas regocidas de la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this; //Guardamos el Activity actual para tener unicamente un intent y no varios al hacer cambios de ventana
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //Se oculta el Navbar
        db = new DBHelper(MainActivity.this); // Se crea la base de datos

        //Boton de para crear musicas
        Button crearmusica = (Button)findViewById(R.id.button2);
        //Si el boton de crear musica es pulsado hara lo siguiente
        crearmusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,creacion_musica.class);
                startActivity(intent); //Cambia a la clase creacion_musica
            }
        });
        cargarLista(); //Funcion para cargar musicas de la base de datos
    }

    //Funcion para recoger las musicas almacenadas en la base de datos y mostrarlas en una lista
    public void cargarLista(){
        items = db.selectMusica();
        ListView list = findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //Caracteristicas que tendran los items de la lista (Musicas)
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(db.selectMusicaName(items.get(position)));
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.gradientbackground2);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(20);
                return textView;
            }
        };
        //Si la musica es presionada hará lo siguiente
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                musica.idMusica = items.get(i); //Guardamos la clave primaria de la musica
                musica.contador = i; //Guardamos la posicion donde se encuentra la musica que ha sido pulsada
                Intent intent = new Intent(MainActivity.this,musica.class);
                startActivity(intent); //Cambio hacia la clase musica
            }
        });
        list.setAdapter(adapter); //Le seteamos a la lista el ArrayAdapter creado
    }
}