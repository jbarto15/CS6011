package com.example.synthesizer;

import SynthesizerBasic.AudioClip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AudioClipTest {

    @Test
    void getSample() {
        AudioClip audioClip = new AudioClip();
        audioClip.setSample(1,32767);
        AudioClip audioClip1 = new AudioClip();
        audioClip1.setSample(2,-2);



        Assertions.assertEquals(32767, audioClip.getSample(1));
        Assertions.assertEquals(-2, audioClip1.getSample(2));

    }

    @Test
    void setSample() {
        AudioClip audioClip = new AudioClip();
        AudioClip audioClip1 = new AudioClip();

        audioClip.setSample(1,400);
        audioClip1.setSample(2, 32767);


        Assertions.assertEquals(400, audioClip.getSample(1));
        Assertions.assertEquals(32767, audioClip1.getSample(2));


    }

    @Test
    void getData() {
    }
}