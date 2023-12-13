package com.karach.tunnel.state;

import com.karach.tunnel.model.Train;
import com.karach.tunnel.model.Tunnel;

public interface TunnelState {
    void enter(Tunnel tunnel, Train train);

    void exit(Tunnel tunnel, Train train);
}