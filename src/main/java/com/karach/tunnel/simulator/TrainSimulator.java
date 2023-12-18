package com.karach.tunnel.simulator;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainSimulator {
    private static final Logger logger = LogManager.getLogger();
    private static final TrainSimulator instance = new TrainSimulator();
    private static final int SIMULATION_DURATION_MS = 200;
    private final Map<Integer, Tunnel> tunnels;
    private final ArrayDeque<Train> trains;

    private TrainSimulator() {
        tunnels = new HashMap<>();
        trains = new ArrayDeque<>();
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
        return new ArrayList<>(trains);
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

    public void simulate(Train train) {
        if (train != null) {
            Tunnel tunnel = train.getTunnel();
            if (tunnel != null) {
                tunnel.enterTunnel(train);
                try {
                    Thread.sleep(SIMULATION_DURATION_MS);
                } catch (InterruptedException e) {
                    logger.error("Unexpected interruption during simulation", e);
                    Thread.currentThread().interrupt();
                }
                tunnel.exitTunnel(train);
            } else {
                logger.warn("Train {} cannot find the associated tunnel.", train.getId());
            }
        }
    }
}