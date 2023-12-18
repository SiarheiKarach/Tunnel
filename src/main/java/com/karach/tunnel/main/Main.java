package com.karach.tunnel.main;

import com.karach.tunnel.model.Tunnel;
import com.karach.tunnel.model.Train;
import com.karach.tunnel.parser.TunnelParser;
import com.karach.tunnel.simulator.TrainSimulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Starting the Tunnel Simulator");

        TrainSimulator trainSimulator = TrainSimulator.getInstance();
        TunnelParser tunnelParser = new TunnelParser(trainSimulator);
        List<Train> trains = tunnelParser.parseTrains();

        for (int i = 1; i <= 5; i++) {
            Tunnel tunnel = new Tunnel(i, 3);
            trainSimulator.addTunnel(tunnel);
        }

        trains.forEach(train -> {
            Tunnel tunnel = train.getTunnel();
            if (tunnel != null) {
                trainSimulator.addTrainToTunnel(train, tunnel);
            } else {
                logger.warn("Train {} cannot find the associated tunnel.", train.getId());
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(trains.size());

        trains.forEach(train -> executorService.submit(() -> {
            trainSimulator.simulate(train);
        }));

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            logger.error("Error while waiting for simulation to finish", e);
        }

        logger.info("Tunnel Simulator finished");
    }
}