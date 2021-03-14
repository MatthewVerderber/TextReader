import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.lang.Math;
import java.util.ArrayList;

public class TextReader{

    public static void main(String args[]) throws FileNotFoundException{
        
        File file1 = new File("C:\\Users\\Matt\\Java_Projects\\TextReader\\DriversLicense.txt");
        File file2 = new File("C:\\Users\\Matt\\Java_Projects\\TextReader\\Mood.txt");
        File file3 = new File("C:\\Users\\Matt\\Java_Projects\\TextReader\\BlindingLights.txt");
        File file4 = new File("C:\\Users\\Matt\\Java_Projects\\TextReader\\34+35.txt");
        File file5 = new File("C:\\Users\\Matt\\Java_Projects\\TextReader\\Levitating.txt");

        File[] fileArray = {file1, file2, file3, file4, file5};
        double[] percentArray = new double[((fileArray.length*2) + fileArray.length) / 2];
        double maxPercent = 0;
        int maxPercentFile1 = 0;
        int maxPercentFile2 = 0;

        /*
        System.out.println("Number of words counted with StringTokenizer: " + countWithTokenizer(file1));
        System.out.println("Number of words counted with String function split(): " + countWithSplit(file1));

        System.out.println("Number of unique words counted in file 1: " + countWordFrequency(file1).size());
        System.out.println("Number of unique words counted in file 2: " + countWordFrequency(file2).size());

        ArrayList<String> commonWords = findCommonWords(file1, file2);

        System.out.println("Number of words shared between the two files: " + commonWords.size());
        System.out.println("Shared Words: ");
        commonWords.forEach((value) -> {
            System.out.println(value);
        });

        System.out.println("Average percent similar words between files 1 and 2: " + compareTexts(file1, file2));
        */

        for(int i = 0; i < fileArray.length; i++){
            for(int j = i+1; j < fileArray.length; j++){
                if(compareTexts(fileArray[i], fileArray[j]) > maxPercent){
                    maxPercent = compareTexts(fileArray[i], fileArray[j]);
                    maxPercentFile1 = i + 1;
                    maxPercentFile2 = j + 1;
                }
            }
        }


        System.out.println("The most similar files are file" + maxPercentFile1 + " and file" + maxPercentFile2 + " with similarity: " + maxPercent);
    }

    public static int countWithTokenizer(File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        String currentLine = new String();
        int wordCount = 0;

        while(sc.hasNextLine()){
            currentLine = sc.nextLine();
            StringTokenizer st = new StringTokenizer(currentLine);
            while(st.hasMoreTokens()){
                wordCount++;
                st.nextToken();
            }
        }
        return wordCount;
    }


    public static int countWithSplit(File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        String currentLine = new String();
        int wordCount = 0;

        while(sc.hasNextLine()){
            currentLine = sc.nextLine();
            if(!currentLine.isEmpty()){                            //Prevents overcounting due to empty lines
                String[] splitHolder = currentLine.split("\\s+");  // \s+ is the regex for all types of whitespaceoo
                wordCount += splitHolder.length;
                }
        }

        return wordCount;
    }

    public static HashMap countWordFrequency(File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        Boolean inArray = false;
        String currentLine = new String();
        int fileWordCount = countWithSplit(file);

        fileWordCount = (fileWordCount <= 172000) ? fileWordCount : 344000;
        HashMap<String, Integer> uniqueWordCount = new HashMap<String, Integer>(fileWordCount);


        while(sc.hasNextLine()){
            currentLine = sc.nextLine();
            if(!currentLine.isEmpty()){                 
                for(String splitHolder : currentLine.split("\\s+")){
                    splitHolder = splitHolder.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "").toLowerCase();
                     if(uniqueWordCount.containsKey(splitHolder)){
                        uniqueWordCount.put(splitHolder, uniqueWordCount.get(splitHolder) + 1);
                    }
                    else {
                        uniqueWordCount.put(splitHolder, 1);
                    }
                }
            }
        }
        return uniqueWordCount;
    }
    
    public static ArrayList<String> findCommonWords(File file1, File file2) throws FileNotFoundException{

        HashMap<String, Integer> file1Unique = countWordFrequency(file1);
        HashMap<String, Integer> file2Unique = countWordFrequency(file2);

        ArrayList<String> commonWords = new ArrayList<String>();

        file1Unique.forEach((k1, v1) ->{
            file2Unique.forEach((k2, v2) -> {
                if(k1.equals(k2)){
                    commonWords.add(k1);
                }
            });
        });

        return commonWords;
    }

    public static double compareTexts(File file1, File file2) throws FileNotFoundException{

        HashMap<String, Integer> file1Unique = countWordFrequency(file1);
        HashMap<String, Integer> file2Unique = countWordFrequency(file2);

        ArrayList<String> commonWords = new ArrayList<String>();

        commonWords = findCommonWords(file1, file2);

        double percentFile1 = (double) commonWords.size() / (double) file1Unique.size();
        double percentFile2 = (double) commonWords.size() / (double) file2Unique.size();
        
        /*
        System.out.println("Number of words in common: " + commonWords.size());
        System.out.println("Percent similarity of file 1: " + percentFile1);
        System.out.println("Percent similarity of file 2: " + percentFile2);
        */

        double averagePercent = (percentFile1 + percentFile2) / 2;

        return averagePercent;
    }
}