package com.example.synthesizer;
import javax.sound.sampled.*;



public class Main {
    public static void main(String[] args) throws LineUnavailableException {
        // Get properties from the system about samples rates, etc.
// AudioSystem is a class from the Java standard library.
        Clip c = AudioSystem.getClip(); // Note, this is different from our AudioClip class.

// This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
        AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

        //create a sin wave
        AudioComponent generate = new SineWave(440); // Your code
        AudioComponent generate2 = new SineWave(100);
        AudioComponent generate3 = new SineWave(660);
        //create a Volume adjuster object with a scale to change the volume by
        AudioComponent audioAdjust = new VolumeAdjuster(0.9f);
        //use the connect input method in audio adjust to connect the first generated sin wave
        audioAdjust.connectInput(generate);
        //create an audio clip of type sin wave
        AudioClip clip = generate.getClip();// Your code
        AudioClip clip2 = generate2.getClip();
        //create an audio clip of type volume adjuster
        AudioClip clip3 = audioAdjust.getClip();
        //create a type mixer audio component
        AudioComponent mix = new Mixer();
        //call the connect input method on the mix object and send in the sin wave as the input
        mix.connectInput(generate);
        mix.connectInput(generate2);
        mix.connectInput(generate3);
        //call the connect input method on the volume adjuster object and send in the mix object as the input
        audioAdjust.connectInput(mix);
        //create an audio clip of type mixer
        AudioClip clip4 = mix.getClip();
        //create linear ramp audio component
        AudioComponent ramp = new LinearRamp(50, 2000);
        AudioComponent variableFrequency = new VFSineWave();
        variableFrequency.connectInput(ramp);
        AudioClip clip5 = variableFrequency.getClip();


        c.open( format16, clip5.getData(), 0, clip5.getData().length ); // Reads data from our byte array to play it.

        System.out.println( "About to play..." );
        c.start(); // Plays it.
        c.loop( 0 ); // Plays it 2 more times if desired, so 6 seconds total

// Makes sure the program doesn't quit before the sound plays.
        while( c.getFramePosition() < AudioClip.totalSamples || c.isActive() || c.isRunning() ){
            // Do nothing while we wait for the note to play.
        }

        System.out.println( "Done." );
        c.close();
    }
}
