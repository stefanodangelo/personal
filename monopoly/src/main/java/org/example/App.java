package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner inputStream = new Scanner(System.in);
        int POPULATION_SIZE = 10, WELL_PREDICTED = 0, TOTAL_WELL_PREDICTED = 0, TOTAL_CASES = 0;
        List<Double> odds = new ArrayList<>();

        while(true){
            //initialize odds
            for (WheelValue val : WheelValue.values()) {
                if(val.equals(WheelValue.ONE) || val.equals(WheelValue.TWO) || val.equals(WheelValue.FIVE))
                    odds.add(val.getProbability());
                else {
                    int index = odds.size() - 1;
                    odds.set(index, odds.get(index) + val.getProbability());
                }
            }
            //analyze values
            for(int i = 0; i < POPULATION_SIZE; i++){
                printRecommendedBets();
                System.out.println("SPIN NO." + (i+1) + "\n");
                printOdds(odds);
                WheelValue predictedValue = predictValue(odds, (double) WELL_PREDICTED/ (double) i, (double) TOTAL_WELL_PREDICTED/ (double) TOTAL_CASES);
                System.out.print("Insert the value that came out from the wheel (");
                for (WheelValue value : WheelValue.values()) {
                    System.out.print(Color.formatMessageColor(value.getValue(), value.getColor()));
                    if (value.equals(WheelValue.values()[WheelValue.values().length - 1]))
                        System.out.print("): ");
                    else
                        System.out.print(", ");
                }

                WheelValue extractedValue = getCorrectInput(inputStream);
                if(predictedValue.equals(extractedValue)) {
                    WELL_PREDICTED++;
                    TOTAL_WELL_PREDICTED++;
                }
                System.out.println();

                for(int j = 0; j < WheelValue.values().length; j++)
                    if(WheelValue.values()[j].equals(extractedValue)) {
                        int index = odds.size() - 1;
                        calculateNewOdd(POPULATION_SIZE, odds, Math.min(j, index));
                    }

                TOTAL_CASES++;
                System.out.print("\033[H\033[2J");
            }
            //reset parameters
            odds = new ArrayList<>();
            WELL_PREDICTED = 0;
        }
    }

    private static void calculateNewOdd(double POPULATION_SIZE, List<Double> odds, int index) {
        //double odd = odds.remove(index);
        double newOdd = (odds.get(index) - ((1.0 / POPULATION_SIZE) * 100));
        if(newOdd < 0) {
            for(int k = 0; k < odds.size(); k++) {
                if(!odds.get(k).equals(odds.get(index))){
                    double weight = WheelValue.values()[k].getProbability();
                    if (k == odds.size() - 1) {
                        for (int i = odds.size(); i < WheelValue.values().length; i++)
                            weight += WheelValue.values()[i].getProbability();
                    }
                    odds.set(k, odds.get(k) + ((1.0 / POPULATION_SIZE) * weight) / (odds.size() - 1));
                }
            }
        }
        odds.set(index, newOdd);
    }

    private static WheelValue getCorrectInput(Scanner input) {
        WheelValue correctInput;
        correctInput = WheelValue.getWheelByValue(input.next());
        while (correctInput == null) {
            System.out.println("Not valid value. Type again.");
            correctInput = getCorrectInput(input);
        }
        return correctInput;
    }

    private static WheelValue predictValue(List<Double> odds, double accuracy, double totAccuracy){
        double max = 0.0;
        WheelValue predictedValue;

        for(Double d : odds) {
            /*
            double weight = WheelValue.values()[odds.indexOf(d)].getProbability();
            if(odds.indexOf(d) == odds.size() - 1){
                for(int i = odds.size(); i < WheelValue.values().length; i++)
                    weight += WheelValue.values()[i].getProbability();
            }
            if (d * weight / 100 > max)
                max = d;
             */
            if (d > max)
                max = d;
        }
        predictedValue = WheelValue.values()[odds.indexOf(max)];
        System.out.print("Next value will probably be ");
        if(odds.indexOf(max) == odds.size() - 1) {
            System.out.print("one of the following: ");
            for(int i = odds.size() - 1; i < WheelValue.values().length; i++) {
                System.out.print(Color.formatMessageColor(WheelValue.values()[i].getValue(), WheelValue.values()[i].getColor()));
                if (i != WheelValue.values().length - 1)
                    System.out.print(", ");
            }
            System.out.println();
        }
        else
            System.out.println(": " + Color.formatMessageColor(predictedValue.getValue(),predictedValue.getColor()));
        System.out.printf("Accuracy: %.2f", accuracy*100);
        System.out.println("%");
        System.out.printf("Total accuracy: %.2f", totAccuracy*100);
        System.out.println("%");
        System.out.println();

        return predictedValue;
    }

    private static void printOdds(List<Double> odds){
        for (int k = 0; k < odds.size(); k++) {
            if (k == odds.size() - 1) {
                System.out.print("Odds of ");
                for (int l = k; l < WheelValue.values().length; l++) {
                    System.out.print(Color.formatMessageColor(WheelValue.values()[l].getValue(), WheelValue.values()[l].getColor()));
                    if (l == WheelValue.values().length - 1)
                        System.out.print(":\t");
                    else
                        System.out.print(", ");
                }
            }
            else
                System.out.print("Odds of " + Color.formatMessageColor(WheelValue.values()[k].getValue(), WheelValue.values()[k].getColor()) + ":\t\t\t");
            System.out.printf("%.2f", odds.get(k));
            System.out.println("%");
        }
        System.out.println();
    }

    private static void printRecommendedBets(){
        System.out.print("RECOMMENDED BETS: ");
        System.out.println(Color.formatMessageColor(WheelValue.TWO.getValue(), WheelValue.TWO.getColor()) + "-" +
                           Color.formatMessageColor(WheelValue.FIVE.getValue(), WheelValue.FIVE.getColor()) + "-(" +
                           Color.formatMessageColor(WheelValue.X2.getValue(), WheelValue.X2.getColor()) + ")   " +
                           Color.formatMessageColor("\u2605", Color.YELLOW));
        System.out.println("\t\t  " + Color.formatMessageColor(WheelValue.FIVE.getValue(), WheelValue.FIVE.getColor()) + "-" +
                           Color.formatMessageColor(WheelValue.TEN.getValue(), WheelValue.TEN.getColor()) + "-" +
                           Color.formatMessageColor(WheelValue.X2.getValue(), WheelValue.X2.getColor()));
        System.out.println("\t\t  " + Color.formatMessageColor(WheelValue.FIVE.getValue(), WheelValue.FIVE.getColor()) + "-" +
                           Color.formatMessageColor(WheelValue.X2.getValue(), WheelValue.X2.getColor()) + "-(" +
                           Color.formatMessageColor(WheelValue.X4.getValue(), WheelValue.X4.getColor()) + ")");
    }
}
