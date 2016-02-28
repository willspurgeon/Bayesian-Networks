package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Will on 2/28/16.
 */
public class Network {

    ArrayList<Node> network;

    public Network(ArrayList<String> networkInput, String query) {
        String[] queries = query.split(",");

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

            System.out.println("Name: " + nodeName + " Parent1: " + parentNames[0] + " Prob1: " + probabilities[1]);
        }



    }
}
