package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
    //byte array variable
    byte[] byteArray;

    //static variable for the duration constant
    static final int duration = 2;

    //static variable for the sample rate
    static final int sampleRate = 44100;

    //total samples
    public final static int totalSamples = duration * sampleRate;

    //constructor
    public AudioClip() {
        byteArray = new byte[duration * sampleRate * 2];
    }

    //methods
    //get a specific sample in the array
    public int getSample(int index) {
        //variables to store the target bytes
        int b1 = byteArray[index * 2 + 1] & 0xFF;
        System.out.println("MSB"+Integer.toString(byteArray[index * 2 + 1],2));
        int b2 = byteArray[index * 2] & 0xFF;
        System.out.println("LSB"+Integer.toString(byteArray[index * 2],2));

        //shift b1 to the left 8
        b1 <<= 8;

        //use bitwise OR on b1 and b2 to get the actual sample
         int sample = b1 | b2;
        System.out.println("wholesample"+Integer.toString(sample,2));
        return sample;
    }

    //set a specific sample in the array
    public void setSample(int index, int value) {
        //set the higher byte and the lower byte to the value given
        //cast type byte to the value so that they can be compared. To get the high byte, we need to first right shift the value by 8 then & each bit with a 1
        short sample = (short) value;
        System.out.println("original sample"+Integer.toString(sample,2));

        System.out.println("MSB at set"+Integer.toString((sample >> 8),2));
        byteArray[(index * 2) + 1] = (byte) ((sample >> 8)& 0xFF);

        byteArray[index * 2] = (byte) (sample & 0xFF);
        System.out.println("LSB at set"+Integer.toString((sample & 0xFF),2));

//        byte least = (byte) value;
//        byte most = (byte) (value >> 8);
//        byteArray[2 * index] = least;
//        byteArray[2 * index + 1] = most;
    }

    //method that gets our array of bytes and returns it
    public byte[] getData() {
        //use the copy of method to copy our byte array
        return Arrays.copyOf(byteArray, byteArray.length);
    }

}
