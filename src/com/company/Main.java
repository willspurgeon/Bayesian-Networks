package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static String query;

    public static void main(String[] args) {
	    if(args.length != 3){
            System.out.println("Incorrect number of inputs!");
            System.exit(-1);
        }

        String networkFilePath = args[0];
        String queryFilePath = args[1];
        Integer numSamples = Integer.parseInt(args[2]);


        try {

            FileReader networkFileReader = new FileReader(networkFilePath);
            BufferedReader networkBufferedReader = new BufferedReader(networkFileReader);

            FileReader queryFileReader = new FileReader(queryFilePath);
            BufferedReader queryBufferedReader = new BufferedReader(queryFileReader);


            int inputCount = 0;
            networkBufferedReader.readLine();

            ArrayList<String> networkInput = new ArrayList<>();
            String line;
            while((line  = networkBufferedReader.readLine()) != null){
                networkInput.add(line);
            }

            query = queryBufferedReader.readLine();

        }catch (FileNotFoundException error){

        }catch (IOException error){

        }
    }
}
