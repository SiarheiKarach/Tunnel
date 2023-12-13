package com.karach.tunnel.parser;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import com.karach.tunnel.simulator.TrainSimulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TunnelParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String TUNNELS_CSV_FILE_PATH = "tunnels.csv";
    private final TrainSimulator trainSimulator;

    public TunnelParser(TrainSimulator trainSimulator) {
        this.trainSimulator = trainSimulator;
    }

    public List<Train> parseTrains() {
        List<Train> trains = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TUNNELS_CSV_FILE_PATH)) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    reader.readLine();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.trim().split(";");
                        int id = Integer.parseInt(data[0].trim());
                        int tunnelId = Integer.parseInt(data[1].trim());
                        int capacity = Integer.parseInt(data[2].trim());

                        Train train = new Train(id, tunnelId, capacity);
                        trains.add(train);
                        TrainSimulator.getInstance().addTrain(train);

                        Tunnel tunnel = TrainSimulator.getInstance().getTunnelById(tunnelId);
                        if (tunnel == null) {
                            logger.warn("Train {} cannot find the associated tunnel.", train.getId());
                        }
                    }
                }
            } else {
                logger.error("Could not find CSV file: " + TUNNELS_CSV_FILE_PATH);
            }
        } catch (IOException | NumberFormatException e) {
            logger.error("Error parsing trains", e);
        }

        return trains;
    }
}