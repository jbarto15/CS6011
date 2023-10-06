package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import SynthesizerBasic.SineWave;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;

public class SynthesizerApplication extends Application {
    //make center panel as a member variable so it is visible to the start method
    public AnchorPane centerPanel = new AnchorPane();

    //array list of audio component widgets that will store each widget we create
    public static ArrayList<AudioComponentWidget> widgets = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        //create a pane and set the background color
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: green");

        //TOP PANEL
        //create a top panel using a horizontal box and set the style
        HBox topPanel = new HBox();
        topPanel.setStyle("-fx-background-color: red; -fx-border-color: white");
        topPanel.setPadding(new Insets(30));
        Label nameOfProgram = new Label("Synthesizer");
        topPanel.getChildren().add(nameOfProgram);

        //CENTER PANEL
        //use the anchor pane member variable and make a circle that will represent the speaker
        centerPanel.setStyle("-fx-background-color: red; -fx-border-color: white");
        Circle speaker = new Circle(20);
        speaker.setFill(Color.BLACK);
        speaker.setCenterX(625);
        speaker.setCenterY(150);
        //get children nodes to place in the center pane
        centerPanel.getChildren().add(speaker);

        //RIGHT PANEL
        //create a right panel that holds the sine wave, volume adjuster, and mixer buttons
        VBox rightPanel = new VBox();
        rightPanel.setStyle("-fx-background-color: blue; -fx-border-color:white");
        //rightPanel.setStyle("-fx-border-color: white");
        rightPanel.setPadding(new Insets(60));
        //create buttons for the right pane
        Button sineWave = new Button("Sine Wave");
        sineWave.setPadding(new Insets(10));
        sineWave.setOnAction(e->createComponent(e));
        Button volumeAdjuster = new Button("Volume Adjuster");
        volumeAdjuster.setPadding(new Insets(10));
        Button mixer = new Button("Mixer");
        mixer.setPadding(new Insets(10));
        //add children nodes to the right panel
        rightPanel.getChildren().add(sineWave);
        rightPanel.getChildren().add(volumeAdjuster);
        rightPanel.getChildren().add(mixer);

        //BOTTOM PANEL
        //create bottom panel and set the style, create a play button
        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setStyle("-fx-fill: blue");
        bottomPanel.setStyle("-fx-border-color: white");
        bottomPanel.setPadding(new Insets(30));
        Button playButton = new Button("Play");
        playButton.setPadding(new Insets(10));
        playButton.setOnAction(e->playAudio(e));
        bottomPanel.getChildren().add(playButton);


        //MAIN LAYOUT
        //setting the layout in main with its components
        mainLayout.setCenter(centerPanel);
        mainLayout.setRight(rightPanel);
        mainLayout.setBottom(bottomPanel);
        mainLayout.setTop(topPanel);

        //SCENE
        //create a scene and add the main layout
        Scene scene = new Scene(mainLayout, 900, 500);
        stage.setTitle("Synthesizer");
        stage.setScene(scene);
        stage.show();
    }

    //component method that creates a sine wave, a widget and adds them to the center panel
    private void createComponent(ActionEvent e) {
        //create a new sine wave
        AudioComponent sinewave = new SineWave(200);
        //create a new widget
        AudioComponentWidget widget = new AudioComponentWidget(sinewave, centerPanel);
        //add the widget to the center panel
        centerPanel.getChildren().add(widget);
        //add the widget to the array list of widgets
        widgets.add(widget);
    }


    //method that will play the audio we want
    private void playAudio(ActionEvent e) {
        try {
            Clip c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            byte[] data = widgets.get(0).component_.getClip().getData();
            c.open(format16, data, 0, data.length);
            c.start();

            AudioListener listener = new AudioListener(c);
            c.addLineListener(listener);
        } catch (LineUnavailableException k) {
            System.out.println(k.getMessage());
        }
    }



    public static void main(String[] args) {
        //method that will lau
        launch();
    }
}