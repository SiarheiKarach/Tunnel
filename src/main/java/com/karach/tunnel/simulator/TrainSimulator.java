package com.karach.tunnel.simulator;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainSimulator {
    private static final Logger logger = LogManager.getLogger();
    private static final TrainSimulator instance = new TrainSimulator();
    private final Map<Integer, Tunnel> tunnels;
    private final List<Train> trains;

    private TrainSimulator() {
        tunnels = new HashMap<>();
        trains = new ArrayList<>();
    }

    public static TrainSimulator getInstance() {
        return instance;
    }

    public void addTunnel(Tunnel tunnel) {
        if (tunnel != null) {
            tunnels.put(tunnel.getId(), tunnel);
        } else {
            logger.warn("Attempted to add a null tunnel to TrainSimulator");
        }
    }

    public Tunnel getTunnelById(int tunnelId) {
        return tunnels.get(tunnelId);
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void addTrainToTunnel(Train train, Tunnel tunnel) {
        if (train != null && tunnel != null) {
            tunnel.enterTunnel(train);
        } else {
            logger.warn("Attempted to add a null train or tunnel to TrainSimulator");
        }
    }

    public void simulate(Train train) throws InterruptedException {
        if (train != null) {
            Tunnel tunnel = train.getTunnel();
            if (tunnel != null) {
                tunnel.enterTunnel(train);
                Thread.sleep(1000); // Placeholder for simulation logic
                tunnel.exitTunnel(train);
            } else {
                logger.warn("Train {} cannot find the associated tunnel.", train.getId());
            }
        }
    }
}