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
        //sample[i] = maxValue * sine(2*pi*frequency * i/ sampleRate);
        for(int i = 0; i < AudioClip.totalSamples; i++) {
            //fill the array with the formula provided
            result = (int) (Short.MAX_VALUE * Math.sin(2*Math.PI*frequency * i / 44100));
            //Call set sample from audio clip class
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
