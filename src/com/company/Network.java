package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Will on 2/28/16.
 */
public class Network {

    ArrayList<Node> network = new ArrayList<>();

    public Network(String networkFilePath, String queryPath) {
        ArrayList<String> networkInput = new ArrayList<>();
        String[] nodeTypes = null;
        try {

            FileReader networkFileReader = new FileReader(networkFilePath);
            BufferedReader networkBufferedReader = new BufferedReader(networkFileReader);

            FileReader queryFileReader = new FileReader(queryPath);
            BufferedReader queryBufferedReader = new BufferedReader(queryFileReader);

            String query = queryBufferedReader.readLine();
            nodeTypes = query.split(",");

            int inputCount = 0;

            networkInput = new ArrayList<>();
            String line;
            System.out.println("LINE BEFORE");
            while((line  = networkBufferedReader.readLine()) != null){
                System.out.println("LINE");
                networkInput.add(line);
            }

        }catch (FileNotFoundException error){
            System.out.println("FILE NOT FOUND!");

        }catch (IOException error){
            System.out.println("IO EXCEPTION");
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
            node.setType(nodeTypes[i]);
            network.add(node);
            i++;
        }

    }

    public Node getNodeWithName(String name){
        for(Node node: network){
            if(node.nodeName.equals(name)){
                return node;
            }
        }
        System.out.println(name + " ABORT!");
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
            ArrayList<Boolean> priors = priorSample();

            boolean consistent = isConsistent(priors);
            if(consistent) {
                if (priors.get(indexOfQuery())) {
                    counts.set(1, counts.get(1) + 1);
                } else {
                    counts.set(0, counts.get(0) + 1);
                }
            }
        }
        System.out.println("False count: " + counts.get(0) + " True count: " + counts.get(1));
        return (double)counts.get(1) / (double)(counts.get(0) + counts.get(1)); //# of trues / total
    }

    private boolean isConsistent(ArrayList<Boolean> priors){
        if(priors.size() != network.size()){
            System.out.println("Fatal error!");
            System.exit(-1);
        }

        ArrayList<Integer> evidence = indexesOfEvidence();

        for(Integer i: evidence){
            if(priors.get(i)){
                if(network.get(i).type != Node.NodeType.EVIDENCETRUE){
                    return false;
                }
            }else{
                if(network.get(i).type != Node.NodeType.EVIDENCEFALSE){
                    return false;
                }
            }
        }
        return true;

    }

    private ArrayList<Integer> indexesOfEvidence(){
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        for(Node node: network){
            if(node.type == Node.NodeType.EVIDENCEFALSE || node.type == Node.NodeType.EVIDENCETRUE){
                result.add(i);
            }
            i++;
        }
        return result;
    }

    private int indexOfQuery(){
        int i = 0;
        for(Node node: network){
            if(node.type == Node.NodeType.QUERY){
                return i;
            }
            i++;
        }
        return -1;
    }

    private ArrayList<Boolean> priorSample(){
        ArrayList<Boolean> output = new ArrayList<>();
        int i = 0;
        for(Node node: network){
            output.add(node.priorSample());
            i++;
        }
        //System.out.println("Size of priors: " + output.size());
        return output;
    }

    public double likelyhoodWeightingSampling(int numSamples){
        ArrayList<Double> counts = new ArrayList<>();
        counts.add(0.0); //False count
        counts.add(0.0); //True count
        for(int i = 1; i <= numSamples; i++){
            WeightedSample samples = weightedSample();
                for(Boolean bool: samples.output){
                    if(bool){
                        counts.set(1, counts.get(1)+samples.w);
                    }else{
                        counts.set(0, counts.get(0)+samples.w);
                    }
            }
        }
        System.out.println("False count: " + counts.get(0) + " True count: " + counts.get(1));
        return counts.get(1)/(counts.get(0)+counts.get(1));
    }

    private WeightedSample weightedSample(){
        double w = 1.0;
        ArrayList<Boolean> output = new ArrayList<>();
        int i = 0;
        for(Node node: network){
            if(node.type == Node.NodeType.EVIDENCEFALSE || node.type == Node.NodeType.EVIDENCETRUE){
                //w = w * node.
                if(node.type == Node.NodeType.EVIDENCETRUE){
                    System.out.println("Parents: " + node.probGivenParents());
                    w = w * node.probGivenParents();
                }else{
                    w = w * (1-node.probGivenParents());
                }
            }else{
                output.add(node.priorSample());
            }
            i++;
        }
        return new WeightedSample(w, output);
    }

    class WeightedSample{
        double w = 1.0;
        ArrayList<Boolean> output = new ArrayList<>();

        WeightedSample(double w, ArrayList<Boolean> priors){
            this.w = w;
            this.output = priors;
        }
    }

}
