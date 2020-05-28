import java.util.Scanner;
import java.util.InputMismatchException;

public class AT160ProblemSolver {
    private static final double constant = 0.7854;
    private static final double convertToCi = 16.387;

    public static double calcCR(double DH, double CHV, double B, double S) {
        double SV = constant * B * B * S;
        double CLV = constant * B * B * DH;
        CHV = CHV / convertToCi;
        double CR = CLV + SV + CHV;
        CR = CR / (CLV + CHV);
        return CR;
    }

    public static double calcCID(double B, double S, int C) {
        return B * B * constant * S * C;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double DH;
        double CHV;
        double B;
        double S;
        int c;
        int counter = 0;
        while (counter < 1) {
            try {
                System.out.println("What would you like to calculate?");
                System.out.println("Your options are: \"CID\" (displacement),"
                + " \"CR\" (compression ratio), and \"quit\".");
                String toCalc = in.next();
                toCalc = toCalc.toUpperCase();
                switch(toCalc) {
                        case "CR": 
                            System.out.println("What is the deck height, in " +
                            "inches?");
                            DH = in.nextDouble();
                            System.out.println("What is the chamber volume, in"
                            + " cubic centimeters?");
                            CHV = in.nextDouble();
                            System.out.println("What is the bore, in inches?");
                            B = in.nextDouble();
                            System.out.println("What is the stroke, in" 
                            + " inches?");
                            S = in.nextDouble();
                            System.out.println("The compression ratio is: " +
                            calcCR(DH, CHV, B, S) + ":1");
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "CID":
                            System.out.println("What is the bore, in inches?");
                            B = in.nextDouble();
                            System.out.println("What is the stroke, in" 
                            + " inches?");
                            S = in.nextDouble();
                            System.out.println("How many cylinders?");
                            c = in.nextInt();
                            System.out.println("The displacement is: " + 
                            calcCID(B, S, c)+ " inches.");
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "QUIT":
                            counter = 100;
                            break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nYou put in something wrong. Try again." +
                "\n");
            }
        }

    }
}