package com.karach.tunnel.state;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;

public interface TunnelState {
    boolean enter(Tunnel tunnel, Train train);

    boolean exit(Tunnel tunnel, Train train);
}