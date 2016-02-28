package com.company;

import java.util.ArrayList;

/**
 * Created by Will on 2/28/16.
 */

//Assuming option A.
public class Node {

    public NodeType type;
    String nodeName;

    float conditionalProbability;

    ArrayList<Node> parents;


    enum NodeType{
        QUERY,
        EVIDENCE,
        UNKNOWN
    }

}
