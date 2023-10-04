package com.example.synthesizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

//CODE FROM NABIL to REFERENCE
//        AnchorPane MainLayout = new AnchorPane();
//        MainLayout.setStyle("-fx padding: 10; -fx-background-color");
//        Label frequencyLabel = new Label("SineWave");
//        frequencyLabel.setStyle("-fx-font: 20; -fx-font-weight: bold");
//
//
//        VBox freqBox = new VBox();
//        freqBox.setStyle("-fx-background-color: grey");
//        freqBox.relocate(70, 120);
//
//        Slider freqSlider = new Slider(0, 300, 30);
//
//        freqBox.getChildren().add(frequencyLabel);
//        freqBox.getChildren().add(freqSlider);
//        freqSlider.setOnMouseDragged(g->handleFreq(g, freqSlider, frequencyLabel,));
//
//        VBox v1 = new VBox();
//        Label l1 = new Label("Dragging");
//        l1.setStyle("-fx-background-color: red");
//        v1.getChildren().add(l1);
//        MainLayout.getChildren().add(v1);
//        MainLayout.getChildren().add(freqBox);

        Scene scene = new Scene(fxmlLoader.load(), 320, 340);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    //CODE FROM NABIL to REFERENCE
//    private void handleFreq(MouseEvent g) {
//        //give me the value of the slider
//        //set the value of the label using the slider's value
//        int result = (int) freqSlider.getValue();
//        frequencyLabel.setText("SineWave " + result + " Hz");
//        v1.relocate();
//    }


    public static void main(String[] args) {
        launch();
    }
}