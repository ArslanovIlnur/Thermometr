package org.app.server;

import org.app.instruments.Sensor;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket clientSocket;
    private PrintWriter out;

    public ClientHandler(Server server, Socket clientSocket) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.out = new PrintWriter(clientSocket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendData(){
        Sensor sensor;

        try {
            sensor = new Sensor();
            out.println(sensor.getTemperature() + ":" + sensor.getHumidity());
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true){
            server.sendToAll();
        }
    }
}
