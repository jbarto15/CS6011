package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import SynthesizerBasic.SineWave;
import SynthesizerBasic.VolumeAdjuster;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AudioComponentWidget extends Pane { //used to extend pane. Now it extends AudioComponentWidgetBase because we made a super class that will create all widgets
    //member variable that stores an audio component
    public AudioComponent component_;
    //member variable that stores the anchor pane
    public AnchorPane parent_;
    public String label_;

    //member variable for the sliders
    Slider widgetSlider_;

    //slider values
    double sliderValues_;

    //NOTE FROM CLASS
    public double mouseXPos, mouseYPos, widgetXPos, widgetYPos;
    Line line_;



    VBox leftside;
    //constructor, takes an audio component, and a parent anchor pane
    public AudioComponentWidget(AudioComponent component, AnchorPane parent, String label) { //, Slider widgetSlider, double sliderValues
        //assign member variables to the parameter
        component_ = component;
        parent_ = parent;
        label_ = label;


        //create a horizontal box to create the widget and set the style
        HBox widgetLayout = new HBox();
        widgetLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5; -fx-background-color: yellow");

        //create a vertical box in the widget that has a close button, and a circle for the audio output, give it some style
        VBox rightSide = new VBox();
        Button closeButton = new Button("x");
        closeButton.setOnAction(e -> closeWidget(e));
        Circle output = new Circle(10);
        output.setFill(Color.AQUA);


        //NOTE FROM CLASS
        //handle drawing the line - handle 3 events
        output.setOnMousePressed(e->startConnection(e, output));
        output.setOnMouseDragged(e->moveConnection(e, output));
        output.setOnMouseReleased(e->endConnection(e, output));

        //add the close button, and circle to the vertical box
        rightSide.getChildren().add(closeButton);
        rightSide.getChildren().add(output);

        //set alignment, padding, and spacing to the right side of the widget
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        //LeftSide
        leftside = new VBox();
        Label freqLabel = new Label(label);
        leftside.getChildren().add(freqLabel);
        leftside.setOnMousePressed(e->getPosInformation(e));
        leftside.setOnMouseDragged(e->moveWidget(e));

        //MOST LIKELY DONT NEED ANYMORE
        //create a slider, set the event to a mouse dragged
//        Slider freqSlider = new Slider(200, 880, 400);
//        leftside.getChildren().add(freqSlider);
//        freqSlider.setOnMouseDragged(e -> setFrequency(e, freqSlider)); //add freqLabel after freqSlider
        //add the slider and the right side components to the widget


        widgetLayout.getChildren().add(leftside);
        widgetLayout.getChildren().add(rightSide);


        //add the widget layout
        this.getChildren().add(widgetLayout);

        //set the position of the widget
        this.setLayoutX(100);
        this.setLayoutY(100);
    }


    //NOTE FROM CLASS
    private void endConnection(MouseEvent e, Circle output) {
        Circle speaker = SynthesizerApplication.speaker;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());

        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0 * Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0)));
        if (distance < 10) {
            //the wave to some array list
            SynthesizerApplication.widgets.add(this); //that will add the connected to the other opened //nabil changed this widgets name to ConnectedWidgets
            //better to create a new array list for connected widgets only

            //the wave to some array list
        } else {
            parent_.getChildren().remove(line_);
            line_ = null;
        }
    }

    private void moveConnection(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());

    }

    private void startConnection(MouseEvent e, Circle output) {
        if (line_ != null) {
            parent_.getChildren().remove(line_);
        }

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(5);

        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

        line_.setEndX((e.getSceneX()));
        line_.setEndY(e.getSceneY());

        parent_.getChildren().add(line_);


    }


    //method that sets the frequency, responds to the event of a mouse
    protected void setFrequency(MouseEvent e, Slider freqSlider) {
        //cast sine wave to the component so it knows that our component is of type sine wave, use the set frequency method
        //in our sin wave class to set the frequency to our parameter freqSlider
        ((SineWave)component_).setFrequency((freqSlider.getValue()));

        //need to add left side stuff above to this method
    }


    //method that closes the widget when an action event occurs which is when the X is pressed on the widget
    private void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
        SynthesizerApplication.widgets.remove(this);

        //NOTE FROM CLASS
        SynthesizerApplication.widgets.remove(this);
        SynthesizerApplication.connectedWidgets.remove(this);
        parent_.getChildren().remove(line_);
    }

    //NOTE FROM CLASS
    //need a method to move the widget
    protected void moveWidget(MouseEvent e) {
        double deltaX = e.getSceneX() - mouseXPos;
        double deltaY = e.getSceneY() - mouseYPos;

        this.relocate(deltaX + widgetXPos, deltaY + widgetYPos);
    }
    //need a method to get the position information of the widget
    protected void getPosInformation(MouseEvent e) {
        mouseXPos = e.getSceneX();
        mouseYPos = e.getSceneY();
        widgetXPos = this.getLayoutX();
        widgetYPos = this.getLayoutY();
    }
}