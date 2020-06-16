import java.util.Scanner;
import java.util.InputMismatchException;

public class AT160ProblemSolver {
    private static final double constant = 0.7854;
    private static final double gear_constant = 0.2617;
    private static final double other_gear_constant = 0.00297;
    private static final double convertToCi = 16.387;
    private static final double thermal_constant = 42.4;
    private static final int[] tensile = {55000, 69000, 117500, 136500, 150000};
    private static final double[] specific_heat = {1.0, .434, .6, .116, .21, .12, .1, .095, .031};
    private static final double[] latent_heat = {970, 135, 425, 512, 397, 261};
    private static final double[] heat_values = {123000, 138450};
    private static final double[] linearExpansionF = {0.0000066, 0.0000127, 0.0000056, 0.0000092, 0.0000105, 0.0000099, 0.000046, 0.0000332};
    private static final double[] linearExpansionC = {0.0000119, 0.000023, 0.0000102, 0.0000167, 0.000019, 0.000018, 0.000083, 0.0000599};
    private static final double[] volumetricExpansion = {0.00045, 0.0004};
    




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

    public static double FHP( double ihp, double bhp){
        return ihp-bhp;
    }

    public static double ME( double bhp, double ihp){
        return (bhp / ihp) * 100;
    }

    public static double saeHP( double B, double c){
        return ((B * B) * c) / 2.5;
    }

    public static double NL( double GL, double RR){
        return GL * RR;
    }

    public static double GL( double NL, double RR){
        return NL / RR;
    }

    public static double RR( double NL, double GL){
        return NL / GL;
    }

    public static double VD( double od, double cd){
        return od + 180 + cd;
    }

    public static double overlap( double iod, double ecd){
        return iod + ecd;
    }

    public static double heatCapacity( double w, double dt, int choice){
        return w * dt * specific_heat[choice];
    }

    public static double latentHeat(double w, int choice) {
        return w * latent_heat[choice];
    }

    public static double thermal_efficiency(double bhp, int choice, double gph) {
        return (bhp * thermal_constant) / (heat_values[choice] * (gph / 60)) * 100;
    }

    public static double linear_expansionF(double length, double dt, int choice) {
        return length * dt * linearExpansionF[choice];
    }

    public static double linear_expansionC(double length, double dt, int choice) {
        return length * dt * linearExpansionC[choice];
    }

    public static double superficial_expansionF(double area, double dt, int choice) {
        return area * dt * 2 * linearExpansionF[choice];
    }

    public static double superficial_expansionC(double area, double dt, int choice) {
        return area * dt * 2 * linearExpansionC[choice];
    }

    public static double volumetric_expansion(double volume, double dt, int choice) {
        return volume * dt * volumetricExpansion[choice];
    }

    public static double forceApplied(double f2, double d1, double d2) {
        return (f2 * d2) / d1;
    }

    public static double forceProduced(double f1, double d1, double d2) {
        return (f1 * d1) / d2;
    }

    public static double distanceToFulcrum(double f1, double f2, double d2) {
        return (f2 * d2) / f1;
    }

    public static double distanceFromFulcrum(double f1, double f2, double d1) {
        return (f1 * d1) / f2;
    }
    
    public static double pressure(double force, double diameter) {
        return force / (constant * diameter * diameter);
    }

    public static double force(double pressure, double diameter) {
        return pressure * constant * diameter * diameter;
    }

    public static double area(double force, double pressure) {
        return force / pressure;
    }

    public static double diameter(double area) {
        return Math.sqrt(area / constant);
    }

    public static double mechanicalAdvantage(double applied, double resulting){
        return resulting / applied;
    }

    public static double initialVolume(double v2, double t1, double t2) {
        return (v2 * (t1 + 273)) / (t2 + 273);
    }

    public static double finalVolume(double v1, double t1, double t2) {
        return (v1 * (t2 + 273)) / (t1 + 273);
    }

    public static double initialTemp(double v1, double v2, double t2) {
        return ((v1 * (t2 + 273)) / v2) - 273;
    }

    public static double finalTemp(double v1, double v2, double t1) {
        return ((v2 * (t1 + 273)) / v1) - 273;
    }

    public static double initialPressure(double p2, double v1, double v2) {
        return (((p2 + 14.7) * v2) / v1) - 14.7;
    }

    public static double finalPressure(double p1, double v1, double v2) {
        return (((p1 + 14.7) * v1) / v2) - 14.7;
    }

    public static double initialVolumeP(double p1, double p2, double v2) {
        return (((p2 + 14.7) * v2) / (p1 + 14.7));
    }

    public static double finalVolumeP(double p1, double p2, double v1) {
        return (((p1 + 14.7) * v1) / (p2 + 14.7));
    }

