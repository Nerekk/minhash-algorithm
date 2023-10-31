package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        SimilarityAlgorithm algorithm = new SimilarityAlgorithm();
        int[] qs = {10, 20, 40};
        int[] funs = {100, 200};

        algorithm.run("Sienkiewicz_1.txt", "Sienkiewicz_2.txt", 20, 200);
        algorithm.run("Sienkiewicz_1.txt", "Prus_1.txt", 20, 200);
        System.out.println("---------------------------------\n");

        for (int q : qs) {
            for (int fun : funs) {
                algorithm.run("Sienkiewicz_1.txt", "Prus_1.txt", q, fun);
            }
        }
    }
}