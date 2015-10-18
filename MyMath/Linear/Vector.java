package MyMath.Linear;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public class Vector {
    private double[] x;

    public Vector(double... x) {
        this.x = x;
    }

    public int getDim() {
        return x.length;
    }

    public double get(int i) {
        try {
            return x[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public void set(int i, double val) {
        this.x[i] = val;
    }
}
