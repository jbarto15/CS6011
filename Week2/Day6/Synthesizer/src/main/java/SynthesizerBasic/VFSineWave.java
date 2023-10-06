package SynthesizerBasic;

public class VFSineWave implements AudioComponent {
    //Linear ramp member variable
    private AudioClip ramp;

    @Override
    public AudioClip getClip() {
        //create audio clip object to return
        AudioClip clip = new AudioClip();
        double phase = 0;
        for (int i = 0; i < AudioClip.totalSamples; i++) {
            phase = phase + 2 * Math.PI * ramp.getSample(i) / AudioClip.sampleRate;
            clip.setSample(i,(int) (Short.MAX_VALUE * Math.sin(phase)));
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        //assign input audio component to our ramp member variable as a audio clip
        ramp = input.getClip();
    }
}
