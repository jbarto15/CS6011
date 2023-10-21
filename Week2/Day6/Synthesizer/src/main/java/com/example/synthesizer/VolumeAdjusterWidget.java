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


    //DONT FORGET TO OVERRIDE THESE
    //Methods that will handle the connections between widgets and with the speaker
    private void endInputConnection(MouseEvent e, Circle input) {
        //get the speaker from the main window
        Circle speaker = SynthesizerApplication.speaker;
        //create the bounds for the speaker
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());

        //calculate the distance between the line and the speaker so that they can connect
        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0 * Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0)));
        //if the distance between the line and the speaker is less than 10 we want to add the widget to add the connected widget to a new array list of connected widgets
        if (distance < 25) {
            //add the wave to the connectedWidgets array list and not just the number of audio component widgets on the screen. We only want the connected widgets to play sound
            SynthesizerApplication.connectedWidgets.add(this);
        } else {
            //if the line and the speaker are not less than 10 pixels away from each other, remove the line
            parent_.getChildren().remove(line_);
            //set the line to null to make sure it is not pointing
            line_ = null;
        }
    }

    private void startConnection(MouseEvent e, Circle output) {
        //check to see if the line is not null, and if it is not, remove the line
        if (line_ != null) {
            parent_.getChildren().remove(line_);
        }
        //get the bounds of the parent and output
        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        //create a new line and set the stroke width, we made this a global variable in the class so that it could be used by all the connection methods
        line_ = new Line();
        line_.setStrokeWidth(5);

        //set the starting point of the line to the current widget subtracted by the parent minimum X and Y position
        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

        //set the end point of where the line should be drawn
        line_.setEndX((e.getSceneX()));
        line_.setEndY(e.getSceneY());

        //add the line to the parent which is our center anchor pain
        parent_.getChildren().add(line_);
    }
}
