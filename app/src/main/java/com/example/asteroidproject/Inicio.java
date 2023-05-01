package com.example.asteroidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    RecyclerView nombre;
    Button btnVolver;

    int id = 0;
    Usuario u;
    Asteroide a;
    DaoUsuario dao;
    DaoAsteroides ast;
    private String lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        nombre=(RecyclerView)findViewById(R.id.nombre);
        btnVolver=(Button) findViewById(R.id.btnVolver);

        Bundle b=getIntent().getExtras();
        id= b.getInt("id");
        dao=new DaoUsuario(this);
        u=dao.getUsuarioId(id);
        a=dao.getAsteroides(id);
        lista = dao.selectAsteroids().toString();
        // nombre.setText(u.getEmail()+" "+u.getFirt_name());
        DaoAsteroides task = new DaoAsteroides();
        task.execute();
       // nombre.setRecyclerListener(lista);
       // nombre.setText(lista);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnVolver){
            Intent i = new Intent(Inicio.this, MainActivity.class);
            startActivity(i);
            // finish();
        }
    }
}