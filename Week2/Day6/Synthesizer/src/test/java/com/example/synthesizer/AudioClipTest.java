package com.example.synthesizer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {

    @Test
    void getSample() {
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        randomNumbers.add(1);
        randomNumbers.add(4);
        randomNumbers.add(-12);
        randomNumbers.add(6);
        randomNumbers.add(8);
        randomNumbers.add(32);
        randomNumbers.add(-21);
        AudioClip clip = new AudioClip();
        clip.getSample(2);
    }

    @Test
    void setSample() {
    }

    @Test
    void getData() {
    }
}