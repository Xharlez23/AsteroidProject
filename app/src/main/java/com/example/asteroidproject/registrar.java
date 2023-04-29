package com.example.asteroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrar extends AppCompatActivity implements View.OnClickListener {
    EditText email, password,firt_name,last_name,identificador;
    Button btnVolver, btnRegistrar;
    DaoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        email=(EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.Password);
        firt_name=(EditText)findViewById(R.id.firtName);
        last_name=(EditText)findViewById(R.id.lastName);
        identificador=(EditText)findViewById(R.id.identificacion);
        btnVolver=(Button) findViewById(R.id.btnVolver);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);


        btnVolver.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao = new DaoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnVolver){
            Intent i = new Intent(registrar.this, MainActivity.class);
            startActivity(i);
            finish();

        } else if (id == R.id.btnRegistrar) {
            Usuario u = new Usuario();
            u.setEmail(email.getText().toString());
            u.setPassword(password.getText().toString());
            u.setFirt_name(firt_name.getText().toString());
            u.setLast_name(last_name.getText().toString());
            u.setIdentificador(Integer.parseInt(identificador.getText().toString()));
            //  dao.procesarAsteroides();
            if (!u.isNull()){
                Toast.makeText(this,"Error: Campos vacios, por favor llenar campos", Toast.LENGTH_LONG).show();
            } else if (dao.insertUsuario(u)) {
                Toast.makeText(this,"Registro exitoso", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(registrar.this, Inicio.class);
                startActivity(i2);
                finish();
            } else {
                Toast.makeText(this,"Usuario ya registrado", Toast.LENGTH_LONG).show();
            }
        }
    }
}