package MyMath.Probablilistic.StochasticProcesses;

import MyMath.Linear.Vector;

import java.util.Random;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public class RandomVariable {
    int dim;
    public int length;
    Vector[] vectors;

    public RandomVariable(int dim, int length) {
        this.dim = dim;
        this.length = length;
        Random rand = new Random();
        vectors = new Vector[length];
        double[] vector_dump = new double[dim];
        int counter = 0;
        for (int i = 0; i < length; i++) {
            vector_dump = new double[dim];
            for (int j = 0; j < dim; j++) {
                vector_dump[j] = rand.nextDouble();
            }
            vectors[i] = new Vector(vector_dump);
            counter++;
            if(counter > 10000) {
                counter = 0;
                rand = new Random();
            }
        }
    }

    public boolean isEvenDistribution(double equity, int freedom_degree) {
        double hiSqr = calculateHiSquared(freedom_degree);
        double quantile = hiSquaredQuantile(equity, freedom_degree);
        return hiSqr < quantile;
    }

    public double getX(int i) {
        return vectors[i].get(0);
    }

    public double calculateHiSquared(int freedom_degree) {
        int size = (int) Math.pow(freedom_degree, dim);
        int[] frequencies = new int[size];
        for (int i = 0; i < size; i++) {frequencies[i] = 0;}
        for (int i = 0; i < length; i++) {inc(frequencies, vectors[i], freedom_degree);}
        double theoretical_frequency = (double) length/Math.pow(freedom_degree, dim);
        double adder = 0;
        for (int i = 0; i < size; i++) {
            adder += Math.pow((frequencies[i] - theoretical_frequency),2)/theoretical_frequency;
            System.out.println(frequencies[i]);
        }
        adder = adder/length;
        System.out.println("Hi squared = " + adder);
        return adder;
    }

    private void inc(int[] frequencies, Vector v, int freedom_degree) {
        int index = 0;
        int scale = 1;
        for (int i = 0; i < v.getDim(); i++) {
            index += (((v.get(i) * freedom_degree)) / 1)*scale;
            scale *= freedom_degree;
        }
        try {
            frequencies[index]++;
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    public double hiSquaredQuantile(double alfa, int freedom_degree) {
        double quantile;
        double d;
        double A, B, C, D, E;
        if(alfa >= 0.5) {
            d = 2.0637*Math.pow(Math.log(1/(1 - alfa) - 0.16), 0.4274) - 1.5774;
        } else {
            d = -2.0637*Math.pow(Math.log(1/(alfa) - 0.16), 0.4274) + 1.5774;
        }
        A = d*Math.sqrt(2);
        B = (2.0/3.0)*(d*d - 1.0);
        C = d * (d*d - 7.0)/(9*Math.sqrt(2));
        D = (6*d*d + 14*d*d - 32)/(405);
        E = d*(9*d*d*d*d + 256*d*d - 433)/(4860*Math.sqrt(2));
        double nSqrt = Math.sqrt(freedom_degree);
        double res =  freedom_degree + A*nSqrt + B + C/nSqrt + D/freedom_degree + E/(freedom_degree*nSqrt);
        System.out.println("quantile = " + res);
        return res;
    }
}