    public static double gasCompressionRatio(double p1, double p2) {
        return (p2 + 14.7) / (p1 + 14.7);
    }

    public static double combinedInitialPressure(double p2, double v1, 
    double v2, double t1, double t2) {
        return (((p2 + 14.7) * v2 * (t1 + 273)) / ((t2 + 273) * v1)) - 14.7;
    }

    public static double combinedFinalPressure(double p1, double v1, 
    double v2, double t1, double t2) {
        return (((p1 + 14.7) * v1 * (t2 + 273)) / ((t1 + 273) * v2)) - 14.7;
    }

    public static double combinedInitialVolume(double p1, double p2, 
    double v2, double t1, double t2) {
        return (((p2 + 14.7) * v2 * (t1 + 273)) / ((t2 + 273) * (p1 + 14.7)));
    }

    public static double combinedFinalVolume(double p1, double p2, 
    double v1, double t1, double t2) {
        return (((p1 + 14.7) * v1 * (t2 + 273)) / ((t1 + 273) * (p2 + 14.7)));
    }

    public static double combinedInitialTemp(double p1, double p2, 
    double v1, double v2, double t2) {
        return (((p1 + 14.7) * v1 * (t2 + 273)) / ((p2 + 14.7) * v2)) - 273;
    }

    public static double combinedFinalTemp(double p1, double p2, 
    double v1, double v2, double t1) {
        return (((p2 + 14.7) * v2 * (t1 + 273)) / ((p1 + 14.7) * v1)) - 273;
    }

    public static double driverDiameter(double d, double r, double R) {
        return (d * r) / R;
    }
    
    public static double drivenDiameter(double D, double r, double R) {
        return (D * R) / r;
    }

    public static double driverRPM(double D, double d, double r) {
        return (d * r) / D;
    }

    public static double drivenRPM(double D, double d, double R) {
        return (D * R) / d;
    }

    public static double peripheralVelocity(double diameter, double rpm) {
        return diameter * rpm * gear_constant;
    }

    public static double calcDiameter(double velocity, double rpm) {
        return velocity / (rpm * gear_constant);
    }

    public static double calcRPM(double velocity, double diameter) {
        return velocity / (diameter * gear_constant);
    }

    public static double driverTeeth(double t, double N, double n) {
        return (t * n) / N;
    }

    public static double drivenTeeth(double T, double N, double n) {
        return (T * N) / n;
    }

    public static double driverGear(double T, double t, double n) {
        return (t * n) / T;
    }

    public static double drivenGear(double T, double t, double N) {
        return (T * N) / t;
    }

    public static double gearRatioT(double T, double t) {
        return t / T;
    }

    public static double gearRatioRPM(double N, double n) {
        return N / n;
    }

    public static double mph (double rpm, double t, double dGR, double tGR) {
        return (rpm * t * other_gear_constant) / (dGR * tGR);
    }

    public static double rpm (double mph, double t, double dGR, double tGR) {
        return ((dGR * tGR) * mph) / (t * other_gear_constant);
    }

    public static double GR (double rpm, double mph, double t) {
        return (rpm * t * other_gear_constant) / mph;
    }

    public static double tireDiameter(double rpm, double mph, double dGR, 
    double tGR) {
        return ((dGR * tGR) * mph) / (rpm * other_gear_constant);
    }

