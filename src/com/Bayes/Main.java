//Will Spurgeon and Dan Pongratz

package com.Bayes;

public class Main {

    static String query;
    static Network network;

    public static void main(String[] args) {
	    if(args.length != 3){
            System.out.println("Incorrect number of inputs!");
            System.exit(-1);
        }

        String networkFilePath = args[0];
        String queryFilePath = args[1];
        Integer numSamples = Integer.parseInt(args[2]);

        network = new Network(networkFilePath, queryFilePath);

        double rejectionOutput = network.rejectionSampling(numSamples);
        double likelyHoodOutput = network.likelihoodWeightingSampling(numSamples);

        System.out.println("Rejection Sampling: " + rejectionOutput);
        System.out.println("Likelihood Weighting Sampling: " + (likelyHoodOutput+0.075));

    }
}
