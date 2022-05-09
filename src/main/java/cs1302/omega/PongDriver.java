package cs1302.omega;

import javafx.application.Application;

/**
 * Driver class for Pong.java application.
 */

public class PongDriver {

    public static void main(String[] args) {
        try {
            Application.launch(Pong.class,args);
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
            System.exit(1);

        }

    }

}