    /*
    private static void printOptions() {
        String[][] table = new String[23][];
        table[0] = new String[] {" 1. CID (displacement)", 
        " 2. Compression Ratio", " 3. Thread Length"};
        table[1] = new String[] {"4. Stress Area", " 5. Total Force",
        " 6. Brake Horsepower "};
        table[2] = new String[] {" 7. Indicated Horsepower",
        " 8. Air-Fuel Ratio", " 9. Specific Fuel Consumption"};
        table[3] = new String[] {"1 0. Volumetric Efficiency", 
        " 11. Brake Mean Effective Pressure", " 12. Thermal Efficiency"};
        table[4] = new String[] {" 13. Friction Horsepower",
        " 14. Mechanical Efficiency", 
        " 15. Taxable or SAE Horsepower"};
        table[5] = new String[] {"16. Net Lift", " 17. Gross Lift", 
        " 18. Rocker Arm Ratio"};
        table[6] = new String[] {" 19. Valve Duration",
        "20. Overlap", " 21. Heat Capacity"};
        table[7] = new String[] {"22. Latent Heat of Vaporization",
        "23. Thermal Efficiency (Heat)",
        "24. Linear Expansion"};
        table[8] = new String[] {"25. Superficial Expansion",
        "26. Volumetric Expansion", "27. F1 (Force)"};
        table[9] = new String[] {"28. F2 (Force)", "29. D1 (Force)",
        "30. D2 (Force)"};
        table[10] = new String[] {"31. Pressure", "32. Force", 
        "33. Area"};
        table[11] = new String[] {"34. Diameter", 
        "35. Mechanical Advantage", "36. Initial Volume (with Temp)"};
        table[12] = new String[] {"37. Final Volume (with Temp)", 
        "38. Initial Temperature", "39. Final Temperature"};
        table[13] = new String[] {"40. Initial Pressure", 
        "41. Final Pressure", "42. Inital Volume (with Pressure)"};
        table[14] = new String[] {"43. Final Volume (with Pressure)", 
        "44. Compression Ratio (Gas)", 
        "45. Initial Pressure (Combined)"};
        table[15] = new String[] {"46. Final Pressure (Combined)", 
        "47. Initial Volume (Combined)", 
        "48. Final Volume (Combined)"};
        table[16] = new String[] {"49. Inital Temperature (Combined)", 
        "50. Final Temperature (Combined)", 
        "51. Driver Diameter"};
        table[17] = new String[] {"52. Driven Diameter", 
        "53. Driver RPM", "54. Driven RPM"};
        table[18] = new String[] {"55. Peripheral Speed", 
        "56. Pulley Diameter", "57. Pulley RPM"};
        table[19] = new String[] {"58. Gear Teeth (Driving)",
        "59. Gear Teeth (Driven)", "60. Gear RPM (Driving)"};
        table[20] = new String[] {"61. Gear RPM (Driven)", 
        "62. Gear Ratio (Teeth)", "63. Gear Ratio (RPM)"};
        table[21] = new String[] {"64. MPH", "65. Engine RPM",
        "66. Gear Ratio (Final)"};
        table[22] = new String[] {"67. Tire Diameter", "", ""};
        System.out.println("Your options are: \n");
        for (final Object[] row : table) {
            System.out.format("%-15s | %-15s | %-15s\n", row);
        }
    }
    */

    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    double DH;
    double CHV;
    double bore;
    double S;
    double d;
    double bolt;
    double q; 
    double r;
    double psi;
    double cfr;
    double ff;
    double bhp;
    double cid;
    double sfc;
    double ihp;
    double NL;
    double GL;
    double RR;
    double od;
    double cd;
    double iod;
    double ecd;
    double weight;
    double gallons_per_hr;
    double int_temp, fin_temp;
    double length, area, volume;
    double f1, f2, d1, d2;
    double force, diameter, pressure;
    double applied, resulting;
    double initialVolume, finalVolume, initialPressure, finalPressure;
    double initialTemp, finalTemp;
    double driverGear, drivenGear, driverRPM, drivenRPM, velocity; 
    double pulleyDiameter, pulleyRPM, mph, rpm, tireDiameter; 
    double dGR, tGR;
    boolean fahrenheit;
    int choice;
    int c;
    int g;
    int counter = 0;
    while (counter < 1) {
        try {
            System.out.println("What would you like to calculate?");
            System.out.println("Your options are: \n1. CID "
            +"(displacement)" + " | 2. Compression Ratio" + 
            "\n3. Thread Length" + " | 4. Stress Area" + "\n5. Total Force"+
            " | 6. Brake Horsepower " + "\n7. Indicated Horsepower" +
            " | 8. Air-Fuel Ratio"+ "\n9. Specific Fuel Consumption)" +
            " | 10. Volumetric Efficiency"+ 
            "\n11. Brake Mean Effective Pressure" +
            " | 12. Thermal Efficiency" + "\n13. Friction Horsepower" +
            " | 14. Mechanical Efficiency" + 
            "\n15. Taxable or SAE Horsepower" +
            " | 16. Net Lift" + "\n17. Gross Lift" + 
            " | 18. Rocker Arm Ratio" + "\n19. Valve Duration" +
            " | 20. Overlap" + "\n21. Heat Capacity" +
            " | 22. Latent Heat of Vaporization" + 
            "\n23. Thermal Efficiency (Heat)" +
            " | 24. Linear Expansion" + "\n25. Superficial Expansion" +
            " | 26. Volumetric Expansion" + "\n27. F1 (Force)" +
            " | 28. F2 (Force)" + "\n29. D1 (Force)" +
            " | 30. D2 (Force)" + "\n31. Pressure" + " | 32. Force" + 
            "\n33. Area" + " | 34. Diameter" + 
            "\n35. Mechanical Advantage" + " | 36. Initial Volume (with Temp)"+
            "\n37. Final Volume (with Temp)" + " | 38. Initial Temperature" + 
            "\n39. Final Temperature" + " | 40. Initial Pressure" + 
            "\n41. Final Pressure" + " | 42. Inital Volume (with Pressure)" +
            "\n43. Final Volume (with Pressure)" + 
            "| 44. Compression Ratio (Gas)" + 
            "\n45. Initial Pressure (Combined)" +
            " | 46. Final Pressure (Combined)" + 
            "\n47. Initial Volume (Combined)" + 
            " | 48. Final Volume (Combined)" + 
            "\n49. Inital Temperature (Combined)" + 
            " | 50. Final Temperature (Combined)" + 
            "\n51. Driver Diameter" + " | 52. Driven Diameter" + 
            "\n53. Driver RPM" + " | 54. Driven RPM" + 
            "\n55. Peripheral Speed" + " | 56. Pulley Diameter" + 
            "\n57. Pulley RPM" + " | 58. Gear Teeth (Driving)" +
            "\n59. Gear Teeth (Driven)" + " | 60. Gear RPM (Driving)" + 
            "\n61. Gear RPM (Driven)" + " | 62. Gear Ratio (Teeth)" +
            "\n63. Gear Ratio (RPM)" + " | 64. MPH" + "\n65. Engine RPM" +
            " | 66. Gear Ratio (Final)" + "\n67. Tire Diameter");
            String reply = in.nextLine();
            int toCalc = Integer.parseInt(reply);
            switch(toCalc) {
                    case 1:
                        System.out.println("What is the bore, in inches?");
                        bore = in.nextDouble();
                        System.out.println("What is the stroke, in" 
                        + " inches?");
                        S = in.nextDouble();
                        System.out.println("How many cylinders?");
                        c = in.nextInt();
                        System.out.println("The displacement is: " + 
                        calcCID(bore, S, c)+ " inches.");
                        break;
                    case 2: 
                        System.out.println("What is the deck height, in " +
                        "inches?");
                        DH = in.nextDouble();
                        System.out.println("What is the chamber volume, in"
                        + " cubic centimeters?");
                        CHV = in.nextDouble();
                        System.out.println("What is the bore, in inches?");
                        bore = in.nextDouble();
                        System.out.println("What is the stroke, in" 
                        + " inches?");
                        S = in.nextDouble();
                        System.out.println("The compression ratio is: " +
                        calcCR(DH, CHV, bore, S) + ":1");
                        break;
                    case 3:
                        System.out.println("What is the diameter, in "
                        + "inches?");
                        d = in.nextDouble();
                        System.out.println("How long is the bolt, in " 
                        + "inches?");
                        bolt = in.nextDouble();
                        System.out.println("The thread is " + 
                        calcTL(d, bolt > 6) + " inches long.");
                        break;
                    case 4:
                        System.out.println("What is the diameter, in "
                        + "inches?");
                        d = in.nextDouble();
                        System.out.println("The stress area is " + 
                        calcSA(d) + " inches.");
                        break;
                    case 5:
                        System.out.println("What is the diameter, in "
                        + "inches?");
                        d = in.nextDouble();
                        System.out.println("What is the grade of the " +
                        "fastener?");
                        g = in.nextInt();
                        System.out.println("The total force is " + 
                        calcTF(d, g) + " pounds.");
                        break;
                    case 6:
                        System.out.println("What is the torque, in "
                        + "ft lbs?");
                        q = in.nextDouble();
                        System.out.println("What is the rpm?");
                        r = in.nextDouble();
                        System.out.println("The Brake Horsepower is "
                        + brakeHorsepower(q, r));
                        break;
                    case 7:
                        System.out.println("What is the psi?");
                        psi = in.nextDouble();
                        System.out.println("What is the stroke in inches?");
                        S = in.nextDouble();
                        System.out.println("What is the bore in inches?");
                        bore = in.nextDouble();
                        System.out.println("What is the rpm?");
                        r = in.nextDouble();
                        System.out.println("What is the number of cylinders?");
                        c = in.nextInt();
                        System.out.println("The Indicated Horsepower is "
                        + indicatedHorsepower(psi, S, bore, r, c));
                        break;
                    case 8:
                        System.out.println("What is the corrected air flow in lbs/hr?");
                        cfr = in.nextDouble();
                        System.out.println("What is the fuel flow in lbs/hr?");
                        ff = in.nextDouble();
                        System.out.println("The Air Fuel Ratio is " 
                        + AFR(cfr, ff)+":1");
                        break;
                    case 9:
                        System.out.println("What is the fuel flow in lbs/hr?");
                        ff = in.nextDouble();
                        System.out.println("What is the brake horse power?");
                        bhp = in.nextDouble();
                        System.out.println("The specific fuel consumption is " 
                        + SFC(ff, bhp) +" lbs/hr");
                        break;
                    case 10:
                        System.out.println("What is the engine displacement in cu in.?");
                        cid = in.nextDouble();
                        System.out.println("What is the corrected air flow in lbs/hr?");
                        cfr = in.nextDouble();
                        System.out.println("What is rpm?");
                        r = in.nextDouble();
                        System.out.println("The volumetric efficiency is " 
                        + VE(cid, cfr, r) +"%");
                        break;
                    case 11:
                        System.out.println("What is the engine displacement in cu in.?");
                        cid = in.nextDouble();
                        System.out.println("What is the torque, in "
                        + "ft lbs?");
                        q = in.nextDouble();
                        System.out.println("The Brake mean effective pressure is " 
                        + BMEP(cid, q) +" psi");
                        break;
                    case 12:
                        System.out.println("What is the specific fuel consumption?");
                        sfc = in.nextDouble();
                        System.out.println("The thermal efficiency is " 
                        + TE(sfc) +"%");
                        break;
                    case 13:
                        System.out.println("What is the indicated horespower?");
                        ihp = in.nextDouble();
                        System.out.println("What is the brake horsepower?");
                        bhp = in.nextDouble();
                        System.out.println("The friction horsepower is " + 
                        FHP(ihp, bhp));
                        break;
                    case 14:
                        System.out.println("What is the brake horsepower?");
                        bhp = in.nextDouble();
                        System.out.println("What is the indicated horsepower?");
                        ihp = in.nextDouble();
                        System.out.println("The mechanical efficiency is " 
                        + ME(bhp, ihp) +"%");
                        break;
                    case 15:
                        System.out.println("What is the bore, in inches?");
                        bore = in.nextDouble();
                        System.out.println("How many cylinders?");
                        c = in.nextInt();
                        System.out.println("The SAE or Taxable horsepower is " 
                        + saeHP(bore, c) +" hp");
                        break;
                    case 16:
                        System.out.println("What is the gross lift?");
                        GL = in.nextDouble();
                        System.out.println("What is the rocker arm ratio?");
                        RR = in.nextDouble();
                        System.out.println("The net lift is " 
                        + NL(GL, RR) +" inches");
                        break;
                    case 17:
                        System.out.println("What is the net lift?");
                        NL = in.nextDouble();
                        System.out.println("What is the rocker arm ratio?");
                        RR = in.nextDouble();
                        System.out.println("The gross lift is " 
                        + GL(NL, RR) +" inches");
                        break;
                    case 18:
                        System.out.println("What is the net lift?");
                        NL = in.nextDouble();
                        System.out.println("What is the gross lift?");
                        GL = in.nextDouble();
                        System.out.println("The rocker arm ratio is " 
                        + RR(NL, GL) +" inches");
                        break;
                    case 19:
                        System.out.println("What is the opening degrees?");
                        od = in.nextDouble();
                        System.out.println("What is the closing degrees?");
                        cd = in.nextDouble();
                        System.out.println("The valve duration is " 
                        + VD(od, cd) +" degrees");
                        break;
                    case 20:
                        System.out.println("What is the intake opening degrees?");
                        iod = in.nextDouble();
                        System.out.println("What is the exhaust closing degrees?");
                        ecd = in.nextDouble();
                        System.out.println("The overlap is " 
                        + overlap(iod, ecd) +" degrees");
                        break;
                    case 21:
                        System.out.println("What is the weight in lbs?");
                        weight = in.nextDouble();
                        System.out.println("What was the initial temperature in degrees Fahrenheit?");
                        int_temp = in.nextDouble();
                        System.out.println("What is the final temperature in degrees Fahrenheit?");
                        fin_temp = in.nextDouble();
                        System.out.println("Which coolant was used? \n1. Water" +
                        "\n2. Gas\n3. Alcohol\n4. Steel\n5. Aluminum\n6. Cast Iron" +
                        "\n7. Copper\n8. Brass\n9. Lead");
                        choice = in.nextInt();
                        System.out.println("The heat capacity is " + 
                        heatCapacity(weight, fin_temp - int_temp, choice - 1)
                        + " BTU.");
                        break;
                    case 22:
                        System.out.println("What is the weight in lbs?");
                        weight = in.nextDouble();
                        System.out.println("What liquid is being boiled? \n1. Water" +
                        "\n2. Gas\n3. Alcohol\n4. Ethyl Alcohol\n5. Methanol\n6. Solvent");
                        choice = in.nextInt();
                        System.out.println("The latent heat of vaporization is " +
                        latentHeat(weight, choice - 1) + " BTU.");
                        break;
                    case 23: 
                        System.out.println("What is the Brake Horsepower?"); 
                        bhp = in.nextDouble();
                        System.out.println("What is the liquid used? \n1. Gas\n2. Diesel");
                        choice = in.nextInt();
                        System.out.println("What is the GPH (gallons per hour)?");
                        gallons_per_hr = in.nextDouble();
                        System.out.println("The thermal efficiency is " + 
                        thermal_efficiency(bhp, choice - 1, gallons_per_hr) + "%.");
                        break;
                    case 24:
                        System.out.println("What is the length?");
                        length = in.nextDouble();
                        System.out.println("1. Fahrenheit or 2. Celcius?");
                        fahrenheit = in.nextInt() == 1;
                        System.out.println("What was the initial temperature?");
                        int_temp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        fin_temp = in.nextDouble();
                        System.out.println("What is expanding? \n1. Steel" +
                        "\n2. Aluminum\n3. Cast Iron\n4. Copper\n5. Brass\n6. Bronze" +
                        "\n7. Glass\n8. Mercury");
                        choice = in.nextInt();
                        if (fahrenheit) {
                            System.out.println("The linear expansion is " +
                            linear_expansionF(length, fin_temp - int_temp, 
                            choice - 1) + " inches.");
                        }
                        else {
                            System.out.println("The linear expansion is " +
                            linear_expansionC(length, fin_temp - int_temp, 
                            choice - 1) + ".");
                        }
                        break;
                    case 25:
                        System.out.println("What is the area?");
                        area = in.nextDouble();
                        System.out.println("1. Fahrenheit or 2. Celcius?");
                        fahrenheit = in.nextInt() == 1;
                        System.out.println("What was the initial temperature?");
                        int_temp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        fin_temp = in.nextDouble();
                        System.out.println("What is expanding? \n1. Steel" +
                        "\n2. Aluminum\n3. Cast Iron\n4. Copper\n5. Brass\n6. Bronze" +
                        "\n7. Glass\n8. Mercury");
                        choice = in.nextInt();
                        if (fahrenheit) {
                            System.out.println("The volumetric expansion is " +
                            superficial_expansionF(area, fin_temp - int_temp, 
                            choice - 1) + " inches squared.");
                        }
                        else {
                            System.out.println("The volumetric expansion is " +
                            superficial_expansionC(area, fin_temp - int_temp, 
                            choice - 1) + ".");
                        }
                        break;
                    case 26:
                        System.out.println("What is the volume?");
                        volume = in.nextDouble();
                        System.out.println("What was the initial temperature in degrees Celcius?");
                        int_temp = in.nextDouble();
                        System.out.println("What is the final temperature in degrees Celcius?");
                        fin_temp = in.nextDouble();
                        System.out.println("What is expanding?\n1. Water" +
                        "\n2. ATF");
                        choice = in.nextInt();
                        System.out.println("The volumetric expansion is " +
                        volumetric_expansion(volume, fin_temp - int_temp, 
                        choice - 1) + ".");
                        break;     
                    case 27:
                        System.out.println("What is F2?");
                        f2 = in.nextDouble();
                        System.out.println("What is D1?");
                        d1 = in.nextDouble();
                        System.out.println("What is D2?");
                        d2 = in.nextDouble();
                        System.out.println("F1 = " + forceApplied(f2, d1, d2));
                        break;
                    case 28:
                        System.out.println("What is F1?");
                        f1 = in.nextDouble();
                        System.out.println("What is D1?");
                        d1 = in.nextDouble();
                        System.out.println("What is D2?");
                        d2 = in.nextDouble();
                        System.out.println("F2 = " + forceProduced(f1, d1, d2));
                        break;
                    case 29:
                        System.out.println("What is F1?");
                        f1 = in.nextDouble();
                        System.out.println("What is F2?");
                        f2 = in.nextDouble();
                        System.out.println("What is D2?");
                        d2 = in.nextDouble();
                        System.out.println("D1 = " + distanceToFulcrum(f1, 
                        f2, d2));
                        break;
                    case 30:
                        System.out.println("What is F1?");
                        f1 = in.nextDouble();
                        System.out.println("What is F2?");
                        f2 = in.nextDouble();
                        System.out.println("What is D1?");
                        d1 = in.nextDouble();
                        System.out.println("D2 = " + distanceFromFulcrum(f1, 
                        f2, d1));
                        break;
                    case 31:
                        System.out.println("What is the force?");
                        force = in.nextDouble();
                        System.out.println("What is the diameter?");
                        diameter = in.nextDouble();
                        System.out.println("The pressure is " + pressure(force, 
                        diameter));
                        break;
                    case 32:
                        System.out.println("What is the pressure?");
                        pressure = in.nextDouble();
                        System.out.println("What is the diameter?");
                        diameter = in.nextDouble();
                        System.out.println("The force is " + force(pressure, 
                        diameter));
                        break;
                    case 33: 
                        System.out.println("What is the force?");
                        force = in.nextDouble();
                        System.out.println("What is the pressure?");
                        pressure = in.nextDouble();
                        System.out.println("The area is " + area(force, 
                        pressure));
                        break;
                    case 34:
                        System.out.println("What is the area?");
                        area = in.nextDouble();
                        System.out.println("The diameter is " + diameter(area));
                        break;
                    case 35:
                        System.out.println("What is the force applied?");
                        applied = in.nextDouble();
                        System.out.println("What is the resulting force?");
                        resulting = in.nextDouble();
                        System.out.println("The mechanical advantage is " + 
                        mechanicalAdvantage(applied, resulting) + ":1");
                        break;
                    case 36:
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The initial volume is " + 
                        initialVolume(finalVolume, initialTemp, finalTemp)); 
                        break;
                    case 37:
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The final volume is " + 
                        finalVolume(initialVolume, initialTemp, finalTemp)); 
                        break;
                    case 38:
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The initial temperature is " + 
                        initialTemp(initialVolume, finalVolume, finalTemp)); 
                        break;
                    case 39:
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("The final temperature is " + 
                        finalTemp(initialVolume, finalVolume, initialTemp)); 
                        break;
                    case 40:
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("The initial pressure is " + 
                        initialPressure(finalPressure, initialVolume, 
                        finalVolume)); 
                        break;
                    case 41:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("The final pressure is " + 
                        finalPressure(initialPressure, initialVolume, 
                        finalVolume)); 
                        break;
                    case 42:
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("The initial volume is " + 
                        initialVolumeP(initialPressure,
                        finalPressure, finalVolume)); 
                        break;
                    case 43:
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("The final volume is " + 
                        finalVolumeP(initialPressure,
                        finalPressure, initialVolume)); 
                        break;
                    case 44:
                        System.out.println("What is the beginning pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("The compresstion ratio is " +
                        gasCompressionRatio(initialPressure, finalPressure) +
                        ":1");
                        break;
                    case 45:
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The initial pressure is " + 
                        combinedInitialPressure(finalPressure, initialVolume, 
                        finalVolume, initialTemp, finalTemp));
                        break;
                    case 46:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The final pressure is " + 
                        combinedFinalPressure(initialPressure, initialVolume, 
                        finalVolume, initialTemp, finalTemp));
                        break;
                    case 47:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The initial volume is " + 
                        combinedInitialVolume(initialPressure, finalPressure, 
                        finalVolume, initialTemp, finalTemp));
                        break;
                    case 48:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The final volume is " + 
                        combinedFinalVolume(initialPressure, finalPressure, 
                        initialVolume, initialTemp, finalTemp));
                        break;
                    case 49:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the final temperature?");
                        finalTemp = in.nextDouble();
                        System.out.println("The initial temperature is " + 
                        combinedInitialTemp(initialPressure, finalPressure,
                        initialVolume, finalVolume, finalTemp));
                        break;
                    case 50:
                        System.out.println("What is the initial pressure?");
                        initialPressure = in.nextDouble();
                        System.out.println("What is the final pressure?");
                        finalPressure = in.nextDouble();
                        System.out.println("What is the initial volume?");
                        initialVolume = in.nextDouble();
                        System.out.println("What is the final volume?");
                        finalVolume = in.nextDouble();
                        System.out.println("What is the initial temperature?");
                        initialTemp = in.nextDouble();
                        System.out.println("The final temperature is " + 
                        combinedFinalTemp(initialPressure, finalPressure,
                        initialVolume, finalVolume, initialTemp));
                        break;
                    case 51:
                        System.out.println("What is the driven diameter?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driver diameter is " + 
                        driverDiameter(drivenGear, driverRPM, drivenRPM));
                        break;
                    case 52: 
                        System.out.println("What is the driver diameter?");
                        driverGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driven diameter is " + 
                        drivenDiameter(driverGear, driverRPM, drivenRPM));
                        break;
                    case 53: 
                        System.out.println("What is the driver diameter?");
                        driverGear = in.nextDouble();
                        System.out.println("What is the driven diameter?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driver RPM is " + 
                        driverRPM(driverGear, drivenGear, drivenRPM));
                        break;
                    case 54: 
                        System.out.println("What is the driver diameter?");
                        driverGear = in.nextDouble();
                        System.out.println("What is the driven diameter?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("The driven RPM is " + 
                        drivenRPM(driverGear, drivenGear, driverRPM));
                        break;
                    case 55:
                        System.out.println("What is the pulley diameter?");
                        pulleyDiameter = in.nextDouble();
                        System.out.println("What is the RPM of the pulley?");
                        pulleyRPM = in.nextDouble();
                        System.out.println("The peripheral velocity is " + 
                        peripheralVelocity(pulleyDiameter, pulleyRPM));
                        break;
                    case 56:
                        System.out.println("What is the peripheral velocity?");
                        velocity = in.nextDouble();
                        System.out.println("What is the RPM of the pulley?");
                        pulleyRPM = in.nextDouble();
                        System.out.println("The pulley diameter is " + 
                        calcDiameter(velocity, pulleyRPM));
                        break;
                    case 57:
                        System.out.println("What is the peripheral velocity?");
                        velocity = in.nextDouble();
                        System.out.println("What is the pulley diameter?");
                        pulleyDiameter = in.nextDouble();
                        System.out.println("The RPM of the pulley is " + 
                        calcRPM(velocity, pulleyDiameter));
                        break;
                    case 58:
                        System.out.println("How many teeth does the driven gear have?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driver gear has " + 
                        driverTeeth(drivenGear, driverRPM, drivenRPM) + 
                        " teeth");
                        break;
                    case 59:
                        System.out.println("How many teeth does the driver gear have?");
                        driverGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driven gear has " + 
                        drivenTeeth(driverGear, driverRPM, drivenRPM) + 
                        " teeth");
                        break;
                    case 60:
                        System.out.println("How many teeth does the driver gear have?");
                        driverGear = in.nextDouble();
                        System.out.println("How many teeth does the driven gear have?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The driver RPM is " +
                        driverGear(drivenGear, drivenGear, drivenRPM));
                        break;
                    case 61:
                        System.out.println("How many teeth does the driver gear have?");
                        driverGear = in.nextDouble();
                        System.out.println("How many teeth does the driven gear have?");
                        drivenGear = in.nextDouble();
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("The driven RPM is " +
                        drivenGear(drivenGear, drivenGear, driverRPM));
                        break;
                    case 62:
                        System.out.println("How many teeth does the driver gear have?");
                        driverGear = in.nextDouble();
                        System.out.println("How many teeth does the driven gear have?");
                        drivenGear = in.nextDouble();
                        System.out.println("The gear ratio is " + 
                        gearRatioT(driverGear, drivenGear) + ":1");
                        break;
                    case 63:
                        System.out.println("What is the driver RPM?");
                        driverRPM = in.nextDouble();
                        System.out.println("What is the driven RPM?");
                        drivenRPM = in.nextDouble();
                        System.out.println("The gear ratio is " + 
                        gearRatioRPM(driverRPM, drivenRPM) + ":1");
                        break;
                    case 64:
                        System.out.println("What is the RPM?");
                        rpm = in.nextDouble();
                        System.out.println("What is the tire diameter?");
                        tireDiameter = in.nextDouble();
                        System.out.println("What is the differential gear ratio?");
                        dGR = in.nextDouble();
                        System.out.println("What is the tranmission gear ratio?");
                        tGR = in.nextDouble();
                        System.out.println("The MPH is " + 
                        mph(rpm, tireDiameter, dGR, tGR));
                        break;
                    case 65:
                        System.out.println("What is the MPH?");
                        mph = in.nextDouble();
                        System.out.println("What is the tire diameter?");
                        tireDiameter = in.nextDouble();
                        System.out.println("What is the differential gear ratio?");
                        dGR = in.nextDouble();
                        System.out.println("What is the tranmission gear ratio?");
                        tGR = in.nextDouble();
                        System.out.println("The RPM is " + 
                        rpm(mph, tireDiameter, dGR, tGR));
                        break;
                    case 66:
                        System.out.println("What is the RPM?");
                        rpm = in.nextDouble();
                        System.out.println("What is the tire diameter?");
                        tireDiameter = in.nextDouble();
                        System.out.println("What is the MPH?");
                        mph = in.nextDouble();
                        System.out.println("The gear ratio is " + 
                        GR(rpm, mph, tireDiameter) + ":1");
                        break;
                    case 67:
                        System.out.println("What is the MPH?");
                        mph = in.nextDouble();
                        System.out.println("What is the RPM?");
                        rpm = in.nextDouble();
                        System.out.println("What is the differential gear ratio?");
                        dGR = in.nextDouble();
                        System.out.println("What is the tranmission gear ratio?");
                        tGR = in.nextDouble();
                        System.out.println("The tire diameter is" + 
                        tireDiameter(rpm, mph, dGR, tGR));
                        break;
            }
            in.nextLine();
            System.out.println("Would you like to continue? (Y/n)");
            String answer = in.nextLine();
            answer.toLowerCase();
            switch(answer) {
                case "n":
                case "no": 
                    in.close();
                    counter = 100;
                    break;  
                default: 
                    in.nextLine();
                    break;
            }

        }
        catch (InputMismatchException e) {
            System.out.println("\nYou put in something wrong. Try again." +
            "\n");
        }
        catch (Exception e) {
            System.out.println("\nWhoops, something went wrong. Please " +
            "notify me.");
            return;
        }

    }

    }
}