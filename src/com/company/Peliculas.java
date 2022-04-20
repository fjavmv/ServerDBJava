package com.company;

public class Peliculas {
    private final int id;
    private final String titulo;
    private final String descripcion;
    private final int release_year;

    public Peliculas(int id, String titulo, String descripcion, int release_year) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.release_year = release_year;
    }

    @Override
    public String toString(){
        return  "\nIdentificador: " +"\t"+ id  + "\n" + "Titulo de la pelicula: " +"\t"+ titulo + "\n" + "Resumen: " +"\t"+ descripcion + "\nFilmada en: " +"\t"+ release_year + "\n" + " ";
    }

}
