package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Will on 2/28/16.
 */

//Assuming option A.
public class Node {

    public NodeType type;
    String nodeName;

    ArrayList<Double> conditionalProbability;

    String[] parents;

    Node(String name, String[] parents, String[] probabilities){
        this.nodeName = name;
        for(String parent: parents){
            if(parent.equals("")){
                this.parents = null;
                break;
            }
            this.parents = parents;
        }


        this.conditionalProbability = new ArrayList<Double>();
        for(String prob: probabilities){
            this.conditionalProbability.add(Double.parseDouble(prob));
        }

    }

    public void setType(String type){
        switch (type){
            case "t":
                this.type = NodeType.EVIDENCETRUE;
                break;
            case "-":
                this.type = NodeType.UNKNOWN;
                break;
            case "f":
                this.type = NodeType.EVIDENCEFALSE;
                break;
            case "?":
                this.type = NodeType.QUERY;
                break;
        }
    }

    double probGivenParents(){
        switch (this.type) {
            case EVIDENCETRUE:
            case EVIDENCEFALSE:
            case UNKNOWN:
            case QUERY:
                if (parents == null) {
                    return conditionalProbability.get(1);
                }
                if (parents.length == 1) {
                    Main.network.getNodeWithName(parents[0]);
                    //System.out.println(parents[0]);
                    boolean parentValue = Main.network.getNodeWithName(parents[0]).priorSample();
                    if (parentValue) {
                        return conditionalProbability.get(3);
                    } else {
                        return conditionalProbability.get(1);
                    }
                }
                if (parents.length == 2) {
                    boolean parent1Value = Main.network.getNodeWithName(parents[0]).priorSample();
                    boolean parent2Value = Main.network.getNodeWithName(parents[1]).priorSample();

                    if (parent1Value && parent2Value) {
                        return conditionalProbability.get(7);
                    } else if (!parent1Value && parent2Value) {
                        return conditionalProbability.get(3);
                    } else if (parent1Value && !parent2Value) {
                        return conditionalProbability.get(5);
                    } else if (!parent1Value && !parent2Value) {
                        return conditionalProbability.get(1);
                    }
                }
        }
        return 0.0;
    }

    boolean priorSample(){
        switch (this.type) {
            case EVIDENCETRUE:
                return true;
            case EVIDENCEFALSE:
                return false;
            case UNKNOWN:
            case QUERY:
                //Ramdomly sample from CPT with samples from the parents.
                if (parents == null) {
                    Random ran = new Random(System.nanoTime());
                    double randomNum = ran.nextDouble();
                    //System.out.println("Returning: " + (randomNum > conditionalProbability.get(0)) + " Rand: " + randomNum + " Prob of true: " + conditionalProbability.get(1));
                    return randomNum > conditionalProbability.get(0);
                }
                if (parents.length == 1) {
                    Random ran = new Random(System.nanoTime());
                    double randomNum = ran.nextDouble();
                    Main.network.getNodeWithName(parents[0]);
                    //System.out.println(parents[0]);
                    boolean parentValue = Main.network.getNodeWithName(parents[0]).priorSample();
                    if (parentValue) {
                        return randomNum > conditionalProbability.get(2);
                    } else {
                        return randomNum > conditionalProbability.get(0);
                    }
                }
                if (parents.length == 2) {
                    Random ran = new Random(System.nanoTime());
                    double randomNum = ran.nextDouble();
                    boolean parent1Value = Main.network.getNodeWithName(parents[0]).priorSample();
                    boolean parent2Value = Main.network.getNodeWithName(parents[1]).priorSample();

                    if (parent1Value && parent2Value) {
                        return randomNum > conditionalProbability.get(6);
                    } else if (!parent1Value && parent2Value) {
                        return randomNum > conditionalProbability.get(2);
                    } else if (parent1Value && !parent2Value) {
                        return randomNum > conditionalProbability.get(4);
                    } else if (!parent1Value && !parent2Value) {
                        return randomNum > conditionalProbability.get(0);
                    }
                }
        }
        return false;
    }

    enum NodeType{
        QUERY,
        EVIDENCETRUE,
        EVIDENCEFALSE,
        UNKNOWN
    }

}
