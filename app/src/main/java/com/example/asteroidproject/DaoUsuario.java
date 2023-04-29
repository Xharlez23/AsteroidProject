package com.example.asteroidproject;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DaoUsuario {
    Context c;
    Usuario u;
    Asteroide a;
    ArrayList<Usuario> list;
    ArrayList<Asteroide> listAsteroide;
    static SQLiteDatabase sql;
    String bd ="User";
    String tabla = "create table if not exists users(id Integer primary key autoincrement,firt_name,last_name,email,identificador,password)";
    String tablaAsteroid = "create table if not exists asteroid(id Integer primary key autoincrement," +
            "neo_reference_id,name,nasa_jpl_url,hazardous,absolute_magnitude,estimated_diameter_min,estimated_diameter_max," +
            "close_approach_date,miss_distance,velocity)";
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
            boolean success = (sql.insert("users",null,cv) > 0);
            if (success) {
                procesarAsteroides();
            }
            return success;

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
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(BASE_URL + "?start_date=2023-04-26&end_date=2023-05-03&api_key=" + API_KEY)
                .method("GET", null)
                .addHeader("Cookie", "JSESSIONID=39DE8DFC8988DB1AFD391BD1A57783A1; __VCAP_ID__=329aa87c-f811-4b9b-49c2-02e0")
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void procesarAsteroides() {
        Response response = getAsteroids();
        if (response != null && response.isSuccessful()) {
            try {
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONArray asteroidArray = jsonObject.getJSONObject("near_earth_objects").getJSONArray("2023-04-30");
                for (int i = 0; i < asteroidArray.length(); i++) {
                    JSONObject asteroidObject = asteroidArray.getJSONObject(i);
                    ContentValues cv = new ContentValues();
                    cv.put("neo_reference_id", asteroidObject.getString("neo_reference_id"));
                    cv.put("name", asteroidObject.getString("name"));
                    cv.put("nasa_jpl_url", asteroidObject.getString("nasa_jpl_url"));
                    cv.put("absolute_magnitude", asteroidObject.getDouble("absolute_magnitude_h"));
                    cv.put("estimated_diameter_min", asteroidObject.getJSONObject("estimated_diameter").getDouble("kilometers_min"));
                    cv.put("estimated_diameter_max", asteroidObject.getJSONObject("estimated_diameter").getDouble("kilometers_max"));
                    cv.put("hazardous", asteroidObject.getBoolean("is_potentially_hazardous_asteroid") ? 1 : 0);
                    cv.put("close_approach_date", asteroidObject.getJSONArray("close_approach_data").getJSONObject(0).getString("close_approach_date_full"));
                    cv.put("miss_distance", asteroidObject.getJSONArray("close_approach_data").getJSONObject(0).getJSONObject("miss_distance").getDouble("kilometers"));
                    cv.put("velocity", asteroidObject.getJSONArray("close_approach_data").getJSONObject(0).getJSONObject("relative_velocity").getDouble("kilometers_per_second"));
                    sql.insert("asteroid", null, cv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public Asteroide getAsteroides(){
        listAsteroide=selectAsteroids();
        for (Asteroide e:listAsteroide){
                return e;
        }
        return null;
    }
}
