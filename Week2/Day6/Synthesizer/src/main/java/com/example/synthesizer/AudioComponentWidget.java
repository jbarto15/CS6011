package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import SynthesizerBasic.SineWave;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AudioComponentWidget extends Pane {
    //member variable that stores an audio component
    public AudioComponent component_;
    //member variable that stores the anchor pane
    public AnchorPane parent_;

    //constructor, takes an audio component, and a parent anchor pane
    public AudioComponentWidget(AudioComponent component, AnchorPane parent) {
        //assign member variables to the parameter
        component_ = component;
        parent_ = parent;

        //create a horizontal box in the widget and set the style
        HBox widgetLayout = new HBox();
        widgetLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5; -fx-background-color: yellow");

        //create a vertical box in the widget that has a close button, and a circle for the audio output, give it some style
        VBox rightSide = new VBox();
        Button closeButton = new Button("x");
        closeButton.setOnAction(e -> closeWidget(e));
        Circle output = new Circle(10);
        output.setFill(Color.AQUA);

        //add the close button, and circle to the vertical box
        rightSide.getChildren().add(closeButton);
        rightSide.getChildren().add(output);

        //set alignment, padding, and spacing to the right side of the widget
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        //create a slider, set the event to a mouse dragged
        Slider freqSlider = new Slider(200, 880, 400);
        freqSlider.setOnMouseDragged(e -> setFrequency(e, freqSlider));
        //add the slider and the right side components to the widget
        widgetLayout.getChildren().add(freqSlider);
        widgetLayout.getChildren().add(rightSide);

        //add the widget layout
        this.getChildren().add(widgetLayout);

        //set the position of the widget
        this.setLayoutX(100);
        this.setLayoutY(100);
    }


    //method that sets the frequency, responds to the event of a mouse
    private void setFrequency(MouseEvent e, Slider freqSlider) {
        //cast sine wave to the component so it knows that our component is of type sine wave, use the set frequency method
        //in our sin wave class to set the frequency to our parameter freqSlider
        ((SineWave)component_).setFrequency((freqSlider.getValue()));
    }


    //method that closes the widget when an action event occurs which is when the X is pressed on the widget
    private void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
        SynthesizerApplication.widgets.remove(this);
    }
}