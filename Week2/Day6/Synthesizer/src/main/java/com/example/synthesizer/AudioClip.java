package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
    //byte array variable
    byte[] byteArray;

    //static variable for the duration constant
    static final Integer duration = 2;

    //static variable for the sample rate
    static final Integer sampleRate = 44100;

    //constructor
    public AudioClip() {
        byteArray = new byte[7];
    }

    //methods
    //get a specific sample in the array
    public int getSample(int index) {
        //variables to store the target bytes
        byte b1 = byteArray[(index * 2) + 1];
        byte b2 = byteArray[index * 2];
        //variable that will store the returned integer containing the 16 bits
        int sample = 0;

        //shift b1 to the left 8
        b1 <<= 8;

        //use bitwise OR on b1 and b2 to get the actual sample
        sample = b1 | b2;

        return sample;
    }

    //set a specific sample in the array
    public void setSample(int index, int value) {
        //create byte variables that will set each byte
        //cast type byte to the value so that they can be compared. To get the high byte, we need to first right shift the value by 8 then and each bit with a 1
        byte b1 = (byte) ((value >> 8) & 0xFF);
        byte b2 = (byte) (value & 0xFF);
    }

    //method that gets our array of bytes and returns it
    public byte[] getData() {
        //use the copy of method to copy our byte array
        return Arrays.copyOf(byteArray, byteArray.length);
    }

}
