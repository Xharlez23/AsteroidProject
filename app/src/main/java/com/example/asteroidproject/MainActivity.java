package com.example.asteroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText email, password;
Button btnEntrar, btnRegistrar;
DaoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.Password);
        btnEntrar=(Button) findViewById(R.id.btnEntrar);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);


        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao = new DaoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEntrar){
            String u = email.getText().toString();
            String p = password.getText().toString();
            if (u.equals("") && p.equals("")){
                Toast.makeText(this,"Error: campos vacios",Toast.LENGTH_LONG).show();
            } else if (dao.login(u,p)==1) {
                Usuario us = dao.getUsuario(u,p);
                Toast.makeText(this,"Iniciando",Toast.LENGTH_LONG).show();

                Intent i2 = new Intent(MainActivity.this, Inicio.class);
                i2.putExtra("id",us.getId());
                startActivity(i2);
                finish();
            } else {
                Toast.makeText(this,"datos incorrectos",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.btnRegistrar) {
            Intent i = new Intent(MainActivity.this, registrar.class);
            startActivity(i);
        }
    }
}