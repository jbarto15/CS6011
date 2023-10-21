package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MixerWidget extends AudioComponentWidget {
    public MixerWidget(AudioComponent component, AnchorPane parent, String label) {
        super(component, parent, label);

        //a vertical box for the input circle to the mixer
        VBox leftSide = new VBox();

        //a circle to go inside the VBox that will take the inputs
        Circle inputToMixer = new Circle(10);
        inputToMixer.setFill(Color.AQUA);

        //add the circle to the leftside
        leftside.getChildren().add(inputToMixer);

        //set the position of the widget
        this.setLayoutX(400);
        this.setLayoutY(100);
    }
}
