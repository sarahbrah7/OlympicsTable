import java.util.Scanner;//for scanner input
import java.util.Random;//for random dice [FOR ABSTRACT DATA TYPE]

//all imported for reading and writing to files
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//for queue implementation
import java.io.*;
import java.util.*;

//record of country's medals
class CountryInfo{
    //holds countries name
    String countryName;
    //holds countries medal totals
    int goldTotal;
    int silverTotal;
    int bronzeTotal;
    int totalMedals;
}

class olympics {
    //main procedure commences the program
    public static void main(String[] param) {
        //will create a text file to store the olympics table content
        try {
            File olympicTable = new File("medalTable.txt");
            if (olympicTable.createNewFile()) {
                System.out.println("File created: " + olympicTable.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        //calls the procedure which asks the user for their chosen country
        askCountry();
    }

    //---------------------------------------------------COUNTRIES---------------------------------------------------//
    //asks user for their chosen country
    public static void askCountry() {
        int amount;
        //for the user's input
        Scanner scanner = new Scanner(System.in);

        //asks user for amount of countries will be on the medal table
        System.out.println("Choose how many countries you would like to keep track of");
        //holds the user's answer
        amount = scanner.nextInt();

        //will ask user
        askEvent(amount);
    }

    //-----------------------------------------------------MEDALS-----------------------------------------------------//
    //calculates the medal won randomly
    public static char randomMedal() {
        char result;
        int diceResult;

        //for the dice random is defined within the procedure
        Random rand = new Random();

        //limits the random number generated to 0-5
        diceResult = rand.nextInt(6);
        //added 1 to the result so it simulates the limit between 1-6
        //... this is as it simulates a dice
        diceResult += 1;

        //checks whether the output is 3 or 4
        if (diceResult == 3 | diceResult == 4) {
            System.out.println("A bronze medal was won by the country during the event.");
            //coutnry wins a bronze medal
            result = 3;
        }
        //checks whether the output is 5
        else if (diceResult == 5) {
            System.out.println("A silver medal was won by the country during the event.");
            //country wins a silver medal
            result = 2;
        }
        //checks whether the output is 6
        else if (diceResult == 6) {
            System.out.println("A gold medal was won by the country during the event.");
            //country wins a gold medal
            result = 1;
        }
        //checks whether the output is 1 or 2
        else {
            System.out.println("No medals won by the country during the event.");
            //coutnry does not win the medal
            result = 0;
        }

        //returns the result of the medal
        return result;
    }

    //procedure calculates the amount of gold medals the country won
    public static int alreadyWonMedals(String medalType) {
        //holds the amount the country won
        int oldMedal;

        //for the user's input
        Scanner scanner = new Scanner(System.in);

        //outputs to the user
        System.out.println("How many " + medalType + " medals has the team already won?");
        //holds the users input from the question
        oldMedal = scanner.nextInt();

        //returns the input made by the user
        return oldMedal;
    }

    //calculates the total of medals won by the country
    public static int medalTotal(int finalBronze, int finalSilver, int finalGold) {
        int tempFinal;

        //creates a total by adding medals together
        tempFinal = finalBronze + finalSilver + finalGold;

        //returns the total
        return tempFinal;
    }

    //-----------------------------------------------------EVENTS-----------------------------------------------------//
    //procedure checks whether the event is still going on
    public static void askEvent(int totalCountries) {
        //holds the name of the next event
        String nextEvent;
        //checks whether the event is going on
        boolean check = false;
        //holds the counter of events which have occurred
        int countEvent;
        //holds the type of medal won
        int medalResult;
        //holds the total of medals won
        int total;
        //will hold the current countries names
        String tempCountry;
        int tempBronze;
        int tempSilver;
        int tempGold;

        //Queue for countries names
        Queue<String> tempNames = new PriorityQueue<>();

        //arrays will hold all countries information
        String[] countryNames = new String[totalCountries];
        int[] bronzeMedals = new int[totalCountries];
        int[] silverMedals = new int[totalCountries];
        int[] goldMedals = new int[totalCountries];
        //this array woudl simulate a queue
        int[] medalTotals = new int[totalCountries];

        //holds the user's input
        Scanner scanner = new Scanner(System.in);

        //iterates for every country
        for (int i = 0; i < totalCountries; i++) {
            //asks user for country name and stores within array
            System.out.println("What's the country you will be keeping a track of?");
            tempCountry = scanner.next();
            tempNames.add(tempCountry);

            //finds the countries original amount of medals
            tempBronze = alreadyWonMedals("Bronze");
            tempSilver = alreadyWonMedals("Silver");
            tempGold = alreadyWonMedals("Gold");

            //asks user for amount of events occuring
            System.out.println("How many events will there be?");
            countEvent = scanner.nextInt();

            //loops for every event which occurs
            for (int j = countEvent; j > 0; j--) {
                //asks for what the current event is
                System.out.println("What is the current event?");
                //holds user input
                nextEvent = scanner.next();

                //randomly decides whether the country has won a medal during the event
                // ... and if so which medal was won
                medalResult = randomMedal();
                //checks if a gold medal was one
                if (medalResult == 1) {
                    //increments amount of gold medals won
                    tempGold += 1;
                } else if (medalResult == 2) {
                    //increments amount of silver medals won
                    tempSilver += 1;
                } else if (medalResult == 3) {
                    //increments amount of bronze medals won
                    tempBronze += 1;
                }
            }

            bronzeMedals[i] = tempBronze;
            silverMedals[i] = tempSilver;
            goldMedals[i] = tempGold;

            //calculates the total amount of medals won in seperate
            total = medalTotal(goldMedals[i], silverMedals[i], bronzeMedals[i]);
            //stoers the total amount of medals won by each country in the queue
            medalTotals[i] = total;
        }

        for(int i = 0; i < totalCountries; i++){
            countryNames[i] = tempNames.poll();
        }

        //will create the overall medal table once all medals have been calculated
        finalTable(bronzeMedals, silverMedals, goldMedals, medalTotals, countryNames);
    }

    //-----------------------------------------------------SORTING-----------------------------------------------------//
    //creates the overall final medal table (Sorted)
    public static void finalTable(int[] finalBronzes, int[] finalSilvers, int[] finalGolds, int[] finalTotals, String[] finalCountries) {
        //stores amount of countries which have partken in the program
        int size = finalTotals.length;
        //new array storres unsorted array of medal totals
        int[] oldTotals = new int[size];
        //control for while loop
        int index;

        //creates an unsorted array
        for(int i = 0; i < size; i++){
            oldTotals[i] = finalTotals[i];
        }

        //this medal totals array will now be sorted
        finalTotals = bubblesort(finalTotals, size);

        //searches for the position where the coutnries information is
        //so that the information is printed correctly in order
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if(finalTotals[i] == oldTotals[j]){
                    countryRecords(finalGolds[j], finalSilvers[j], finalBronzes[j], finalTotals[i], finalCountries[j], i + 1, size);
                }
            }
        }
    }

    //a bubble sort algorithm
    public static int[] bubblesort(int[] array, int arraySize) {
        // checks if the array has been sorted
        boolean sorted = false;

        //would iterate if the array is not sorted
        while (!sorted) {
            //once for iteration is complete
            sorted = true;

            //iterates through array to sort until all array is sorted
            for (int i = 0; i < arraySize - 1; i++) {
                for (int j = 0; j < arraySize - 1; j++) {
                    //checks if the value after the curerent value is bigger
                    if (array[j] < array[j + 1]) {
                        //if so a swap occurs
                        int tmp = array[j + 1];
                        array[j + 1] = array[j];
                        array[j] = tmp;
                        // array still hasn't been sorted
                        sorted = false;
                    }
                }
            }
        }
        //returns the sorted array
        return array;
    }

//-----------------------------------------------------OUTPUT-----------------------------------------------------//
    //creates a record for each country
    public static void countryRecords(int totalGold, int totalSilver, int totalBronze, int totalMedals, String finalCountryNames,int currentFinalCount, int totalRecrods){

         //new record made for country
         CountryInfo thisCountry = new CountryInfo();

         thisCountry.countryName = finalCountryNames;
         thisCountry.goldTotal = totalGold;
         thisCountry.silverTotal = totalSilver;
         thisCountry.bronzeTotal = totalBronze;
         thisCountry.totalMedals = totalMedals;

         //will output the board with the coutnries information
         outputInfo(thisCountry, currentFinalCount, totalRecrods);
    }

    //method which outputs the information of the country
    public static void outputInfo(CountryInfo finalCountry, int checkAmount, int allCountriesFinal){
         //for user input when asked what they want of the program
         String askMore;
         //will check if input is valid
         boolean checkValid = true;

         Scanner scanner = new Scanner(System.in);
        //the output statement
        System.out.println(finalCountry.countryName + " G:" + finalCountry.goldTotal + " S:" + finalCountry.silverTotal + " B:" + finalCountry.bronzeTotal + " Final:" + finalCountry.totalMedals);
        //will add the current country to the text file
        addToArchive(finalCountry);

        //to check what user wants to do after
        while(checkValid == true && checkAmount == allCountriesFinal){
            System.out.println("Input number answer for what you would want to do [1. Record more countries; 2. Look at table archives; 3. End program]");
            //holds given option made by the user
            askMore = scanner.next();
            //will redo the program and ask user for ocuntries
            if(askMore.equals("1")){
                askCountry();
                checkValid = false;
            }
            //will output the full olympics table
            else if(askMore.equals("2")){
                outputArchives();
                checkValid = true;
            }
            //will end the program
            else if(askMore.equals("3")){
                clearArchive();
                System.exit(0);
                checkValid = false;
            }
            //invalid output means the user is asked the question again
            else{
                System.out.println("Invalid answer, try again");
                checkValid = true;
            }
        }
     }
     //procedure writes to file
     public static void addToArchive(CountryInfo countryArchive){
         //will hold the records contents
         int gold = countryArchive.goldTotal;
         int silver = countryArchive.silverTotal;
         int bronze = countryArchive.bronzeTotal;
         int total = countryArchive.totalMedals;
         String name = countryArchive.countryName;

         //all integer values are converted into string values
         String strGold = String.valueOf(gold);
         String strSilver = String.valueOf(silver);
         String strBronze = String.valueOf(bronze);
         String strTotal = String.valueOf(total);

         //will check if the file can be written to
         try{
             //creates a writable file
             FileWriter table = new FileWriter("/Users/sarahbrahimi/Documents/Procecural Module/medalTable.txt", true);
             BufferedWriter addToTable = new BufferedWriter(table);

             //adds the given statement to the the file
             addToTable.write("[" + name + " G:" + strGold + " S:" + strSilver + " B:" + strBronze + " Final:" + strTotal + "]");
             addToTable.close();
         }
         catch (IOException e) {
             //outputs error statement
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
     }

     //will print out the complete external file
     public static void outputArchives(){
         try{
             //creates a readble file
             File outputTable = new File("/Users/sarahbrahimi/Documents/Procecural Module/medalTable.txt");

             Scanner reader = new Scanner(outputTable);
             //checks for when all the text has finished on the file
             while(reader.hasNextLine()) {
                 //outputs every line of the file until it is complete
                 String filesLine = reader.nextLine();
                 System.out.println(filesLine);
             }
             //closes file
             reader.close();
         }
         //if there is an error
        catch (IOException e) {
             //outputs error message
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

     }

     //program flushes the whole file for when the program is used in the future
     public static void clearArchive(){
        try{
            FileWriter oldArchives = new FileWriter("/Users/sarahbrahimi/Documents/Procecural Module/medalTable.txt");
            //rewirtes the file to be empty
            oldArchives.write("");
            oldArchives.close();
        }
        //if there is an error
        catch (IOException e) {
            //outputs error message
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
     }
}