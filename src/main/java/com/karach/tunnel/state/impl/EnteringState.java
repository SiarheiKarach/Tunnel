package com.karach.tunnel.state.impl;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import com.karach.tunnel.state.TunnelState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnteringState implements TunnelState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void enter(Tunnel tunnel, Train train) {
        try {
            tunnel.getSemaphore().acquire();

            if (tunnel.getTrains().size() < tunnel.getMaxCapacity()) {
                tunnel.getTrains().offer(train);
                logger.info("Train {} entered Tunnel {}", train.getId(), tunnel.getId());
            } else {
                logger.info("Train {} is waiting to enter Tunnel {}", train.getId(), tunnel.getId());
            }
        } catch (InterruptedException e) {
            logger.error("Error while entering the tunnel", e);
        } finally {
            tunnel.getSemaphore().release();
        }
    }

    @Override
    public void exit(Tunnel tunnel, Train train) {
        throw new IllegalStateException("Train is still entering the tunnel.");
    }
}