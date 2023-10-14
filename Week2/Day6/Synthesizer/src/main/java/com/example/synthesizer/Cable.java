//package com.example.synthesizer;
//
//import SynthesizerBasic.AudioClip;
//import SynthesizerBasic.AudioComponent;
//import javafx.geometry.Bounds;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//
//public class Cable implements AudioComponent {
//    //variable that will store the line
//    Line line_;
//
//    //variable that will store the parent
//    AnchorPane parent_;
//
//    //variable to store the inputComponent that will be connected to the speaker
//    AudioComponent inputAC;
//
//    //constructor
//
//    @Override
//    public AudioClip getClip() {
//        return null;
//    }
//
//    @Override
//    public boolean hasInput() {
//        return false;
//    }
//
//    @Override
//    public void connectInput(AudioComponent input) {
//        inputAC = input;
//
//    }
//
//    //method that will draw a line between the inputs to show the connection visually
//    public void drawInputLines(AudioComponent input1, AudioComponent input2) {
//        //look up how to draw a line between the two components
//
//    }
//
//
//    //NOTE FROM CLASS
//    //handle drawing the line - handle 3 events
//        //should say speaker instead of output at some point
//        Circle output = new Circle(10);
//
//        output.setOnMousePressed(e->startConnection(e, output));
//        output.setOnMouseDragged(e->moveConnection(e, output));
//        output.setOnMouseReleased(e->endConnection(e, output));
//
//
//    //NOTE FROM CLASS
//    private void endConnection(MouseEvent e, Circle output) {
//        Circle speaker = SynthesizerApplication.speaker;
//        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
//
//        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0 * Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0)));
//        if (distance < 10) {
//            //the wave to some array list
//            SynthesizerApplication.widgets.add(this); //that will add the connected to the other opened //nabil changed this widgets name to ConnectedWidgets
//            //better to create a new array list for connected widgets only
//
//            //the wave to some array list
//        } else {
//            parent_.getChildren().remove(line_);
//            line_ = null;
//        }
//    }
//
//    private void moveConnection(MouseEvent e, Circle output) {
//        Bounds parentBounds = parent_.getBoundsInParent();
//        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
//        line_.setEndY(e.getSceneY() - parentBounds.getMinY());
//
//    }
//
//    private void startConnection(MouseEvent e, Circle output) {
//        if (line_ != null) {
//            parent_.getChildren().remove(line_);
//        }
//
//        Bounds parentBounds = parent_.getBoundsInParent();
//        Bounds bounds = output.localToScene(output.getBoundsInLocal());
//
//        line_ = new Line();
//        line_.setStrokeWidth(5);
//
//        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
//        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
//
//        line_.setEndX((e.getSceneX()));
//        line_.setEndY(e.getSceneY());
//
//        parent_.getChildren().add(line_);
//
//
//    }
//}
