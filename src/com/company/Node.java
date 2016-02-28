package com.company;

import java.util.ArrayList;

/**
 * Created by Will on 2/28/16.
 */

//Assuming option A.
public class Node {

    Node(String name, String[] parents, String[] probabilities){
        this.nodeName = name;
        this.parents = parents;
        this.conditionalProbability = probabilities;
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
            case "q":
                this.type = NodeType.QUERY;
                break;
        }
    }

    public NodeType type;
    String nodeName;

    String[] conditionalProbability;

    String[] parents;


    enum NodeType{
        QUERY,
        EVIDENCETRUE,
        EVIDENCEFALSE,
        UNKNOWN
    }

}
