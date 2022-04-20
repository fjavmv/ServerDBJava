package com.company;
//Declaramos librerias necesarias
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Servidor {
    private ServerSocket sServidor; // Socket del servidor
    private Socket sCliente; //Socket para el cliente
    private Scanner entrada; //Flujo de entrada para el envío de datos
    private PrintStream salida; //Flujo de salida para recepción de datos

    private final int puerto; //Puerto por el cual escuchará el servidor
    private javax.swing.Timer t; //Declaramos un Timer

    //Constructor de la clase Servidor
    public Servidor(int puerto){
        this.puerto = puerto;
    }

    public void iniciar(){
        final String SERVIDOR_INICIADO = " - SERVIDOR INICIADO -";
        final String EN_ESPERA_DE_CLIENTE = " - ESPERANDO CLIENTE -";

        try{
            // Se crea el socket del servidor a traves de una instancia de la clase ServerSocket
            sServidor = new ServerSocket(puerto);
            System.out.println(SERVIDOR_INICIADO);
            System.out.println(EN_ESPERA_DE_CLIENTE);

            //El metodo accept(), espera hasta que un cliente realice una conexión
            //Una vez que se ha establecido una conexión por el cliente, este
            // método devolverá un objeto tipo Socket, a través del cual se establecerá
            //la comunicación con el cliente
            sCliente = sServidor.accept();

            //Obtengo una referencia a los flujos de datos de entrada y salida del socket cliente
            entrada  = new  Scanner(sCliente.getInputStream());
            salida = new PrintStream(sCliente.getOutputStream());

            //Sección modificable
            System.out.println("Cliente conectado: " + sCliente.getInetAddress() +":"+ sCliente.getPort());
            //llamada al método menu
            menuDeOpciones();
            //Cerramos la conexión
            finalizar();
        }catch (Exception e){
            finalizar();
            e.printStackTrace();
        }
    }

    //Metodo para cerra conexión
    public void finalizar(){
        final String MSJ_CONEXION = "CONEXION CERRADA....";
        try {
            entrada.close();;
            salida.close();
            sCliente.close();
            sServidor.close();
            System.out.println(MSJ_CONEXION);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Menu de opciones
    public void menuDeOpciones(){
        salida.println("EJEMPLO CLIENTE - SERVIDOR CONSULTANDO A UNA DB-POSTGRESQL");
        salida.println("**********************************************************");
        salida.println("Selecciona el tipo de consulta a realizar");
        salida.println("Mostrar las peliculas por ID selecciona 1");
        salida.println("Mostrar todas las peliculas selecciona  2");
        salida.println("Opcion 3 para salir\n");
        salida.println("**********************************************************");
        int entradaOp = entrada.nextInt();

        switch (entradaOp){
            case 1:
                salida.println("Ingresa un ID entre el 1 y 1000 para consultar las peliculas disponibles en la DB \n");
                int entradaId = entrada.nextInt();
                ConectionDb db = new ConectionDb(entradaId);
                db.conectarDb();
                ArrayList<Peliculas> peliculasId = db.consultarPeliculasId();
                salida.println(peliculasId.get(0).toString());
                menuDeOpciones();

            case 2:
                ConectionDb consultaTodas = new ConectionDb();
                consultaTodas.conectarDb();
                ArrayList<Peliculas> peliculas = consultaTodas.consultarPeliculas();
                for (Peliculas pelicula : peliculas) {
                    salida.println(pelicula.toString());
                    salida.println(" ");
                }
                menuDeOpciones();
            case 3:
                salida.println("Programa finalizado");
                finalizar();
                break;

            default:
                salida.println("Opcion invalida.....\n");
                salida.println(" ");
                menuDeOpciones();
        }
    }

}
