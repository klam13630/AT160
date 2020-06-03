import java.util.Scanner;
import java.util.InputMismatchException;

public class AT160ProblemSolver {
    private static final double constant = 0.7854;
    private static final double convertToCi = 16.387;
    private static final int[] tensile = {55000, 69000, 117500, 136500, 150000};

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

    public static double calcTL(double diameter, boolean longer) {
        if (longer) {
            return 2 * diameter + 0.5;
        }
        else {
            return 2 * diameter + 0.25;
        }
    }

    public static double brakeHorsepower (double torque, double rpm) {
        return torque * rpm / 5252;
    }
    public static double calcSA(double diameter) {
        return diameter * diameter * constant;
    }

    public static double indicatedHorsepower (double psi, double stroke, 
    double bore, double rpm, int cylinders) {
        return (psi * (stroke / 12) * (constant * (bore * bore)) * (rpm / 2) * cylinders) / 33000;
    }
       
    public static double calcTF(double diameter, int grade) {
        return calcSA(diameter) * tensile[grade / 2];
    }

    public static double AFR (double cfr, double ff){
            return cfr / ff;
    }
    public static double SFC( double ff, double bhp){
            return ff / bhp;
    }
    public static double VE( double cid , double cfr, double r){
        return (75500 / cid ) * (cfr / r) ;  
    }
    public static double BMEP( double cid , double q){
        return q * (159/cid);  
    }
    public static double TE( double sfc){
        return 13.1 / sfc;  
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double DH;
        double CHV;
        double B;
        double S;
        double d;
        double b;
        double q; 
        double r;
        double psi;
        double cfr;
        double ff;
        double bhp;
        double cid;
        double sfc;

        int c;
        int g;
        int counter = 0;
        while (counter < 1) {
            try {
                System.out.println("What would you like to calculate?");
                System.out.println("Your options are: \"CID\" (displacement),"
                + " \"CR\" (compression ratio), \"TL\" (thread length) " + 
                "\"SA\" (stress area), \"TF\" (total force), \"BHP\" (brake horsepower),"
                + "\"IHP\" (indicated horsepower)," +
                " \"AFR\" (air fuel ratio), \"SFC\" (specific fuel consumption)," +
                " \"VE\"(volumetric efficiency), \"BMEP\"(brake mean effective pressure), " +
                " TE\"(thermal efficiency), and \"quit\".");

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
                        case "TL":
                            System.out.println("What is the diameter, in "
                            + "inches?");
                            d = in.nextDouble();
                            System.out.println("How long is the bolt, in " 
                            + "inches?");
                            b = in.nextDouble();
                            System.out.println("The thread is " + 
                            calcTL(d, b > 6) + " inches long.");
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "SA":
                            System.out.println("What is the diameter, in "
                            + "inches?");
                            d = in.nextDouble();
                            System.out.println("The stress area is " + 
                            calcSA(d) + " inches.");
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "TF":
                            System.out.println("What is the diameter, in "
                            + "inches?");
                            d = in.nextDouble();
                            System.out.println("What is the grade of the " +
                            "fastener?");
                            g = in.nextInt();
                            System.out.println("The total force is " + 
                            calcTF(d, g) + " pounds.");
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "BHP":
                            System.out.println("What is the torque, in "
                            + "ft lbs?");
                            q = in.nextDouble();
                            System.out.println("What is the rpm?");
                            r = in.nextDouble();
                            System.out.println("The Brake Horsepower is "
                            + brakeHorsepower(q, r));
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "IHP":
                            System.out.println("What is the psi?");
                            psi = in.nextDouble();
                            System.out.println("What is the stroke in inches?");
                            S = in.nextDouble();
                            System.out.println("What is the bore in inches?");
                            B = in.nextDouble();
                            System.out.println("What is the rpm?");
                            r = in.nextDouble();
                            System.out.println("What is the number of cylinders?");
                            c = in.nextInt();
                            System.out.println("The Indicated Horsepower is "
                            + indicatedHorsepower(psi, S, B, r, c));
                            System.out.println("Press Ctrl + C to quit.\n");
                            break;
                        case "AFR":
                        System.out.println("What is the corrected air flow in lbs/hr?");
                        cfr = in.nextDouble();
                        System.out.println("What is the fuel flow in lbs/hr?");
                        ff = in.nextDouble();
                        System.out.println("The Air Fuel Ratio is " 
                          + AFR(cfr, ff)+":1");
                          System.out.println("Press Ctrl + C to quit.\n");
                          break;
                         case "SFC":
                         System.out.println("What is the fuel flow in lbs/hr?");
                         ff = in.nextDouble();
                         System.out.println("What is the brake horse power?");
                        bhp = in.nextDouble();
                        System.out.println("The specific fuel consumption is " 
                          + SFC(ff, bhp) +" lbs/hr");
                        System.out.println("Press Ctrl + C to quit.\n");
                          break;
                          case "VE":
                          System.out.println("What is the engine displacement in cu in.?");
                         cid = in.nextDouble();
                         System.out.println("What is the corrected air flow in lbs/hr?");
                         cfr = in.nextDouble();
                         System.out.println("What is rpm?");
                         r = in.nextDouble();
                         System.out.println("The volumetric efficiency is " 
                          + VE(cid, cfr, r) +"%");
                          System.out.println("Press Ctrl + C to quit.\n");
                          break;
                        case "BMEP":
                        System.out.println("What is the engine displacement in cu in.?");
                        cid = in.nextDouble();
                        System.out.println("What is the torque, in "
                        + "ft lbs?");
                        q = in.nextDouble();
                        System.out.println("The Brake mean effective pressure is " 
                          + BMEP(cid, q) +" psi");
                        System.out.println("Press Ctrl + C to quit.\n");
                          break;
                        case "TE":
                        System.out.println("What is the specific fuel consumption?");
                        sfc = in.nextDouble();
                        System.out.println("The thermal efficiency is " 
                        + TE(sfc) +"%");
                        System.out.println("Press Ctrl + C to quit.\n");
                        break;
                        case "QUIT":
                            counter = 100;
                            in.close();
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