/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.socketstcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author EDWIN
 */
public class ServidorTCP {
    
    public static void main(String[] args) {
        
        HashMap<String, String> map = new HashMap<>();
        map.put("java", "Un lenguaje de programación");
        map.put("sql", "Structured Query Language");
        map.put("html", "Hypertext Markup Language");
        map.put("css", "Utilizado para dar estilo a los documentos HTML.");
        map.put("python", "Lenguaje de programación de alto nivel");
        map.put("mvc", "Model-View-Controller");

        
        try {
            // Crear un servidor que escucha en el puerto
            ServerSocket servidor = new ServerSocket(4444);
            System.out.println("Servidor esperando conexiones...");

            // Aceptar conexion del cliente
            Socket cliente = servidor.accept();
            System.out.println("Conexión aceptada desde " + cliente.getInetAddress());
            
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            
            int opcion;

            do {
                // Leer palabra desde el cliente
                String palabra = entrada.readUTF();
                String definicion;

                if (palabra.isEmpty()) {
                    definicion = "No se ha ingresado ninguna palabra.";
                } else {
                    definicion = map.getOrDefault(palabra.toLowerCase(), "Palabra no encontrada en el diccionario.");
                }

                // Enviar la definición al cliente
                salida.writeUTF(definicion);

                // Leer opción desde el cliente (1 para continuar, 2 para salir)
                opcion = entrada.readInt();

            } while (opcion == 1); 

            // Cerrar conexión
            cliente.close();
            servidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
