package com.example.asteroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    TextView nombre;
    Button btnVolver;

    int id = 0;
    Usuario u;
    DaoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        nombre=(TextView)findViewById(R.id.nombre);
        btnVolver=(Button) findViewById(R.id.btnVolver);

        Bundle b=getIntent().getExtras();
        id= b.getInt("id");
        dao=new DaoUsuario(this);
        u=dao.getUsuarioId(id);
        nombre.setText(u.getEmail()+" "+u.getFirt_name());
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnVolver){
            Intent i = new Intent(Inicio.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}