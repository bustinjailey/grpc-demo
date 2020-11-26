package org.jbailey.grpcdemo.user;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class UserTagServer extends Thread {
    private final Server server;
    private final int port;

    public UserTagServer(int port) {
        this.port = port;
        server = ServerBuilder.forPort(port).addService(new UserTagService()).build();
    }

    @Override
    public void run() {
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started, listening on " + port);

        while (!this.isInterrupted()) {
            // spin
        }

        server.shutdown();
    }
}
