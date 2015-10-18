package MyMath;

import MyMath.Operations.Mult;
import MyMath.Probablilistic.StochasticProcesses.RandomVariable;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public abstract class Integral {
    public static double byMonteCarlo(Function f, double a, double b, RandomVariable rv) {
        if (b == -8 && a == 0) {
            //Function change_x = new Function("1/x");
            //Function f2 = new Function("-("+f.interpretation+")/(x^2)");
            String ff = f.interpretation;
            ff = ff.replaceAll("x", "(1/x)");
            ff += "*(-1/(x^2))";
            Function f2 = new Function(ff);
            double adder = 0;
            for (int i = 0; i < rv.length; i++) {
                f.setVariable("x", rv.getX(i));
                adder += f.eval();
            }
            double expected = adder / (double) rv.length;
            double res = expected;

            adder = 0;
            for (int i = 0; i < rv.length; i++) {
                f2.setVariable("x", rv.getX(i));
                adder += f2.eval();
            }
            expected = adder / (double) rv.length;
            return res + expected;
        } else {
            double adder = 0;
            for (int i = 0; i < rv.length; i++) {
                f.setVariable("x", rv.getX(i) * (Math.abs(b - a) + Math.min(b, a)));
                adder += f.eval();
            }
            double expected = adder / (double) rv.length;
            return Math.abs(b - a) * expected;
        }
    }
}
