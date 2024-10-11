package org.app.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClientWindow extends JFrame {
    private Socket clientSocket;
    private Scanner in;

    public ClientWindow() {
        try {
            clientSocket = new Socket("localhost", 1234);
            in = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Font font = new Font("Times new Roman", Font.PLAIN, 20);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel temperature = new JLabel();
        JLabel humidity = new JLabel();
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(4, 2);
        panel.setLayout(layout);
        panel.setBackground(Color.WHITE);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String[] data;
                        data = in.nextLine().split(":");

                        temperature.setText(data[0] + "°C");
                        humidity.setText(data[1] + "%");

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = localDate.format(dateFormatter);

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = localTime.format(timeFormatter);

        panel.add(new JLabel("  Дата:")).setFont(font);
        panel.add(new JLabel(date)).setFont(font);
        panel.add(new JLabel("  Время")).setFont(font);
        panel.add(new JLabel(time)).setFont(font);
        panel.add(new JLabel("  Температура:")).setFont(font);
        panel.add(temperature).setFont(font);
        panel.add(new JLabel("  Влажность:")).setFont(font);
        panel.add(humidity).setFont(font);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
