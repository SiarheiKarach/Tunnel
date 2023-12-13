package com.karach.tunnel.model;

import com.karach.tunnel.simulator.TrainSimulator;

public class Train {
    private int id;
    private int tunnelId;
    private int capacity;

    public Train(int id, int tunnelId, int capacity) {
        this.id = id;
        this.tunnelId = tunnelId;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getTunnelId() {
        return tunnelId;
    }

    public int getCapacity() {
        return capacity;
    }

    public Tunnel getTunnel() {
        return TrainSimulator.getInstance().getTunnelById(tunnelId);
    }
}