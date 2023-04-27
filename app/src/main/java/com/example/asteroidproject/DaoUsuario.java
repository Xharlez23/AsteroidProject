package com.example.asteroidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DaoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> list;
    SQLiteDatabase sql;
    String bd ="User";
    String tabla = "create table if not exists users(id Integer primary key autoincrement,firt_name,last_name,email,identificador,password)";

    public DaoUsuario(Context c){
        this.c = c;
        sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        sql.execSQL(tabla);
        u=new Usuario();
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
}
