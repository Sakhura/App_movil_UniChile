package com.unichile.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unichile.app.models.Estudiante;
import com.unichile.app.models.Materia;
import com.unichile.app.models.Nota;
import com.unichile.app.models.Noticia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "unichile.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE estudiantes (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, apellido TEXT, email TEXT UNIQUE, password TEXT, " +
                "carrera TEXT, semestre INTEGER, rut TEXT)");

        db.execSQL("CREATE TABLE materias (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, profesor TEXT, sala TEXT, dia TEXT, " +
                "hora_inicio TEXT, hora_fin TEXT, color TEXT, creditos INTEGER)");

        db.execSQL("CREATE TABLE notas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "materia_id INTEGER, evaluacion TEXT, valor REAL, " +
                "porcentaje INTEGER, fecha TEXT)");

        db.execSQL("CREATE TABLE noticias (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT, descripcion TEXT, fecha TEXT, categoria TEXT)");

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        db.execSQL("DROP TABLE IF EXISTS notas");
        db.execSQL("DROP TABLE IF EXISTS materias");
        db.execSQL("DROP TABLE IF EXISTS estudiantes");
        db.execSQL("DROP TABLE IF EXISTS noticias");
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Estudiante demo
        ContentValues e = new ContentValues();
        e.put("nombre", "Luciel"); e.put("apellido", "Salazar Romero");
        e.put("email", "estudiante@unichile.cl"); e.put("password", "12345");
        e.put("carrera", "Ingeniería en Informática"); e.put("semestre", 5);
        e.put("rut", "20.123.456-7");
        db.insert("estudiantes", null, e);

        // Materias + schedule
        Object[][] mats = {
            {"Estructuras de Datos",   "Prof. Carlos Herrera", "Lab 301",  "Lunes",     "08:00","09:30","#1565C0",4},
            {"Bases de Datos",         "Prof. Ana Torres",     "Sala 205", "Martes",    "10:00","11:30","#2E7D32",4},
            {"Redes de Computadores",  "Prof. Luis Vega",      "Sala 102", "Miércoles", "14:00","15:30","#E65100",3},
            {"Ingeniería de Software", "Prof. María Silva",    "Sala 308", "Jueves",    "09:00","10:30","#6A1B9A",4},
            {"Cálculo Diferencial",    "Prof. Roberto Lagos",  "Sala 410", "Viernes",   "11:00","12:30","#C62828",4},
            // Second sessions same week
            {"Estructuras de Datos",   "Prof. Carlos Herrera", "Lab 301",  "Jueves",    "13:00","14:30","#1565C0",4},
            {"Bases de Datos",         "Prof. Ana Torres",     "Sala 205", "Viernes",   "08:00","09:30","#2E7D32",4},
            {"Redes de Computadores",  "Prof. Luis Vega",      "Sala 102", "Lunes",     "11:00","12:30","#E65100",3},
            {"Ingeniería de Software", "Prof. María Silva",    "Sala 308", "Martes",    "14:00","15:30","#6A1B9A",4},
            {"Cálculo Diferencial",    "Prof. Roberto Lagos",  "Sala 410", "Miércoles", "08:00","09:30","#C62828",4}
        };

        long[] ids = new long[5];
        for (int i = 0; i < mats.length; i++) {
            ContentValues m = new ContentValues();
            m.put("nombre",      (String)  mats[i][0]);
            m.put("profesor",    (String)  mats[i][1]);
            m.put("sala",        (String)  mats[i][2]);
            m.put("dia",         (String)  mats[i][3]);
            m.put("hora_inicio", (String)  mats[i][4]);
            m.put("hora_fin",    (String)  mats[i][5]);
            m.put("color",       (String)  mats[i][6]);
            m.put("creditos",    (Integer) mats[i][7]);
            long newId = db.insert("materias", null, m);
            if (i < 5) ids[i] = newId;
        }

        // Notas
        Object[][] ns = {
            {ids[0],"Control 1",5.5,20,"15/03/2026"}, {ids[0],"Control 2",6.0,20,"10/04/2026"},
            {ids[0],"Proyecto",5.8,30,"20/05/2026"},  {ids[0],"Examen Final",6.2,30,"25/06/2026"},
            {ids[1],"Tarea 1",6.5,15,"18/03/2026"},   {ids[1],"Control",5.0,25,"22/04/2026"},
            {ids[1],"Proyecto BD",6.8,35,"15/05/2026"},{ids[1],"Examen",5.5,25,"20/06/2026"},
            {ids[2],"Control 1",4.5,30,"20/03/2026"}, {ids[2],"Laboratorio",6.0,30,"05/05/2026"},
            {ids[2],"Examen Final",5.2,40,"30/06/2026"},
            {ids[3],"Caso 1",6.0,20,"01/04/2026"},    {ids[3],"Caso 2",5.5,20,"15/04/2026"},
            {ids[3],"Proyecto Final",6.5,40,"01/06/2026"},{ids[3],"Examen",6.0,20,"25/06/2026"},
            {ids[4],"Prueba 1",4.0,25,"25/03/2026"},  {ids[4],"Prueba 2",4.5,25,"02/05/2026"},
            {ids[4],"Examen Final",4.2,50,"28/06/2026"}
        };
        for (Object[] n : ns) {
            ContentValues v = new ContentValues();
            v.put("materia_id", (Long)   n[0]);
            v.put("evaluacion", (String) n[1]);
            v.put("valor",      (Double) n[2]);
            v.put("porcentaje", (Integer)n[3]);
            v.put("fecha",      (String) n[4]);
            db.insert("notas", null, v);
        }

        // Noticias
        String[][] news = {
            {"Proceso de Matrícula 2026",
             "Se informa a todos los estudiantes que el proceso de matrícula para el segundo semestre 2026 comienza el 15 de julio. Recuerden tener al día sus pagos de arancel para poder realizar el proceso sin inconvenientes.",
             "10/06/2026","Académico"},
            {"Feria de Empleabilidad UniChile",
             "La Universidad realizará la Feria de Empleabilidad el próximo 25 de junio en el Pabellón Central. Más de 50 empresas nacionales e internacionales estarán presentes buscando talentos universitarios.",
             "05/06/2026","Eventos"},
            {"Nuevo Laboratorio de IA Inaugurado",
             "Inauguramos nuestro nuevo Laboratorio de Inteligencia Artificial, equipado con las últimas tecnologías en GPU de alto rendimiento. Disponible para estudiantes de Ingeniería a partir del 1 de julio.",
             "01/06/2026","Infraestructura"},
            {"Becas de Intercambio Internacional 2026",
             "Abrimos el proceso de postulación para becas de intercambio en universidades de España, Alemania y Canadá. El plazo vence el 30 de junio. Más información en la Dirección de Relaciones Internacionales.",
             "28/05/2026","Becas"},
            {"Campeonato Deportivo InterFacultades",
             "Inscribe tu equipo al Campeonato Deportivo InterFacultades 2026! Disciplinas: Fútbol, Basketball, Volleyball y eSports. Las inscripciones cierran el 20 de junio en el área de Deportes.",
             "20/05/2026","Deportes"},
            {"Sistema de Biblioteca Actualizado",
             "El sistema de préstamo de libros ha sido actualizado. Ahora puedes renovar tus préstamos directamente desde la app o la web. El límite de préstamos aumenta a 5 libros simultáneos por estudiante.",
             "15/05/2026","Servicios"}
        };
        for (String[] nw : news) {
            ContentValues v = new ContentValues();
            v.put("titulo", nw[0]); v.put("descripcion", nw[1]);
            v.put("fecha", nw[2]); v.put("categoria", nw[3]);
            db.insert("noticias", null, v);
        }
    }

    // ================== QUERIES ==================

    public Estudiante login(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
            "SELECT * FROM estudiantes WHERE email=? AND password=?",
            new String[]{email, password});
        Estudiante est = null;
        if (c.moveToFirst()) {
            est = new Estudiante(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("nombre")),
                c.getString(c.getColumnIndexOrThrow("apellido")),
                c.getString(c.getColumnIndexOrThrow("email")),
                c.getString(c.getColumnIndexOrThrow("carrera")),
                c.getInt(c.getColumnIndexOrThrow("semestre")),
                c.getString(c.getColumnIndexOrThrow("rut")));
        }
        c.close();
        return est;
    }

    public List<Materia> getDistinctMaterias() {
        List<Materia> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
            "SELECT MIN(id) as id, nombre, profesor, sala, dia, hora_inicio, hora_fin, color, creditos " +
            "FROM materias GROUP BY nombre ORDER BY nombre", null);
        if (c.moveToFirst()) do {
            list.add(new Materia(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("nombre")),
                c.getString(c.getColumnIndexOrThrow("profesor")),
                c.getString(c.getColumnIndexOrThrow("sala")),
                c.getString(c.getColumnIndexOrThrow("dia")),
                c.getString(c.getColumnIndexOrThrow("hora_inicio")),
                c.getString(c.getColumnIndexOrThrow("hora_fin")),
                c.getString(c.getColumnIndexOrThrow("color")),
                c.getInt(c.getColumnIndexOrThrow("creditos"))));
        } while (c.moveToNext());
        c.close();
        return list;
    }

    public List<Materia> getHorarioByDia(String dia) {
        List<Materia> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
            "SELECT * FROM materias WHERE dia=? ORDER BY hora_inicio ASC",
            new String[]{dia});
        if (c.moveToFirst()) do {
            list.add(new Materia(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("nombre")),
                c.getString(c.getColumnIndexOrThrow("profesor")),
                c.getString(c.getColumnIndexOrThrow("sala")),
                c.getString(c.getColumnIndexOrThrow("dia")),
                c.getString(c.getColumnIndexOrThrow("hora_inicio")),
                c.getString(c.getColumnIndexOrThrow("hora_fin")),
                c.getString(c.getColumnIndexOrThrow("color")),
                c.getInt(c.getColumnIndexOrThrow("creditos"))));
        } while (c.moveToNext());
        c.close();
        return list;
    }

    public List<Nota> getNotasByMateriaId(int materiaId) {
        List<Nota> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
            "SELECT * FROM notas WHERE materia_id=?",
            new String[]{String.valueOf(materiaId)});
        if (c.moveToFirst()) do {
            list.add(new Nota(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getInt(c.getColumnIndexOrThrow("materia_id")),
                c.getString(c.getColumnIndexOrThrow("evaluacion")),
                c.getDouble(c.getColumnIndexOrThrow("valor")),
                c.getInt(c.getColumnIndexOrThrow("porcentaje")),
                c.getString(c.getColumnIndexOrThrow("fecha"))));
        } while (c.moveToNext());
        c.close();
        return list;
    }

    public double getPromedioMateria(int materiaId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
            "SELECT SUM(valor * porcentaje / 100.0) FROM notas WHERE materia_id=?",
            new String[]{String.valueOf(materiaId)});
        double prom = 0;
        if (c.moveToFirst()) prom = c.getDouble(0);
        c.close();
        return prom;
    }

    public double getPromedioGeneral() {
        List<Materia> mats = getDistinctMaterias();
        if (mats.isEmpty()) return 0;
        double total = 0;
        for (Materia m : mats) total += getPromedioMateria(m.getId());
        return total / mats.size();
    }

    public List<Noticia> getAllNoticias() {
        List<Noticia> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM noticias ORDER BY id DESC", null);
        if (c.moveToFirst()) do {
            list.add(new Noticia(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("titulo")),
                c.getString(c.getColumnIndexOrThrow("descripcion")),
                c.getString(c.getColumnIndexOrThrow("fecha")),
                c.getString(c.getColumnIndexOrThrow("categoria"))));
        } while (c.moveToNext());
        c.close();
        return list;
    }

    public String getTodayDayName() {
        String[] days = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
        return days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
    }
}
