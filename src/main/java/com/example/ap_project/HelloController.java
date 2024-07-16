package com.example.ap_project;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class HelloController {
    @FXML
    private AnchorPane playingPage;

    Random random = new Random();
    private boolean chr_slide = false;

    boolean game_end = false;

    @FXML
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onPlayButtonClick() {
        System.out.println("play click");
        loadPlayingPage();
    }

    @FXML
    public void onPauseButtonClick() {
        System.out.println("play pause");
        loadPauseMenuPage();
    }

    @FXML
    public void onExitMenuButtonClick() {
        System.out.println("exit button");
        loadStartingPage();
    }

    @FXML
    public void ReturnButton(){
        return_back();
    }

    @FXML
    public void onExitButtonClick() {
        stage.close();
    }

    ArrayList<Rectangle> rectangles = new ArrayList<>();
    int index = 2;

    private int score = 0;

    private void add_rectange() {
        Rectangle pillar = new Rectangle();
        pillar.setX(rectangles.get(index).getX() + random.nextInt(200) + 150);
        pillar.setY(450);
        pillar.setHeight(260);
        pillar.setWidth(random.nextInt(150) + 50);

        rectangles.add(pillar);
        index++;
    }

    Rectangle stick = new Rectangle();
    public void initialise() {
        Rectangle pillar = new Rectangle();
        pillar.setX(124);
        pillar.setY(450);
        pillar.setHeight(260);
        pillar.setWidth(130);

        Rectangle pillar2 = new Rectangle();
        pillar2.setX(pillar.getX() + random.nextInt(200) + 150);
        pillar2.setY(450);
        pillar2.setHeight(260);
        pillar2.setWidth(130);

        Rectangle pillar3 = new Rectangle();
        pillar3.setX(pillar2.getX() + random.nextInt(200) + 150);
        pillar3.setY(450);
        pillar3.setHeight(260);
        pillar3.setWidth(130);

        rectangles.add(pillar);
        rectangles.add(pillar2);
        rectangles.add(pillar3);

        stick.setX(250);
        stick.setHeight(10);
        stick.setY(439);
        stick.setRotate(180);
        stick.setWidth(4);
    }

    private Rectangle new_stick(double d){
        Rectangle stick = new Rectangle();
        stick.setX(250);
        stick.setHeight(10);
        stick.setY(439);
        stick.setRotate(180);
        stick.setWidth(4);
        return stick;
    }

    private void switchToGameOver() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("end_page.fxml"));
            AnchorPane gameOverPage = fxmlLoader.load();
            Scene scene = new Scene(gameOverPage);

            stage.setScene(scene);
            stage.show();

            HelloController controller = fxmlLoader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToGameOverWithDelay() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Adjust the delay time in milliseconds as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Switch to the game over page
            Platform.runLater(this::switchToGameOver);
        });
        thread.start();
    }


    private void flip_stick(Rectangle stick) {
        double pivotX = stick.getX(); // Endpoint X-coordinate
        double pivotY = stick.getY(); // Endpoint Y-coordinate

        Rotate rotate = new Rotate(0, pivotX, pivotY); // Initial angle is 0
        stick.getTransforms().add(rotate);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90)));

        timeline.play();
        System.out.println("flipping done");
    }

    private void return_back() {
        System.out.println("return");
        if (playingPage != null) {
            Scene scene = new Scene(playingPage); // Reuse the stored playingPage
            stage.setScene(scene);
            stage.show();
        } else {
            loadPlayingPage(); // Load the playing page if playingPage is null
        }
    }

    private void loadPlayingPage() {
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("playing_page.fxml"));
                AnchorPane loadedplayingPage = fxmlLoader.load();

            playingPage = loadedplayingPage;

                initialise();
                score = 0;

                Image characterImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("character.png")));
                ImageView character = new ImageView(characterImg);
                character.setX(rectangles.get(index-2).getX() + rectangles.get(index-2).getWidth() - 50);
                character.setY(388);
                character.setFitWidth(41);
                character.setFitHeight(60);

            Text scoreText = new Text("Score: " + score); // Create a Text node for the score
            scoreText.setX(400); // Set X position
            scoreText.setY(60); // Set Y position
            scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 42)); // Set font and size

            playingPage.getChildren().add(scoreText);


