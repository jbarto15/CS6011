package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SineWaveWidget extends AudioComponentWidget {
    public SineWaveWidget(AudioComponent component, AnchorPane parent, String label) {
        super(component, parent,"Sine Wave");

        Slider freqSlider = new Slider(200, 880, 400);
        leftside.getChildren().add(freqSlider);
        freqSlider.setOnMouseDragged(e -> setFrequency(e, freqSlider)); //add freqLabel after freqSlider

    }
    }
