package org.app.instruments;

public class Sensor {
    private int temperature;
    private int humidity;

    public Sensor() {
        this.temperature = -15 + (int) (Math.random() * 75);
        this.humidity = 40 + (int) (Math.random() * 40);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }
}
