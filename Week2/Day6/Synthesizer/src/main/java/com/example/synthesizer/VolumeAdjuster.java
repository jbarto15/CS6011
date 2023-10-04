package com.example.synthesizer;

public class VolumeAdjuster implements AudioComponent {
    //variable for the scale
    private float scale;

    //create an AudioComponent variable
    private AudioComponent inputAC;

    //constructor, sets the member variable called scale
    public VolumeAdjuster(float scale) {
        this.scale = scale;
    }

    @Override
    public AudioClip getClip() {
        //variable to store the input clip
        AudioClip inputClip = inputAC.getClip();
        //variable that stores the updated clip after the volume has been changed
        AudioClip result = inputAC.getClip();
        //use a for loop to go through the original clip and then update each sample with the given scale (change in volume)
        for (int i = 0; i < AudioClip.totalSamples; i++) {
            //create a variable to store the updated value after multiplying it by the scale
            int updatedValue = (int) (scale * inputClip.getSample(i));
            //if the updated value is greater than the Short.MAX_VALUE, assign the updated value to the max value
            //if the updated value is less than the Short.MIN_VALUE, assign the updated value to the min value
            if (updatedValue > Short.MAX_VALUE) {
                updatedValue = Short.MAX_VALUE;
            } else if (updatedValue < Short.MIN_VALUE) {
                updatedValue = Short.MIN_VALUE;
            }
            //set the sample in the result clip to the updated value
            result.setSample(i, updatedValue);
        }
        return result;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        //save the input component to our member variable inputAC
        inputAC = input;
    }
}
