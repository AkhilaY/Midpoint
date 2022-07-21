package cs1302.omega;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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



public class Midpoint extends Application {

    private VBox vbox;
    private BorderPane borderPane;
    private HBox hbox;
    private TextField search1;
    private TextField search2;
    private String location1;
    private String location2;
    private Button button;
    private static final String API_KEY = "AIzaSyC_IRQJ8j2PPyQUuCDx5f-bx0_NSfuhC5E";
    private Scene scene;
    private Stage stage;
    private String text;



    public Midpoint() {
        this.stage = null;
        this.scene = null;
    }


    public void start(Stage stage) {
        this.stage = stage;
        hbox = new HBox(20);
        search1 = new TextField();
        search2 = new TextField();
        button = new Button("Go");
        hbox.getChildren().addAll(search1, search2, button);
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

                processSearch(location1, location2);

            }
        };

    private static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();


    Gson gson = new Gson();



    public void processSearch(String source, String destination) {

        try {

            var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + API_KEY;
            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            var client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();

            //distance matrix
            Distance d = gson.fromJson(jsonString, Distance.class);
            JsonParser jp = new JsonParser();
            JsonObject jo = (JsonObject) jp.parse(jsonString);
            JsonArray ja = (JsonArray) jo.get("rows");

            jo = (JsonObject) ja.get(0);
            ja = (JsonArray) jo.get("elements");
            jo = (JsonObject) ja.get(0);

            JsonObject je =  (JsonObject)jo.get("distance");
            JsonObject jf = (JsonObject) jo.get("duration");
            var distance = je.get("text").getAsNumber();

            System.out.println(distance);


        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        }




    }






}
