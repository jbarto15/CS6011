package com.example.synthesizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {

    @Test
    void getSample() {
        AudioClip audioClip = new AudioClip();
        audioClip.setSample(1,32768);
        AudioClip audioClip1 = new AudioClip();
        audioClip1.setSample(2,-32768);



        Assertions.assertEquals(32768, audioClip.getSample(1));
        //Assertions.assertEquals(-32768, audioClip1.getSample(1));

    }

    //@Test
//    void setSample() {
//        AudioClip audioClip = new AudioClip();
//        AudioClip audioClip1 = new AudioClip();
//
//        audioClip.setSample(1,400);
//        audioClip1.setSample(2, 32768);
//
//
//        Assertions.assertEquals(400, audioClip.getSample(1));
//        Assertions.assertEquals(32768, audioClip1.getSample(2));
//
//
//    }

    @Test
    void getData() {
    }
}