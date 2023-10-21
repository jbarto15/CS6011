//import SynthesizerBasic.AudioClip;
//import SynthesizerBasic.AudioComponent;
//import com.example.synthesizer.AudioComponentWidget;
//import javafx.geometry.Bounds;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.shape.Line;
//
//public class Cable implements AudioComponent {
//    private AudioComponent sourceWidget;
//    private AudioComponent destinationWidget;
//    private Pane canvas;  // Reference to the canvas for visual representation
//
//    //variable to store the line drawn
//    Line line_;
//    //store the parent
//    AnchorPane parent_;
//
//    public Cable(AudioComponent source, AudioComponent destination, AnchorPane parent) {
//        this.sourceWidget = source;
//        this.destinationWidget = destination;
//        this.parent_ = parent;
//    }
//
//    public void connectWidgets() {
//        // Connect audio between source and destination widgets.
//        destinationWidget.connectInput(sourceWidget);
//
//        // Draw the cable visually on the canvas.
//        drawCable();
//    }
//
//    private void drawCable(AudioComponentWidget output) {
//        //get the bounds of the parent and output
//        Bounds parentBounds = parent_.getBoundsInParent();
//        Bounds bounds = output.localToScene(output.getBoundsInLocal());
//
//        //create a new line and set the stroke width, we made this a global variable in the class so that it could be used by all the connection methods
//        line_ = new Line();
//        line_.setStrokeWidth(5);
//
//        //set the starting point of the line to the current widget subtracted by the parent minimum X and Y position
//        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
//        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
//
//        //set the end point of where the line should be drawn
//        line_.setEndX((e.getSceneX()));
//        line_.setEndY(e.getSceneY());
//
//        //add the line to the parent which is our center anchor pain
//        parent_.getChildren().add(line_);
//    }
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
//        sourceWidget = input;
//    }
//}
