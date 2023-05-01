package com.example.asteroidproject;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DaoUsuario<Int> {
    Context c;
    Usuario u;
    Asteroide a;
    ArrayList<Usuario> list;
    ArrayList<Asteroide> listAsteroide;
    static SQLiteDatabase sql;
    String bd ="User";
    String tabla = "create table if not exists users(id Integer primary key autoincrement,firt_name,last_name,email,identificador,password)";
    String tablaAsteroid = "create table if not exists asteroid(id Integer primary key autoincrement," +
            "neo_reference_id,name,nasa_jpl_url,absolute_magnitude,estimated_diameter_min,estimated_diameter_max," +
            "miss_distance,velocity,hazardous,user_id)";
    private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed";
    private static final String API_KEY = "lINjJNUxNmBc3nsp2I8FNY9Czmf6fujIrGb75wPa";

    public DaoUsuario(Context c){
        this.c = c;
        sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        sql.execSQL(tabla);
        sql.execSQL(tablaAsteroid);
        u=new Usuario();
        a=new Asteroide();
    }

    public boolean insertUsuario(Usuario u){
        if(buscar(u.getEmail())==0){
            ContentValues cv=new ContentValues();
            cv.put("firt_name",u.getFirt_name());
            cv.put("last_name",u.getLast_name());
            cv.put("email",u.getEmail());
            cv.put("identificador",u.getIdentificador());
            cv.put("password",u.getPassword());
            return (sql.insert("users",null,cv)>0);
        }else{
            return false;
        }
    }
    public int buscar(String u){
        int x=0;
        list=selectUsuarios();
        for (Usuario us:list) {
            if (us.getEmail().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Usuario> selectUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from users", null);
        if(cr!=null && cr.moveToFirst()){
            do {
                Usuario u =new Usuario();
                u.setId(cr.getInt(0));
                u.setFirt_name(cr.getString(1));
                u.setLast_name(cr.getString(2));
                u.setEmail(cr.getString(3));
                u.setIdentificador(cr.getInt(4));
                u.setPassword(cr.getString(5));
                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }
    public int login(String u,String p){
        int a=0;
        Cursor cr = sql.rawQuery("select * from users", null);
        if(cr!=null && cr.moveToFirst()){
            do {
                if (cr.getString(3).equals(u)&& cr.getString(5).equals(u)){
                    a++;
                }
            }while (cr.moveToNext());
        }
        return a;
    }
    public Usuario getUsuario(String u, String p){
        list=selectUsuarios();
        for (Usuario e:list){
            if (e.getEmail().equals(u) && e.getPassword().equals(u)){
                return e;
            }
        }
        return null;
    }
    public Usuario getUsuarioId(int id){
        list=selectUsuarios();
        for (Usuario e:list){
            if (e.getId() == id){
                return e;
            }
        }
        return null;
    }
    // obtener datos de Asteroid
    public static Response getAsteroids() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.nasa.gov/neo/rest/v1/feed?start_date=2023-04-27&end_date=2023-05-04&api_key=lINjJNUxNmBc3nsp2I8FNY9Czmf6fujIrGb75wPa")
                .method("GET", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Error resques: "+response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

        public static String getResponseBody(String url) {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    public boolean insertAsteroides(Asteroide a){
        Log.i("ASasdas¿das´pdasod", String.valueOf(a));
            /*
            ContentValues cv=new ContentValues();
            cv.put("neo_reference_id",a.getNeo_reference_id());
            cv.put("name",a.getName());
            cv.put("nasa_jpl_url",a.getNasa_jpl_url());
            cv.put("absolute_magnitude",a.getAbsolute_magnitude());
            Log.i("asteroid-bd",cv.toString());
            return (sql.insert("asteroid",null,cv)>0);/*
             */
        return true;

    }
    public ArrayList<Asteroide> selectAsteroids() {
        ArrayList<Asteroide> lista = new ArrayList<Asteroide>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from asteroid", null);
        if(cr!=null && cr.moveToFirst()){
            do {
                Asteroide a = new Asteroide();
                a.setId(cr.getInt(0));
                a.setNeo_reference_id(String.valueOf(cr.getInt(1)));
                a.setName(cr.getString(2));
                a.setNasa_jpl_url(cr.getString(3));
                a.setAbsolute_magnitude(cr.getDouble(4));
                a.setEstimated_diameter_min(cr.getDouble(5));
                a.setEstimated_diameter_max(cr.getDouble(6));
                a.setHazardous(cr.getInt(7) > 0);
                a.setMiss_distance(cr.getDouble(9));
                a.setVelocity(cr.getDouble(10));
                lista.add(a);
            } while (cr.moveToNext());
        }
        return lista;
    }
    public Asteroide getAsteroides(int id){
        listAsteroide=selectAsteroids();
        for (Asteroide e:listAsteroide){
            if (e.getUser_id() == id){
                return e;
            }
        }
        return null;
    }
}
