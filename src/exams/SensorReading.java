package exams;

import fileworks.DataImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SensorReading {

    static List<Reading> readings = new ArrayList<>();
    static List<InvalidReading> invalidReadings = new ArrayList<>();
    static String folder = "/data/readings";

    static void processReadingFile(String fileName){
        String line;
        int lineNum = 0;
        String[] params;
        DataImport di = new DataImport(fileName);
        while ((line = di.readLine())!=null){
            params = line.split(",");
            Reading current = new Reading(params[0], Integer.parseInt(params[1]));
            readings.add(current);
            if (!checkValidity(params[1]))
                invalidReadings.add(new InvalidReading(current, fileName, lineNum));
            lineNum++;
        }
        di.finishImport();
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        ExecutorService exec = Executors.newFixedThreadPool(threadCount);
        exec.submit(() -> {
            for (int i = 0; i < 50; i++) {
                processReadingFile("data/readings/sensors_" + (i+1) + ".txt");
            }
        });

        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        System.out.println("Total readings: " + (long) readings.size());
        System.out.println("Invalid readings: " + (long) invalidReadings.size());
        for (InvalidReading invalid : invalidReadings) {
            System.out.println(invalid.toString());
        }
    }

    static boolean checkValidity(String param) {
        if (Integer.parseInt(param) >= -20000 && Integer.parseInt(param) <= 20000)
            return true;
        else
            return false;
    }
}
class Reading{

    String sensorID;
    int value;
    public Reading(String sensorID, int value) {
        this.sensorID = sensorID;
        this.value = value;
    }
    @Override
    public String toString() {
        return sensorID + ": " + value;
    }
}

class InvalidReading{
    Reading reading;
    String fileName;
    int lineNum;

    public InvalidReading(Reading reading, String fileName, int lineNum) {
        this.reading = reading;
        this.fileName = fileName;
        this.lineNum = lineNum;
    }

    @Override
    public String toString(){
        return reading.sensorID + ": " + fileName + ", line " + lineNum;
    }
}
