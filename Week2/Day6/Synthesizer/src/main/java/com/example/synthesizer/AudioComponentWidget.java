package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import SynthesizerBasic.Mixer;
import SynthesizerBasic.SineWave;
import SynthesizerBasic.VolumeAdjuster;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.util.ArrayList;

public class AudioComponentWidget extends Pane { //used to extend pane. Now it extends AudioComponentWidgetBase because we made a super class that will create all widgets
    //member variable for the audiocomponent
    public AudioComponent component_;
    //member variable that stores the anchor pane
    public AnchorPane parent_;
    public String label_;
    //make frequency label public
    Label freqLabel;

    //member variable for the sliders
    Slider widgetSlider_;

    //slider values
    double sliderValues_;

    //variables for the positions that will be used in the move method
    public double mouseXPos, mouseYPos, widgetXPos, widgetYPos;
    //variable that will store the line being drawn
    public static Line line_;

    //array list to store lines that are connected from one widget to another or to the speaker
    private ArrayList<Line> connectedLines = new ArrayList<>();


    //vertical box for the leftside of the widget
    VBox leftside;

    //constructor, takes an audio component, and a parent anchor pane
    public AudioComponentWidget(AudioComponent component, AnchorPane parent, String label) {  //Slider widgetSlider, double sliderValues
        //assign member variables to the parameters
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


        //handle drawing the line - handle 3 events
        output.setOnMousePressed(e -> startConnection(e, output));
        output.setOnMouseDragged(e -> moveConnection(e, output));
        output.setOnMouseReleased(e -> endConnection(e)); //this had output by it like the other two above it

        //add the close button, and circle to the vertical box
        rightSide.getChildren().add(closeButton);
        rightSide.getChildren().add(output);

        //set alignment, padding, and spacing to the right side of the widget
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        //LeftSide
        leftside = new VBox();
        freqLabel = new Label(label);
        leftside.getChildren().add(freqLabel);
        leftside.setOnMousePressed(e -> getPosInformation(e));
        leftside.setOnMouseDragged(e -> moveWidget(e));

        widgetLayout.getChildren().add(leftside);
        widgetLayout.getChildren().add(rightSide);

        //add the widget layout
        this.getChildren().add(widgetLayout);

        //set the position of the widget
        this.setLayoutX(100);
        this.setLayoutY(100);
    }


    public boolean lineIntersectsAnyNode() {
        for (Node node : parent_.getChildren()) {
            if (this instanceof SineWaveWidget && node instanceof MixerWidget) {
                MixerWidget shape = (MixerWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    Mixer result = (Mixer) shape.component_;
                    result.connectInput(this.component_);
                    return true;
                }
            }
            if (this instanceof SineWaveWidget && node instanceof VolumeAdjusterWidget) {
                VolumeAdjusterWidget shape = (VolumeAdjusterWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    VolumeAdjuster result = (VolumeAdjuster) shape.component_;
                    result.connectInput(this.component_);
                    System.out.println(result);
                    return true;
                }

            }

            if (this instanceof MixerWidget && node instanceof VolumeAdjusterWidget) {
                VolumeAdjusterWidget shape = (VolumeAdjusterWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    VolumeAdjuster result = (VolumeAdjuster) shape.component_;
                    result.connectInput(this.component_);
//                    System.out.println("Mixer Wave Touching Volume True");
//                    SynthesizeApplication.widgets_.add(this);
                    System.out.println("Size of Volume Mixers " + SynthesizerApplication.widgets.size());
                    return true;
                }
            }
        }
        return false;
    }


    //method that determines the end connection for the drawing of the line
    private void endConnection(MouseEvent e) {
        Circle speaker = SynthesizerApplication.speaker;
        if (this instanceof MixerWidget || this instanceof SineWaveWidget || this instanceof VolumeAdjusterWidget) {

            if (line_.intersects(speaker.getBoundsInParent())) {
                System.out.println(" Line Touching Node");
                SynthesizerApplication.connectedWidgets.add(this);
            } else if (!lineIntersectsAnyNode()){
                parent_.getChildren().remove(line_);
                line_ = null;
            }
        }

    }


    private void moveConnection(MouseEvent e, Circle output) {
        //create the bounds for the parent
        Bounds parentBounds = parent_.getBoundsInParent();
        //set the end of the line
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());

    }

    //My original code
    private void startConnection(MouseEvent e, Circle output) {

        //get the bounds of the parent and output
        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        //check to see if the line is not null, and if it is not, remove the line
//        if (line_ != null) {
//            parent_.getChildren().remove(line_);
//        }


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



    //method that sets the frequency, responds to the event of a mouse
    protected void setFrequency(MouseEvent e, Slider freqSlider) {
        //cast sine wave to the component so it knows that our component is of type sine wave, use the set frequency method
        //in our sin wave class to set the frequency to our parameter freqSlider
        ((SineWave) component_).setFrequency((freqSlider.getValue()));
    }


    //method that closes the widget when an action event occurs which is when the X is pressed on the widget
    private void closeWidget(ActionEvent e) {
        //to close the widget we need to remove the widget from the parent (center anchor pane) and then remove the widget from the array list of widgets
        parent_.getChildren().remove(this);
        SynthesizerApplication.widgets.remove(this);

        //remove the connected widget and the line that is connected when we close the widget
        SynthesizerApplication.connectedWidgets.remove(this);
        parent_.getChildren().remove(line_);

    }

    //method to move the widget
    protected void moveWidget(MouseEvent e) {
        //variables to store the change in x and y position as the widget is getting moved around
        double deltaX = e.getSceneX() - mouseXPos;
        double deltaY = e.getSceneY() - mouseYPos;

        //relocate the widget based on the change in position done by the mouse
        this.relocate(deltaX + widgetXPos, deltaY + widgetYPos);
    }

    //method to get the position information of the widget
    protected void getPosInformation(MouseEvent e) {
        //store the position of the mouse x and y and the widget x and y
        mouseXPos = e.getSceneX();
        mouseYPos = e.getSceneY();
        widgetXPos = this.getLayoutX();
        widgetYPos = this.getLayoutY();
    }
}