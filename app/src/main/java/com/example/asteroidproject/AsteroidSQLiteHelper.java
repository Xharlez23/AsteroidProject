package com.example.asteroidproject;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AsteroidSQLiteHelper  extends SQLiteOpenHelper {
    // Información de la tabla "asteroid"
    public static final String TABLE_ASTEROID = "asteroid";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ASTEROID_ID = "asteroid_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NASA_JPL_URL = "nasa_jpl_url";
    public static final String COLUMN_ABSOLUTE_MAGNITUDE = "absolute_magnitude_h";
    public static final String COLUMN_HAZARDOUS = "is_potentially_hazardous_asteroid";

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "asteroid.db";

    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla "asteroid"
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_ASTEROID + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ASTEROID_ID + " TEXT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_NASA_JPL_URL + " TEXT NOT NULL, "
            + COLUMN_ABSOLUTE_MAGNITUDE + " REAL NOT NULL, "
            + COLUMN_HAZARDOUS + " INTEGER NOT NULL"
            + ");";

    public AsteroidSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "asteroid"
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla anterior si existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASTEROID);
        // Crear la nueva tabla
        onCreate(db);
    }
    public Cursor getAsteroidRecords() {
        // Obtener una instancia de la base de datos en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // Realizar una consulta a la tabla "asteroid"
        String[] projection = {COLUMN_ID, COLUMN_ASTEROID_ID, COLUMN_NAME, COLUMN_NASA_JPL_URL,
                COLUMN_ABSOLUTE_MAGNITUDE, COLUMN_HAZARDOUS};
        Cursor cursor = db.query( TABLE_ASTEROID, projection, null, null, null, null, null);
            Log.d("DATABASE", cursor.toString());
        // Devolver el cursor con los datos de la consulta
        return cursor;
    }

}
