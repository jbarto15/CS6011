package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import SynthesizerBasic.VolumeAdjuster;
import javafx.geometry.Bounds;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VolumeAdjusterWidget extends AudioComponentWidget {
    Slider widgetSlider_;

    public VolumeAdjusterWidget(AudioComponent component, AnchorPane parent, String label) {
        super(component, parent, label);
        //create a default AudioComponent object
        AudioComponent defaultComponent = new VolumeAdjuster(1);

        //create a slider to adjust the volume
        Slider scaleSlider = new Slider(1, 10, 5);
        leftside.getChildren().add(scaleSlider);
        scaleSlider.setOnMouseDragged(e -> setScale(e, scaleSlider));

        //create a circle in the leftside of the horizontal box that will take input
        Circle inputToVolAdjuster = new Circle(10);
        inputToVolAdjuster.setFill(Color.AQUA);
        leftside.getChildren().add(inputToVolAdjuster);


        //NOTE FROM CLASS
        //handle drawing the line - handle 3 events
//        inputToVolAdjuster.setOnMousePressed(e->startConnection(e, inputToVolAdjuster));
//        inputToVolAdjuster.setOnMouseDragged(e->moveConnection(e, inputToVolAdjuster));
//        inputToVolAdjuster.setOnMouseReleased(e->endConnection(e, inputToVolAdjuster));


        //set the position of the widget
        this.setLayoutX(200);
        this.setLayoutY(200);
    }

    //method to set the scale for the volume adjuster
    private void setScale(MouseEvent e, Slider scaleSlider) {
        float scaleSliderValue = (float) scaleSlider.getValue();
        ((VolumeAdjuster)component_).setScale(scaleSliderValue);
    }
}
