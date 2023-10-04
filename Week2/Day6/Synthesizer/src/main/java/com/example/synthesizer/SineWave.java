package com.example.synthesizer;

public class SineWave implements AudioComponent {
    double frequency;

    //constructor
    public SineWave(double freq) {
        this.frequency = freq;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        int result;
        //use a loop
        for (int i = 0; i < AudioClip.totalSamples; i++) {
            //fill the array with the formula given to us
            result = (int) (Short.MAX_VALUE * Math.sin(2*Math.PI*frequency * i / 44100));
            //call set sample from audio clip class
            clip.setSample(i, result);
        }
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
