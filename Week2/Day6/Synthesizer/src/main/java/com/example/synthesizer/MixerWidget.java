package com.example.synthesizer;

import SynthesizerBasic.AudioComponent;
import javafx.scene.layout.AnchorPane;

public class MixerWidget extends AudioComponentWidget {
    public MixerWidget(AudioComponent component, AnchorPane parent, String label) {
        super(component, parent, label);

        //set the position of the widget
        this.setLayoutX(400);
        this.setLayoutY(100);
    }
}
