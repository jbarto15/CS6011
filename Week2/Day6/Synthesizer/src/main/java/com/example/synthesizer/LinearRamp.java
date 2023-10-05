package com.example.synthesizer;

public class LinearRamp implements  AudioComponent {
    //member variable start
    private float start;

    //member variable stop
    private float stop;


    //constructor, give a start and stop value
    public LinearRamp(float start, float stop) {
        //assign the parameter values to my class member variables
        this.start = start;
        this.stop = stop;
    }
    @Override
    public AudioClip getClip() {
        //create a new audioclip
        AudioClip clip = new AudioClip();
        //result variable that will store the values of our sample
        int result;
        //for loop that goes through our total number of samples
        for (int i = 0; i < AudioClip.totalSamples; i++) {
            //fill the array with the formula given to us
            result = (int) (( start * ( AudioClip.totalSamples - i ) + stop * i ) / AudioClip.totalSamples);
            //call set sample from audio clip class
            clip.setSample(i, result);
        }
        //return the clip
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}
