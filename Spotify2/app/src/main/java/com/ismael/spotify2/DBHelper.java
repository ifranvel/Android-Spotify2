package com.ismael.spotify2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=2; //VERSION DE LA BASE DE DATOS
    private static final String DATABASE_NAME="spotify2database.db"; //NOMBRE DE LA BASE DE DATOS
    private SQLiteDatabase dbW; // la database que insertara/modificara
    private SQLiteDatabase dbR; // la database que leera

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbW = this.getWritableDatabase(); //Obtenemos la database que insertara/modificara
        dbR = this.getReadableDatabase(); //obtenemos la database que leera
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREACION TABLA DONDE GUARDO LOS DATOS DE LAS MUSICAS
        sqLiteDatabase.execSQL("CREATE TABLE " + "musica" +"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "url TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //esta funcion no se usa pero debe permanecer debido a que es un metodo que implementa la clase de la que se extiende
    }

    public void insertMusica(String nombre, String url){
        // Funcion para insertar una nueva musica
        dbW.execSQL(
                "INSERT INTO musica (nombre,url) VALUES('"+nombre+"','"+url+"');"
        );
    }

    public void borrar(String id){
        // Funcion para borrar una musica
        dbW.execSQL(
                "DELETE FROM musica WHERE id like " + id
        );
    }

    public void modificarMusica(String id, String nombre, String url){
        // Funcion para modificar una musica
        dbW.execSQL(
                "UPDATE musica SET nombre='" + nombre + "', url='" + url +"'  WHERE id like " + id
        );
    }


    public ArrayList<String> selectMusica(){
        // Funcion para recibir una lista de las musicas que hay en la base de datos
        Cursor listasCursor = dbR.rawQuery("SELECT id FROM " + "musica", null);
        ArrayList<String> musicas = new ArrayList<String>();
        while(listasCursor.moveToNext()){
            System.out.println(listasCursor.getString(0));
            musicas.add(listasCursor.getString(0));
        }
        return musicas;
    }

    public String selectMusicaName(String id){
        // Funcion para recibir el nombre de una musica en concreto
        Cursor nombreCursor = dbR.rawQuery("SELECT nombre FROM " + "musica WHERE id like " + id, null);
        if(nombreCursor.moveToNext()){
            return nombreCursor.getString(0);
        }else{
            return "NULL";
        }
    }

    public int countMusica(){
        // Funcion para recibir el numero de musicas que hay guardadas en la base de datos
        Cursor nombreCursor = dbR.rawQuery("SELECT count(id) FROM musica", null);
        if(nombreCursor.moveToNext()){
            System.out.println("COUNT:" + nombreCursor.getInt(0));
            return nombreCursor.getInt(0);
        }else{
            return 0;
        }
    }


    public String selectMusicaURL(String id){
        // Funcion para recibir la direccion de una musica en concreto
        Cursor nombreCursor = dbR.rawQuery("SELECT url FROM " + "musica WHERE id like " + id, null);
        if(nombreCursor.moveToNext()){
            return nombreCursor.getString(0);
        }else{
            return "NULL";
        }
    }
}
