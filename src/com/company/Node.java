package com.company;

import java.util.ArrayList;

/**
 * Created by Will on 2/28/16.
 */

//Assuming option A.
public class Node {

    Node(String name, String[] parents, String[] probabilities, String type){
        this.nodeName = name;
        this.parents = parents;
        this.conditionalProbability = probabilities;
    }

    public NodeType type;
    String nodeName;

    String[] conditionalProbability;

    String[] parents;


    enum NodeType{
        QUERY,
        EVIDENCE,
        UNKNOWN
    }

}