//                resetStick(stick);
//                stick.setX(rectangles.get(index-2).getX() + rectangles.get(index-2).getWidth() - 4);
//                stick.setY(439);
//                stick.setHeight(10);
//                stick.setWidth(4);
//                stick.setRotate(stick.getRotate() + 180);

                playingPage.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.Q) {
                        if (stick.getHeight() < 425) {
                            stick.setHeight(stick.getHeight() + 5);
                            stick.setY(stick.getY() - 5);
                        } else {
                            stick.setHeight(10);
                            stick.setY(439);
                        }
                    }
                });

                playingPage.setOnKeyReleased(event -> {
                    if (event.getCode() == KeyCode.Q) {
                        flip_stick(stick);
                        System.out.println("flipping done");

                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(e -> {
                            if (stick.getHeight() + 250 > rectangles.get(index-1).getX() - 3 && stick.getHeight() + stick.getX() < rectangles.get(index-1).getX() + rectangles.get(index-1).getWidth() + 3) {
                                System.out.println("char slide");
                                character_slide(character, rectangles.get(index-1).getX());
                                chr_slide = true;

                                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                                pause2.setOnFinished(e2 -> {
                                    if (chr_slide) {
                                        score++;
                                        scoreText.setText("Score: " + score);
//                                        slideRectanglesLeft(pillar, 0);
                                        add_rectange();
                                        playingPage.getChildren().add(rectangles.get(index));

                                        double x_1 = rectangles.get(index-3).getX();
                                        double x_2 = rectangles.get(index-2).getX();
                                        double x_3 = rectangles.get(index-1).getX();
                                        double x_4 = rectangles.get(index).getX();
//                                        slideRectanglesLeft(rectangles.get(index-3), 0);
                                        slideRectanglesLeft(rectangles.get(index-2), x_1 - rectangles.get(index-2).getX());
                                        slideRectanglesLeft(rectangles.get(index-1), x_2 - rectangles.get(index-1).getX());
                                        slideRectanglesLeft(rectangles.get(index), x_3 - rectangles.get(index).getX());
                                        character_slide(character, x_1 + rectangles.get(index-2).getWidth() - 50);

//                                        temp.setHeight(pillar.getHeight());
//                                        temp.setWidth(pillar.getWidth());
//                                        temp.setY(pillar.getY());
//                                        temp.setX(pillar.getX());


//                                        pillar.setWidth(pillar2.getWidth());
//                                        pillar.setHeight(pillar2.getHeight());
//                                        pillar.setX(pillar2.getX());
//                                        pillar.setY(pillar2.getY());
//
//                                        pillar2.setWidth(pillar3.getWidth());
//                                        pillar2.setHeight(pillar3.getHeight());
//                                        pillar2.setX(pillar3.getX());
//                                        pillar2.setY(pillar3.getY());
//
//                                        pillar.setX(pillar3.getX() + random.nextInt(200) + 150);
//                                        pillar.setY(450);
//                                        pillar.setHeight(260);
//                                        pillar.setWidth(random.nextInt(150) + 50);

//                                        slideRectanglesLeft(pillar, x_3 - pillar.getX());

//                                    Rectangle_clone(pillar, pillar2);
//                                    Rectangle_clone(pillar2, pillar3);
//
//                                    pillar.setX(pillar3.getX() + random.nextInt(200) + 150);
//                                    pillar.setY(450);
//                                    pillar.setHeight(260);
//                                    pillar.setWidth(random.nextInt(150) + 50);

                                    playingPage.getChildren().remove(rectangles.get(index-3));
                                    System.out.println("pillar removed");
//                                    resetStick(stick, rectangles.get(index-2).getX() + rectangles.get(index-2).getWidth() - 4);
//                                    stick.setX(rectangles.get(index-3).getX() + rectangles.get(index-2).getWidth() - 4);
//                                    stick.setHeight(10);
//                                    stick.setY(0);
//                                    stick.setX(0);
//                                    stick.setRotate(180);
//                                    stick.setWidth(4);
//                                        timeline.play();
//                                        timeline.play();
////                                        timeline.play();
//                                        stick.setHeight(10);
//                                        stick.setRotate(90);
                                        playingPage.getChildren().remove(stick);
                                        System.out.println("stick removed");
                                        stick = new_stick(170);
                                        playingPage.getChildren().add(stick);
                                        System.out.println("stick added");
                                    }else{
                                        character.toFront();
                                        character_slide(character, rectangles.get(index).getX());
                                        character_drops(character);
                                        game_end = true;
                                        System.out.println("chr falls");
                                        switchToGameOverWithDelay();
                                    }
                                });
                                pause2.play();
                            } else {
                                character.toFront();
                                character_slide(character, rectangles.get(index).getX());
                                character_drops(character);
                                game_end = true;
                                System.out.println("chr falls");
                                switchToGameOverWithDelay();
                            }
                        });

                        pause.play();
                    }
                });

//                playingPage.getChildren().add(pillar);
                playingPage.getChildren().add(stick);
//                playingPage.getChildren().add(temp);
                playingPage.getChildren().add(character);
                character.toFront();
                for(Rectangle i: rectangles){
                    playingPage.getChildren().add(i);
                }
//                playingPage.getChildren().add(pillar2);
//                playingPage.getChildren().add(pillar3);


                Scene scene = new Scene(playingPage);
                stage.setScene(scene);
                stage.show();

                HelloController controller = fxmlLoader.getController();
                controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hello() {
        System.out.println("buffer");
    }

    private void character_drops(ImageView character) {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), character);
            rotateTransition.setByAngle(360); // Rotate the character by 360 degrees
            rotateTransition.setCycleCount(Animation.INDEFINITE); // Keep rotating indefinitely
            rotateTransition.play();

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), character);
            translateTransition.setByY(500); // Drop the character by 500 units (adjust this value as needed)
            translateTransition.play();
    }

    private void Rectangle_clone(Rectangle pillar, Rectangle pillar2) {
        pillar.setWidth(pillar2.getWidth());
        pillar.setHeight(pillar2.getHeight());
        pillar.setX(pillar2.getX());
        pillar.setY(pillar2.getY());
    }

    private void character_slide(ImageView character, double v) {
        TranslateTransition slideRight = new TranslateTransition(Duration.seconds(1), character);
        slideRight.setToX(v - character.getX());
        slideRight.play();
    }

    private void slideRectanglesLeft(Rectangle rectangle, double x) {
        TranslateTransition slideLeft = new TranslateTransition(Duration.seconds(1), rectangle);
        slideLeft.setToX(x);
        slideLeft.play();
    }

    private void loadPauseMenuPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pause_menu.fxml"));
            AnchorPane playingPage = fxmlLoader.load();
            Scene scene = new Scene(playingPage);

            stage.setScene(scene);
            stage.show();

            HelloController controller = fxmlLoader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStartingPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("starting_page.fxml"));
            AnchorPane playingPage = fxmlLoader.load();
            Scene scene = new Scene(playingPage);

            stage.setScene(scene);
            stage.show();

            HelloController controller = fxmlLoader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
