package org.example;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimilarityAlgorithm {
    private int hashSize;
    private List<Integer> coefficients;
    private List<Integer> hashSet1;
    private List<Integer> hashSet2;

    public SimilarityAlgorithm() {
        this.hashSet1 = new ArrayList<>();
        this.hashSet2 = new ArrayList<>();
    }

    DecimalFormat df = new DecimalFormat("0.########");
    public void run(String filename1, String filename2, int hashSize, int numFunctions) throws IOException {
        long startTime = System.nanoTime();

        this.hashSize = hashSize;
        this.coefficients = generateCoefficients(numFunctions);
        hashSet1.clear();
        hashSet2.clear();

        CalculateHashSet(filename1, hashSet1);
        CalculateHashSet(filename2, hashSet2);

        System.out.println(Colors.PURPLE + "Files: " + filename1 + ", " + filename2);
        System.out.println("HashSize: " + hashSize + ", numFunctions: " + numFunctions + Colors.RESET);
        System.out.println(Colors.GREEN + "Jackard index: " + df.format(jaccardIndex()) + Colors.RESET);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double seconds = (double) duration / 1_000_000_000.0;
        System.out.println(Colors.YELLOW + "Execution time: " + seconds + "s\n" + Colors.RESET);
    }
    private void CalculateHashSet(String filename, List<Integer> hashSet) throws IOException {
        String wholeText = loadTextFromFile(filename);

        for (int i = 0; i <= wholeText.length() - hashSize; i += 1) {
            String substring = wholeText.substring(i, i + hashSize);
            hashSet.add(calculateMinHash(substring));
        }
    }

    private String loadTextFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder textBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            textBuilder.append(line);
        }
        reader.close();

        return textBuilder.toString();
    }
    private List<Integer> generateCoefficients(int numFunctions) {
        List<Integer> coeffs = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < numFunctions; i++)
        {
            coeffs.add(rnd.nextInt());
        }
        return coeffs;
    }

    private int calculateMinHash(String text) {
        int hashCode = text.hashCode();

        int minHashValue = Integer.MAX_VALUE;
        for (int coefficient : coefficients) {
            int hashedValue = hashCode ^ coefficient;

            if (hashedValue < minHashValue) {
                minHashValue = hashedValue;
            }
        }
        return minHashValue;
    }

    private double jaccardIndex() {
        int common = 0;
        for (Integer integer : hashSet1) {
            if (hashSet2.contains(integer)) {
                common++;
            }
        }
        System.out.println(Colors.DARK_GREEN + "Jackard values: [S1]" + hashSet1.size() + " [S2]" + hashSet2.size() + " [S1&S2]" + common + Colors.RESET);
        return (double) common / (hashSet1.size() + hashSet2.size() - common);
    }
}
