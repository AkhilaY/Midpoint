package cs1302.omega;
import javafx.application.Application;


public class MidpointDriver {

    public static void main(String[] args) {
        try {
            Application.launch(Midpoint.class,args);
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
            System.exit(1);

        }

    }

}
