package com.example.synthesizer;

import java.util.ArrayList;

public class Mixer implements AudioComponent{
    //public member variable ArrayList of AudioClips
    private ArrayList<AudioClip> mixer;

    //constructor
    public Mixer() {
        this.mixer = new ArrayList<>();
    }
    @Override
    public AudioClip getClip() {
        //create new clip
        AudioClip mixedClip = new AudioClip();
        //for each loop - to loop through arraylist clips
        for (AudioClip oneClip : mixer) {
            //inner loop to loop through each index within each clip
            for (int i = 0; i < AudioClip.totalSamples; i++) {
                mixedClip.setSample(i, mixedClip.getSample(i) + oneClip.getSample(i));
            }

        }
        //add value from for each loop clip to outer clip
        //use set sample to add value back to outer clip
        return mixedClip;
    }

    @Override
    public boolean hasInput() {
        if (this.mixer == null) {
            return false;
        }
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        //take the input and use the getclip() method to get a clip
        AudioClip inputClip = input.getClip();
        //add the inputClip to our ArrayList member variable
        mixer.add(inputClip);
    }



}
