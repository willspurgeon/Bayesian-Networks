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

    public Node getNodeWithName(String name){
        for(Node node: network){
            if(node.nodeName == name){
                return node;
            }
        }
        return null;
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

    private Node getQueryVariable(){
        for(Node node: network){
            if(node.type == Node.NodeType.QUERY){
                return node;
            }
        }
        return null;
    }

    public double rejectionSampling(int numSamples){
        ArrayList<Integer> counts = new ArrayList<>();
        counts.add(0); //False count
        counts.add(0); //True count

        for(int i = 1; i <= numSamples; i++){
            boolean x = getQueryVariable().priorSample();
            //TODO: If x is consistent with the evidence variables:
                if(x){
                    counts.set(1, counts.get(1) + 1);
                }else{
                    counts.set(0, counts.get(1) + 1);
                }
        }

        return counts.get(1) / (counts.get(0) + counts.get(1)); //# of trues / total
    }

    private ArrayList<Boolean> priorSample(){
        Node queryVariable = getQueryVariable();
        ArrayList<Boolean> output = new ArrayList<>();
        int i = 0;
        for(Node node: network){
            output.add(node.priorSample());
            i++;
        }
        return output;
    }

    public double likelyhoodWeightingSampling(int numSamples){
        for(int i = 1; i <= numSamples; i++){

        }

        return 0.0;
    }

    private double weightedSample(){
        return 0.0;
    }

}
