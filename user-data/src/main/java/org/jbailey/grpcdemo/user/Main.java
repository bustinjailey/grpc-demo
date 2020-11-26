package org.jbailey.grpcdemo.user;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Quick and dirty
        UserTagServer serverThread = new UserTagServer(7000);
        serverThread.start();
    }
}
