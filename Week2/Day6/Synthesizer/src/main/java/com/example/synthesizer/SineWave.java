package com.example.synthesizer;

public class SineWave implements AudioComponent {
    //variable that will store the frequency
    double frequency;

    //constructor, takes a frequency and assigns that frequency to our member variable
    public SineWave(double freq) {
        this.frequency = freq;
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
            result = (int) (Short.MAX_VALUE * Math.sin(2*Math.PI*frequency * i / 44100));
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
