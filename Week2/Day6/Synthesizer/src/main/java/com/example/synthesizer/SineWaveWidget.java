package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SineWaveWidget extends AudioComponentWidget {
    //member variable that stores an audio component
    public AudioComponent component_;
    public SineWaveWidget(AudioComponent component, AnchorPane parent, String label) {
        super(component, parent,"Sine Wave");
        component_ = component;

        Slider freqSlider = new Slider(200, 880, 400);
        leftside.getChildren().add(freqSlider);
        int value = (int) freqSlider.getValue();
        freqLabel.setText(label + " " + value + " Hz");
        freqSlider.setOnMouseDragged(e -> setFrequency(e, freqSlider));
    }
}
