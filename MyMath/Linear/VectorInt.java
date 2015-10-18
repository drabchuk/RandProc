package MyMath.Linear;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public class VectorInt {
    private int[] x;

    public VectorInt(int... x) {
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

    public void set(int i, int val) {
        this.x[i] = val;
    }
}
