package com.karach.tunnel.model;

import com.karach.tunnel.state.TunnelState;
import com.karach.tunnel.state.impl.EnteringState;
import com.karach.tunnel.simulator.TrainSimulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Tunnel {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final int maxCapacity;
    private final Queue<Train> trains;
    private final Semaphore semaphore;
    private TunnelState currentState;

    public Tunnel(int id, int maxCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.trains = new ArrayDeque<>();
        this.semaphore = new Semaphore(2, true);
        this.currentState = new EnteringState();

        TrainSimulator.getInstance().addTunnel(this);
    }

    public int getId() {
        return id;
    }

    public Queue<Train> getTrains() {
        return trains;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setCurrentState(TunnelState currentState) {
        this.currentState = currentState;
    }

    public void enterTunnel(Train train) {
        currentState.enter(this, train);
    }

    public void exitTunnel(Train train) {
        currentState.exit(this, train);
    }

    public static Tunnel getTunnelById(int tunnelId) {
        return TrainSimulator.getInstance().getTunnelById(tunnelId);
    }
}