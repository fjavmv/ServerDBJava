package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConectionDb {
    //Datos de para conectar a la Db
    private final String DRIVER = "org.postgresql.Driver";
    private String URL = "jdbc:postgresql://";
    private final String HOST = "localhost";
    private final String PORT = "5432";
    private final String DB_NAME = "dvdrental";
    private final String USER = "postgres";
    private final String PASS = "A9BXZWC173";
    //Mensajes
    private final String CONEXION_EXITOSA = "La coneción a las DB ha sido exitosa....";
    //Consulta
    private final String QUERY_PELICULAS = "SELECT film_id, title, description, release_year FROM film ORDER  BY film_id ASC;";
    private final String QUERY_ID = "SELECT film_id, title, description, release_year FROM film WHERE film_id =";
    //Creamos un objeto de la clase Connection y la inicializamos con null
    Connection connection = null;
    Statement declaracion = null;
    // id
    private int id;
    //Almacenar elementos de la db en un ArrayList
    private ArrayList<Peliculas> peliculas = new ArrayList<Peliculas>();

    //Constructor de la clase que recibe como parametro el id
    public ConectionDb(int id ){
        this.id = id;
    }

    //Constructor vacio
    public  ConectionDb(){
    }

    //metodo que realiza la conexión de manera generica
    public void conectarDb(){
        try{
            //Al método forName le pasamos el DRIVER para conectar a las DB
            Class.forName(DRIVER);
        }catch (ClassNotFoundException ex){
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }
        //Conexión a la db Postgresql
        try{
            //Pasamos datos para la conexión url, host, port, dbnombre, usuario y contraseña para conectar a la DB
            URL = URL + HOST + ":" + PORT + "/" + DB_NAME;
            connection = DriverManager.getConnection(URL , USER , PASS);
            // Método para comprobación de conexión valida
            boolean valid = connection.isValid(5000);
            if(valid){
                System.out.println(valid? "TEST OK" : "TEST FAIL");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":"+ e.getMessage());
            System.exit(0);
        }
    }

    //Consulta de todas las peliculas
    public ArrayList<Peliculas> consultarPeliculas(){
        try {
            //Realizar la consulta a la db
            declaracion = connection.createStatement();
            ResultSet rs = declaracion.executeQuery(QUERY_PELICULAS);
            while(rs.next()){
                int id = rs.getInt("film_id");
                String titulo = rs.getString("title");
                String descripcion = rs.getString("description");
                int release_year = rs.getInt("release_year");
                //Llenamos la lista con los elementos del array list
                peliculas.add(new Peliculas(id,titulo,descripcion,release_year));
            }

            //Cerramos conexión a la db
            rs.close();
            declaracion.close();
            connection.close();

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }

        return peliculas;
    }

    //Consultar por identificador
    public ArrayList<Peliculas> consultarPeliculasId(){
        try {
            //Realizar la consulta a la db
            declaracion = connection.createStatement();
            ResultSet rs = declaracion.executeQuery(QUERY_ID + id + ";");
            while(rs.next()){
                int id = rs.getInt("film_id");
                String titulo = rs.getString("title");
                String descripcion = rs.getString("description");
                int release_year = rs.getInt("release_year");
                peliculas.add(new Peliculas(id,titulo,descripcion,release_year));
            }

            //Cerramos conexión a la db
            rs.close();
            declaracion.close();
            connection.close();

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }

        return peliculas;
    }

}
