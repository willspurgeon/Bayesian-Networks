package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Will on 2/28/16.
 */
public class Network {

    ArrayList<Node> network = new ArrayList<>();

    public Network(ArrayList<String> networkInput, String query) {
        String[] queries = query.split(",");

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

            Node node = new Node(nodeName, parentNames, probabilities, queries[i]);
            network.add(node);
            i++;
        }

    }
}
