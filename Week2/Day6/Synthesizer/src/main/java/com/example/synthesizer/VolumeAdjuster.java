package com.example.synthesizer;

public class VolumeAdjuster implements AudioComponent {
    //variable for the scale
    private int scale;

    //create an AudioComponent variable
    private AudioComponent component;

    //constructor
    public VolumeAdjuster(AudioComponent component, int scale) {
        this.scale = scale;
        this.component = component;

    }

    @Override
    public AudioClip getClip() {
        AudioClip original = component.getClip();
        AudioClip updatedClip = new AudioClip();
        //use a for loop to go through the original clip and then update each sample with the given scale (change in volume)
        for (int i = 0; i < AudioClip.totalSamples; i++) {
            //create a variable to store the updated value after multiplying it by the scale
            int updatedValue = scale * original.getSample(i);
            //if the updated value is greater than the Short.MAX_VALUE, assign the updated value to the max value
            if (updatedValue > Short.MAX_VALUE) {
                updatedValue = Short.MAX_VALUE;
                updatedClip.setSample(i, updatedValue);
            }
            //if the updated value is less than the Short.MIN_VALUE, assign the updated value to the min value
            if (updatedValue < Short.MIN_VALUE) {
                updatedValue = Short.MIN_VALUE;
                updatedClip.setSample(i, updatedValue);
            }
            else{
                updatedClip.setSample(i, updatedValue);
            }
        }
        return updatedClip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}
