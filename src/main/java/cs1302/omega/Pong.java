package cs1302.omega;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Circle;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;
import javafx.scene.text.TextAlignment;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
import javafx.util.Duration;

/**
 * Program for arcade game, Pong.
 */

public class Pong extends Application {


    Pane root;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    Rectangle player;
    Rectangle bot;
    Circle ball1;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 20;
    private static final double BALL = 15;
    private int ballXSpeed = 1;
    private int ballYSpeed = 1;
    private double playerYPos = HEIGHT / 2;
    private double botYPos = HEIGHT / 2;
    private int playerXPos = 0;
    private double botXPos = WIDTH - PLAYER_WIDTH;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    Line line;
    private int playerScore = 0;
    private int botScore = 0;
    private boolean begin;

    /**
     * Start method for application.
     * @param stage is passed as a reference.
     */


    public void start(Stage stage) {

        Canvas c = new Canvas(WIDTH, HEIGHT);
        line = new Line (WIDTH / 2,0,WIDTH / 2,HEIGHT);
        GraphicsContext context = c.getGraphicsContext2D();
        Timeline time = new Timeline(new KeyFrame(Duration.millis(10),
            event -> view(context)));
        time.setCycleCount(Timeline.INDEFINITE);
        c.setOnMouseMoved(event -> playerYPos  = event.getY());
        c.setOnMouseClicked(event ->  begin = true);
        stage.setTitle("PONG");
        stage.setScene(new Scene(new StackPane(c)));
        stage.show();
        time.play();
    }

    /**
     * This method sets up the interface and ensures
     * a user-friendly experience.
     * @param graphics takes in a graphics reference for the game disply.
     */

    private void view(GraphicsContext graphics) {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setFill(Color.BLACK);
        graphics.setFont(Font.font(25));
        if (begin) {
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            if (ballXPos < WIDTH - WIDTH / 4) {
                botYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                botYPos =  ballYPos > botYPos
                    + PLAYER_HEIGHT / 2 ? botYPos += 1 : botYPos - 1;
            }
            graphics.fillOval(ballXPos, ballYPos, BALL, BALL);
        } else {
            graphics.setStroke(Color.BLACK);
            graphics.setTextAlign(TextAlignment.CENTER);
            graphics.strokeText("Click to launch ball.", WIDTH / 2, HEIGHT / 3);
            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;
            ballXSpeed = new Random().nextInt(2) == 0 ? 2 : -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 2 : -1;
        }
        if (ballYPos > HEIGHT || ballYPos < 0) {
            ballYSpeed *= -1;
        }
        if (ballXPos < playerXPos - PLAYER_WIDTH) {
            botScore++;
            if (botScore == 10 && botScore > playerScore) {
                graphics.strokeText("Computer wins.", WIDTH / 2, HEIGHT / 3);
                playerScore = 0;
                botScore = 0;
            }
            begin = false;
        }
        if (((ballXPos + BALL > botXPos) &&
            ballYPos >= botYPos &&
            ballYPos <= botYPos + PLAYER_HEIGHT) ||
            ((ballXPos < playerXPos + PLAYER_WIDTH) &&
            ballYPos >= playerYPos  &&
            ballYPos <= playerYPos + PLAYER_HEIGHT)) {
            ballYSpeed *= -1;
            ballXSpeed *= -1;
        }
        if (ballXPos > botXPos + PLAYER_WIDTH) {
            playerScore++;
            if (playerScore == 10 && playerScore > botScore) {
                graphics.strokeText("You win.", WIDTH / 2, HEIGHT / 3);
                playerScore = 0;
                botScore = 0;
            }
            begin = false;
        }
        graphics.fillRect(botXPos, botYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        graphics.fillRect(playerXPos, playerYPos , PLAYER_WIDTH, PLAYER_HEIGHT);
        graphics.fillText(playerScore + "\t\t\t\t\t\t\t\t" +
            botScore, WIDTH / 2, 100);

    }




}
