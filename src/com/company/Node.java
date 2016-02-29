package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Will on 2/28/16.
 */

//Assuming option A.
public class Node {

    Node(String name, String[] parents, String[] probabilities){
        this.nodeName = name;
        this.parents = parents;

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

    public NodeType type;
    String nodeName;

    ArrayList<Double> conditionalProbability;

    String[] parents;

    boolean priorSample(){
        switch (this.type) {
            case EVIDENCETRUE:
                return true;
            case EVIDENCEFALSE:
                return false;
            case UNKNOWN:
            case QUERY:
                //Ramdomly sample from CPT with samples from the parents.
                if (parents.length == 0) {
                    Random ran = new Random(System.nanoTime());
                    double randomNum = ran.nextDouble();
                    return randomNum > conditionalProbability.get(0);
                }
                if (parents.length == 1) {
                    Random ran = new Random(System.nanoTime());
                    double randomNum = ran.nextDouble();
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
