package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Const implements Operation {
    double val;

    public Const() {
    }

    public Const(double val) {
        this.val = val;
    }

    @Override
    public double eval() {
        return val;
    }

    @Override
    public Operation dif() {
        return new Const(0);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public Operation copy() {
        return new Const(val);
    }

    @Override
    public Operation simplify() {
        return this;
    }

    @Override
    public Operation toStand() {
        return this;
    }
}
