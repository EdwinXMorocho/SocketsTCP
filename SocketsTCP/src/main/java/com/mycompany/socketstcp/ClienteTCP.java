/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.socketstcp;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author EDWIN
 */

public class ClienteTCP {

    private Socket cliente;

    public static void main(String[] args) {
        ClienteTCP clienteTCP = new ClienteTCP();
        clienteTCP.iniciarSocketCliente(); 
    }
   
     public void setCliente(Socket cliente){
        this.cliente = cliente;
       
    }
     
    public void iniciarSocketCliente() {
        Scanner sc = new Scanner(System.in);
        try {
            // Conectar al servidor en localhost y puerto 4444
            this.cliente = new Socket("localhost", 4444);
            System.out.println("Conectado al servidor");
            
            DataOutputStream salida = new DataOutputStream(this.cliente.getOutputStream());
            DataInputStream entrada = new DataInputStream(this.cliente.getInputStream());
            
            int opcion;
            
            do{
            
                System.out.print("Ingrese una palabra a buscar: ");
                String palabra = sc.nextLine();

                //flujo de salida y enviar mensaje al servidor
                //DataOutputStream salida = new DataOutputStream(this.cliente.getOutputStream());
                salida.writeUTF(palabra); // Envía la palabra al servidor

                // flujo de entrada para recibir respuesta del servidor
                //DataInputStream entrada = new DataInputStream(this.cliente.getInputStream());
                String respuesta = entrada.readUTF();
                System.out.println("-- " + respuesta);

                System.out.print("Ingrese 1 para seguir buscando o 2 para salir: ");
                opcion = sc.nextInt();
                sc.nextLine(); 
                
                // Enviar la opción al servidor para manejar el flujo
                salida.writeInt(opcion);
                
            } while (opcion == 1);

            // Cerrar conexiones
            salida.close();
            entrada.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
