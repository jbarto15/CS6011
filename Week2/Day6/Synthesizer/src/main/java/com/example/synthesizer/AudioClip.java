package com.example.synthesizer;

public class AudioClip {
    //byte array variable
    byte[] byteArray;

    //static variable for the duration constant
    static final Integer duration = 2;

    //static variable for the sample rate
    static final Integer sampleRate = 44100;

    //methods
    //get a specific sample in the array
    public int getSample(int index) {
        //variable to store the target byte
        int targetByte = 0;
        //use a loop to go through the byte[] array
        for (int i = 0; i < byteArray.length; i++) {
            if (index == byteArray[i]) {
                targetByte = byteArray[i];
            }
        }
        return targetByte;
    }

    //set a specific sample in the array
    public void setSample(int index, int value) {

    }

    //method that gets our array of bytes and returns it
    public byte[] getData() {
        //in this array the data needs to be stored properly
        //The values should be stored in Little Endian order.
        //In other words, for the value of sample i, the lower 8 bits should be stored at array[ 2*i ] and the upper 8 bits should be stored at array[ (2*i) + 1 ]
    }

}
