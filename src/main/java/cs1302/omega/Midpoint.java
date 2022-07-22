package cs1302.omega;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.lang.Exception;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;


public class Midpoint extends Application {


    private Pane root;
    private BorderPane borderPane;
    private HBox hbox;
    private TextField search1;
    private TextField search2;
    private String location1;
    private String location2;
    private double latitude1;
    private double latitude2;
    private double longitude1;
    private double longitude2;
    private Button button;
    private Label label;
    private GraphicsContext context;
    private Canvas c;
    private static final String API_KEY = "AIzaSyC_IRQJ8j2PPyQUuCDx5f-bx0_NSfuhC5E";
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private Scene scene;
    private Stage stage;
    private String text;



    public Midpoint() {
        this.stage = null;
        this.scene = null;
    }


    public void start(Stage stage) {
        c = new Canvas(WIDTH, HEIGHT);
        this.stage = stage;
        hbox = new HBox(20);
        search1 = new TextField();
        search2 = new TextField();
        label = new Label();
        button = new Button("Go");
        hbox.getChildren().addAll(search1, search2, button, label);
        context = c.getGraphicsContext2D();
        button.setOnAction(searchButton);
        this.scene = new Scene(hbox);
        this.stage.setScene(scene);
        this.stage.setTitle("Distance");
        this.stage.show();

    }

    EventHandler<ActionEvent> searchButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                location1 = search1.getText();
                location2 = search2.getText();
                geoPoint(location1, location2) ;

            }
        };


    private static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    Gson gson = new Gson();



    public void geoPoint(String source, String destination) {

        try {

            String address = source.replaceAll(" ", "%20");

            var url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + address + "&key=" + API_KEY;
            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            var client = HttpClient.newBuilder().build();
            HttpResponse <String> response = client.send
                (request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();



            JsonParser jp = new JsonParser();
            JsonObject jo = (JsonObject) jp.parse(jsonString);

            JsonArray jsonObject1 = (JsonArray) jo.get("results");
            JsonObject jsonObject2 = (JsonObject)jsonObject1.get(0);
            JsonObject jsonObject3 = (JsonObject)jsonObject2.get("geometry");
            JsonObject location = (JsonObject)jsonObject3.get("location");

            latitude1 = location.get("lat").getAsDouble();
            longitude1 = location.get("lng").getAsDouble();


            String place = destination.replaceAll(" ", "%20");

            var url2 = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + place + "&key=" + API_KEY;
            var request2 = HttpRequest.newBuilder().GET().uri(URI.create(url2)).build();
            var client2 = HttpClient.newBuilder().build();
            HttpResponse<String> response2 = client2.send
                (request2, HttpResponse.BodyHandlers.ofString());
            String jsonString2 = response2.body();


            JsonParser jp2 = new JsonParser();
            JsonObject jo2 = (JsonObject) jp2.parse(jsonString2);

            JsonArray jsonObject_1 = (JsonArray) jo2.get("results");
            JsonObject jsonObject_2 = (JsonObject)jsonObject_1.get(0);
            JsonObject jsonObject_3 = (JsonObject)jsonObject_2.get("geometry");
            JsonObject location1 = (JsonObject)jsonObject_3.get("location");


            latitude2 = location1.get("lat").getAsDouble();
            longitude2 = location1.get("lng").getAsDouble();

            halfway(latitude1, longitude1, latitude2, longitude2);

        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        }


    }




    public void halfway(double latitude_1, double longitude_1,
    double latitude_2, double longitude_2) {

        try {

            double latAvg = (latitude_1 + latitude_2) / 2;
            double longAvg = (longitude_1 + longitude_2) / 2;

            System.out.println(latAvg);
            System.out.println(longAvg);

            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+
                latAvg+","+longAvg+"&key="+API_KEY;


            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            var client = HttpClient.newBuilder().build();
            HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();

            JsonParser jp = new JsonParser();
            JsonObject jo = (JsonObject) jp.parse(jsonString);

            JsonArray jsonObject1 = (JsonArray) jo.get("results");
            JsonObject jsonObject2 = (JsonObject)jsonObject1.get(0);
            String address = jsonObject2.get("formatted_address").getAsString();
            System.out.println(address);
            label.setText(address);
            context.strokeText(address, WIDTH / 2, HEIGHT / 3);


        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

}
