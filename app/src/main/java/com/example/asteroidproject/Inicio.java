package com.example.asteroidproject;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    TextView nombre;
    Button btnVolver,btnAgregar,btnVer;

    int id = 0;
    Usuario u;
    Asteroide a;
    DaoUsuario dao;
    DaoAsteroides ast;
    String lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        nombre=(TextView)findViewById(R.id.nombre);
        btnVolver=(Button) findViewById(R.id.btnVolver);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnVer=(Button) findViewById(R.id.btnVer);

        Bundle b=getIntent().getExtras();
        id= b.getInt("id");
        dao=new DaoUsuario(this);
        u=dao.getUsuarioId(id);
        // nombre.setText(u.getEmail()+" "+u.getFirt_name());
        // nombre.setRecyclerListener(lista);
        // nombre.setText(lista);
        btnVolver.setOnClickListener(this);
        btnAgregar.setOnClickListener(this);
        btnVer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnVolver){
            Intent i = new Intent(Inicio.this, MainActivity.class);
            startActivity(i);
            // finish();
        }else if (id == R.id.btnAgregar) {
            DaoAsteroides task = new DaoAsteroides(this);
            task.execute();
            Log.i("datos", String.valueOf(task));
                Toast.makeText(this,"Procesando datos", Toast.LENGTH_LONG).show();
        }else if (id == R.id.btnVer) {
            AsteroidSQLiteHelper dbHelper = new AsteroidSQLiteHelper(this);
            Cursor cursor = dbHelper.getAsteroidRecords();
            Toast.makeText(this,"Procesando datos", Toast.LENGTH_LONG).show();
        }
    }
}