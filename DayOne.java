import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class DayOne {
    public static void main(String[] args) throws FileNotFoundException {
//        final int lines = 0;
        final String locationsLists = "C:\\Users\\mdela\\IdeaProjects\\AdventOfCode\\src\\AoCD1input.csv";
        final String dayOneTestData = "C:\\Users\\mdela\\IdeaProjects\\AdventOfCode\\src\\AoCD1testData.csv";

        int[] leftColumnLocations = splitLinesWithIntegersFromLeftColumn(locationsLists);
        int[] leftColumnLocationsTestData = splitLinesWithIntegersFromLeftColumn(dayOneTestData);
        int[] rightColumnLocations = splitLinesWithIntegersFromRightColumn(locationsLists);
        int[] rightColumnLocationsTestData = splitLinesWithIntegersFromRightColumn(dayOneTestData);

        List<Integer> leftRecordsSorted = Arrays.stream(leftColumnLocations).boxed().sorted().toList();
        List<Integer> leftRecordsSortedTestData = Arrays.stream(leftColumnLocationsTestData).boxed().sorted().toList();
        List<Integer> rightRecordsSorted = Arrays.stream(rightColumnLocations).boxed().sorted().toList();
        List<Integer> rightRecordsSortedTestData = Arrays.stream(rightColumnLocationsTestData).boxed().sorted().toList();

        //sum of differences between each record from left column and right column
        int sumOfDifferences = sumAllDifferencesBetweenLocations(leftRecordsSorted, rightRecordsSorted, 0, countLinesOfLocationsFromCsv(locationsLists));
        System.out.println("Suma różnic pomiędzy rekordów z lewej i prawej strony: "+sumOfDifferences);

        //similarity score = the sum of the multiplication of the record from the left column of the list
        // and the number of its occurrences in the column from the right list
        int countOfOccurancesOfTheRecord = calculateCountOfOccurancesOfTheRecord(leftRecordsSorted, rightRecordsSorted, 999);
//        System.out.println("TEST1: Liczba "+leftRecordsSorted.get(999)+" występuje w prawej kolumnie "+countOfOccurancesOfTheRecord+" razy");

        System.out.println("similarityScoreTest: "+sumAllMultiplicationsOfTheRecordAndItsOccurancesCount(leftRecordsSortedTestData,
                rightRecordsSortedTestData,0,countLinesOfLocationsFromCsv(dayOneTestData)));

        System.out.println("similarityScore: "+sumAllMultiplicationsOfTheRecordAndItsOccurancesCount(leftRecordsSorted,
                rightRecordsSorted,0,countLinesOfLocationsFromCsv(locationsLists)));
    }


    static int[] splitLinesWithIntegersFromLeftColumn(String locationsLists) throws FileNotFoundException {
        final int linesNumber = countLinesOfLocationsFromCsv(locationsLists);
        int[] leftColumnLocations = new int[linesNumber];
        Scanner scanner = new Scanner(new File(locationsLists));
        for (int i = 0; i < linesNumber; i++) {
            String csvLine = scanner.nextLine();
            leftColumnLocations[i] = loadLeftRecord(csvLine);
        }
        scanner.close();
        return leftColumnLocations;
    }

    private static int loadLeftRecord(String csvLine) {
        String[] data = csvLine.split(";");
        return Integer.parseInt(data[0]);
    }

    static int[] splitLinesWithIntegersFromRightColumn(String locationsLists) throws FileNotFoundException {
        final int linesNumber = countLinesOfLocationsFromCsv(locationsLists);
        int[] rightColumnLocations = new int[linesNumber];
        Scanner scanner = new Scanner(new File(locationsLists));
        for (int i = 0; i < linesNumber; i++) {
            String csvLine = scanner.nextLine();
            rightColumnLocations[i] = loadRightRecord(csvLine);
        }
        scanner.close();
        return rightColumnLocations;
    }

    private static int loadRightRecord(String csvLine) {
        String[] data = csvLine.split(";");
        return Integer.parseInt(data[1]);
    }

    static int countLinesOfLocationsFromCsv(String locationsLists) throws FileNotFoundException {
        int lines = 0;
        Scanner scanner = new Scanner(new File(locationsLists));
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            lines++;
        }
        scanner.close();
        return lines;
    }

    static int calculateDifferenceBetweenLocations(List<Integer> ints1, List<Integer> ints2, int i) {
        int diffBetweenLocation = 0;
        if (ints1.get(i) != ints2.get(i)) {
            if (ints1.get(i) > ints2.get(i)) {
                diffBetweenLocation = ints1.get(i) - ints2.get(i);
            } else {
                diffBetweenLocation = ints2.get(i) - ints1.get(i);
            }
            return diffBetweenLocation;
        }
        return diffBetweenLocation;
    }

    static int sumAllDifferencesBetweenLocations(List<Integer> ints1, List<Integer> ints2, int i, int linesNumber) {
        int sum = 0;
        while (i < linesNumber) {
            sum += calculateDifferenceBetweenLocations(ints1, ints2, i);
            i++;
        }
        return sum;
    }

    static int calculateCountOfOccurancesOfTheRecord(List<Integer> ints1, List<Integer> ints2, int i) {
        int frequency = 0;
        frequency = Collections.frequency(ints2, ints1.get(i));
        return frequency;
    }
    static int sumAllMultiplicationsOfTheRecordAndItsOccurancesCount(List<Integer> ints1, List<Integer> ints2, int i, int linesNumber) {
        int sum = 0;
        while (i < linesNumber) {
            int countOfOccurancesOfTheRecord = calculateCountOfOccurancesOfTheRecord(ints1, ints2, i);
            sum += countOfOccurancesOfTheRecord * ints1.get(i);
            i++;
        }
        return sum;
    }
}