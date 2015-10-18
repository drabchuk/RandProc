import MyMath.Function;
import MyMath.Integral;
import MyMath.Probablilistic.StochasticProcesses.RandomVariable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter function");
        Scanner scanner = new Scanner(System.in);
        String funInt = scanner.nextLine();
        Function f = new Function(funInt);
        System.out.println("f = " + f.toString());
        f.toStand();
        System.out.println(f.toString());
        Function df = f.differentiate();
        //System.out.println(df.toString());
        df.toStand();
        System.out.println("df = " + df.toString());
        Function d2f = df.differentiate();
        //System.out.println(df.toString());
        for (int i = 0; i < 3; i++) {
            d2f.simplify();
            //System.out.println(df.toString());
        }
        System.out.println("d2f = " + d2f.toString());
        /*df = df.differentiate();
        System.out.println(df.toString());
        f.setVariable("x", 1.0);
        System.out.println(f.eval());*/


        /*System.out.println("Enter size of random variable");
        int size1 = scanner.nextInt();
        RandomVariable randomVariable = new RandomVariable(1, size1);
        System.out.println("Enter accuracy");
        double accuracy = scanner.nextDouble();
        System.out.println("Enter numbers of parts");
        int parts = scanner.nextInt();
        boolean result = randomVariable.isEvenDistribution(accuracy, parts);
        System.out.println("Random variable created.");
        System.out.println("is even " + result);
        System.out.println();
        System.out.println("Enter down bound:");
        double a = scanner.nextDouble();
        System.out.print("Enter upper bound:");
        double b = scanner.nextDouble();
        System.out.print("int(from "+a+" to "+b+")" );
        System.out.print(f.toString() + " = ");
        System.out.print(Integral.byMonteCarlo(f, a, b, randomVariable));*/
        //System.out.println(randomVariable.hiSquaredQuantile(0.05, 4));
        //System.out.println(randomVariable.calculateHiSquared(2));


    }
}
