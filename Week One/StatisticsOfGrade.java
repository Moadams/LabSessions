import java.util.Scanner;

public class StatisticsOfGrade{
    public static void main(String[] args) {
        
        // Declare the scanner object as sc
        Scanner sc = new Scanner(System.in);

        // prompt the user to enter the number of students 
        System.out.print("Please enter the number of students: ");
        int numberOfStudents = sc.nextInt(); // receive the input

        // consume the new line buffer created by nextInt()
        sc.nextLine();

        double scores[] = getScores(sc, numberOfStudents); // get the scores using the getScores method

        
        sc.close(); // close scanner to prevent memory leaks
        
        double maxScore = getMaxValue(scores); // get the maximum score using the getMaxValue method
        double minScore = getMinValue(scores); // get the minimum score using the getMinValue method
        double averageScore = getAverageValue(scores); // get the average score using the getAverageValue method


        // calculating grade distribution
        int[] stats = calculateGradeStats(scores);

        
        // print out results
        System.out.println("The maximum grade is " + maxScore);
        System.out.println("The minimum grade is " + minScore);
        System.out.println("The average grade is " + averageScore + "\n\n\n");
        plotGraph(stats);

    }

    // getting scores method
    public static double[] getScores(Scanner sc, int numberOfStudents) {
        // prompt the user to enter the marks of students
        System.out.println("Please enter the scores of the students separated by a space: ");
        String scoreInput = sc.nextLine();  // storing the input as a whole string

        // Create a string array and split the values
        String[] scoreArray = scoreInput.trim().split("\\s+");

        // check if the length of the score string is not equal to the numberOfStudents
        if(scoreArray.length != numberOfStudents){
            System.out.println("The number of students should be equal to the number of scores entered");
            return getScores(sc, numberOfStudents);
        }

        // initialize a score array
        double[] scores = new double[numberOfStudents];

        for(int i = 0; i < scoreArray.length; i++){
            scores[i] = validateScore(sc, scoreArray[i]);
        }

        return scores;
    }


    public static double validateScore(Scanner sc, String scoreString) {
        while(true){
            try{
                double score = Double.parseDouble(scoreString); // try converting the string into an integer
                // check if the value is within the range from 0 to 100
                if(score >= 0 && score <= 100){
                    return score;
                }
                System.out.println(scoreString + " is not a valid input. Please input a valid number from 0 to 100");
            }catch(NumberFormatException e){
                System.out.println(scoreString + " is not a valid input. Please input a valid number from 0 to 100");
            }
            scoreString = sc.next(); // get the input again
        }
    }

    public static double getMaxValue(double[] scores){
        double maxValue = scores[0];
        for(int i = 1; i < scores.length; i++){
            if(scores[i] > maxValue){
                maxValue = scores[i];
            }
        }
        return maxValue;
    }

    public static double getMinValue(double[] scores){
        double minValue = scores[0];
        for(int i = 1; i < scores.length; i++){
            if(scores[i] < minValue){
                minValue = scores[i];
            }
        }
        return minValue;
    }

    public static double getAverageValue(double[] scores){
        double sum = 0;
        for(int i = 0; i < scores.length; i++){
            sum += scores[i];
        }
        return (double) sum / scores.length;
    }

    public static int[] calculateGradeStats(double[] scores){
        int[] stats = new int[5];
        for(int i = 0; i < scores.length; i++){
            if(scores[i] > 80){
                stats[4] += 1;
            }else if(scores[i] >= 61 && scores[i] <= 80){
                stats[3] += 1;
            }else if(scores[i] >= 41 && scores[i] <= 60){
                stats[2] += 1;
            }else if(scores[i] >= 21 && scores[i] <= 40){
                stats[1] += 1;
            }else{
                stats[0] += 1;
            }
        }
        return stats;
    }

    public static void plotGraph(int[] stats){
        System.out.println("Graph : \n\n\n ");
        
        // find the maximum number in the stats array
        int statsMax = stats[0];
        for(int i = 1; i < stats.length; i++){
            if(stats[i] > statsMax){
                statsMax = stats[i];
            }
        }

        

        // Start drawing the y axis from the statsMax to 1
        for(int level = statsMax; level >= 1; level--){
            System.out.print(" " + level + " >"); // labels for the y axis
            
            // plot the values for the various levels
            for(int i = 0; i < stats.length; i++){
                if(stats[i] >= level){
                    System.out.print("   #######   ");
                }else{
                    System.out.print("             ");
                }
            }
            System.out.println();
        }

        System.out.println("    +---------- + ---------- + ---------- + ---------- + ---------- +");
        System.out.println("    I   0-20    I   21-40    I    41-60   I    61-80   I   81-100   I");
    

    }
}