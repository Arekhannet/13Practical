// Code is stored as 13template.java
import java.lang.Math.*;  
import java.io.*;   
import java.text.*; 
import java.util.*;

public class timeMethods {
    public static int N = 32654;
    
    public static void main(String args[]) {
        
        DecimalFormat twoD = new DecimalFormat("0.00");
        DecimalFormat fourD = new DecimalFormat("0.0000");
        DecimalFormat fiveD = new DecimalFormat("0.00000");
        
        long start, finish;
        double runTime = 0, runTime2 = 0, time;
        double totalTime = 0.0;
        int n = N;
        int repetition, repetitions = 30;
        
        // Read data from file
        Node[] records = readDataFromFile("ulysses.numbered");
        Node[] sortedRecords = null;
        int[] searchKeys = null;
        
        if (records != null) {
            // Create sorted copy for binary search
            sortedRecords = records.clone();
            Arrays.sort(sortedRecords, (a, b) -> Integer.compare(a.key, b.key));
            
            // Generate 30 random keys
            searchKeys = generateRandomKeys(30, 1, 32654);
        }
        
        runTime = 0;
        for(repetition = 0; repetition < repetitions; repetition++) {
            start = System.currentTimeMillis();
            
            // call the procedures to time here:
            for (int key : searchKeys) {
                linearsearch(records, key);
                binarysearch(sortedRecords, key);
            }
            // Figure out how to alter this guideline here,
            
            finish = System.currentTimeMillis();
            
            time = (double)(finish - start);
            runTime += time;
            runTime2 += (time*time); 
        }
        
        double aveRuntime = runTime/repetitions;
        double stdDeviation = 
            Math.sqrt(runTime2 - repetitions*aveRuntime*aveRuntime)/(repetitions-1);
        
        System.out.printf("\n\n\fStatistics\n");
        System.out.println("________________________________________________");
        System.out.println("Total time   =           " + runTime/1000 + "s.");
        System.out.println("Total time\u00b2  =           " + runTime2);
        System.out.println("Average time =           " + fiveD.format(aveRuntime/1000)
                          + "s. " + '\u00B1' + " " + fourD.format(stdDeviation) + "ms.");
        System.out.println("Standard deviation =     " + fourD.format(stdDeviation));
        System.out.println("n            =           " + n);
        System.out.println("Average time / run =     " + fiveD.format(aveRuntime/n*1000) 
                          + '\u00B5' + "s. "); 
        System.out.println("Repetitions  =             " + repetitions);
        System.out.println("________________________________________________");
        System.out.println();
        System.out.println();
    }
    
    // Node class for storing key-value pairs
    static class Node {
        int key;
        String data;
        
        Node(int key, String data) {
            this.key = key;
            this.data = data;
        }
    }
    
    // Method to read data from file
    static Node[] readDataFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<Node> nodeList = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2) {
                    int key = Integer.parseInt(parts[0]);
                    String data = parts[1];
                    nodeList.add(new Node(key, data));
                }
            }
            reader.close();
            
            return nodeList.toArray(new Node[0]);
            
        } catch (IOException e) {
            return null;
        }
    }
    
    // Method to generate random keys
    static int[] generateRandomKeys(int count, int min, int max) {
        Random rand = new Random();
        int[] keys = new int[count];
        
        for (int i = 0; i < count; i++) {
            keys[i] = rand.nextInt(max - min + 1) + min;
        }
        
        return keys;
    }
    
    // Linear search method
    static int linearsearch(Node[] arr, int targetKey) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].key == targetKey) {
                return i;
            }
        }
        return -1;
    }
    
    // Binary search method
    static int binarysearch(Node[] arr, int targetKey) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid].key == targetKey) {
                return mid;
            }
            
            if (arr[mid].key < targetKey) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
