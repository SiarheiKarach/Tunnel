package com.karach.tunnel.state.impl;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import com.karach.tunnel.state.TunnelState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnteringState implements TunnelState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean enter(Tunnel tunnel, Train train) {
        try {
            tunnel.getSemaphore().acquire();

            if (tunnel.getTrains().size() < tunnel.getMaxCapacity()) {
                tunnel.getTrains().offer(train);
                logger.info("Train {} entered Tunnel {}", train.getId(), tunnel.getId());
                return true;
            } else {
                logger.info("Train {} is waiting to enter Tunnel {}", train.getId(), tunnel.getId());
                return false;
            }
        } catch (InterruptedException e) {
            logger.error("Error while entering the tunnel", e);
            Thread.currentThread().interrupt();
            return false;
        } finally {
            tunnel.getSemaphore().release();
        }
    }

    @Override
    public boolean exit(Tunnel tunnel, Train train) {
        logger.info("Train is still entering the tunnel.");
        return false;
    }
}