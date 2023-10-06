package SynthesizerBasic;

import java.util.ArrayList;

public class Mixer implements AudioComponent{
    //public member variable ArrayList of AudioClips
    private ArrayList<AudioClip> inputClips;

    //constructor, creates the array list
    public Mixer() {
        //assign a new array list to the inputClips member variable when the mixer object is created
        this.inputClips = new ArrayList<>();
    }
    @Override
    public AudioClip getClip() {
        //create new clip that will return the mixed clip
        AudioClip mixedClip = new AudioClip();
        //for each loop - to loop through arraylist of audio components
        for (AudioClip singleClip : inputClips) {
            //inner loop to loop through each sample within each clip
            for (int i = 0; i < AudioClip.totalSamples; i++) {
                //setting the mixedClip by adding together what's already in the mixed clip with the singleClip we're iterating through
                mixedClip.setSample(i, mixedClip.getSample(i) + singleClip.getSample(i));
            }

        }
        //return the mixed clip
        return mixedClip;
    }

    @Override
    public boolean hasInput() {
        //check to see if the array list called inputClips has anything inside of it, if it doesn't return false
        if (this.inputClips == null) {
            return false;
        }
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        //add the inputClip to our ArrayList member variable
        inputClips.add(input.getClip());
    }



}
