package com.karach.tunnel.state.impl;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;
import com.karach.tunnel.state.TunnelState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExitingState implements TunnelState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean enter(Tunnel tunnel, Train train) {
        throw new IllegalStateException("Train is still exiting the tunnel.");
    }

    @Override
    public boolean exit(Tunnel tunnel, Train train) {
        tunnel.getTrains().poll();
        logger.info("Train {} exited Tunnel {}", train.getId(), tunnel.getId());
        tunnel.setCurrentState(new EnteringState());
        return false;
    }
}