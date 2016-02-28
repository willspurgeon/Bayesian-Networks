package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Will on 2/28/16.
 */
public class Network {

    ArrayList<Node> network = new ArrayList<>();

    public Network(String networkFilePath, String queryPath) {
        ArrayList<String> networkInput = new ArrayList<>();
        try {

            FileReader networkFileReader = new FileReader(networkFilePath);
            BufferedReader networkBufferedReader = new BufferedReader(networkFileReader);


            int inputCount = 0;

            networkInput = new ArrayList<>();
            String line;
            while((line  = networkBufferedReader.readLine()) != null){
                networkInput.add(line);
            }

        }catch (FileNotFoundException error){

        }catch (IOException error){

        }


        int i = 0;
        for(String line: networkInput){
            String[] lineArray = line.split(": ");
            String nodeName = lineArray[0];

            Pattern pattern = Pattern.compile("\\[((.{4}\\d ?){0,2})\\] \\[((\\d.\\d{1,2}( ?)){0,})\\]");
            System.out.println(lineArray[1]);
            Matcher m = pattern.matcher(lineArray[1]);

            if(!m.matches()){
                System.out.println("Something is very wrong.");
            }

            String[] parentNames = m.group(1).split(" ");
            String[] probabilities = m.group(3).split(" ");

            Node node = new Node(nodeName, parentNames, probabilities);
            network.add(node);
            i++;
        }

    }

    private void assignNodeStatus(String filePath){
        try{
            FileReader queryFileReader = new FileReader(filePath);
            BufferedReader queryBufferedReader = new BufferedReader(queryFileReader);
            String query = queryBufferedReader.readLine();

            String[] queries = query.split(",");

            int i = 0;
            for(Node node: network){
                node.setType(queries[i]);
                i++;
            }

        }catch(FileNotFoundException error){

        }catch (IOException error){

        }
    }

    public double rejectionSampling(int numSamples){
        return 0.0;
    }

    public double likelyhoodWeightingSampling(int numSamples){
        return 0.0;
    }

}
