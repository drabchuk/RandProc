package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Sum implements Operation {
    Operation o1;
    Operation o2;

    public Sum() {
    }

    public Sum(Operation o1, Operation o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public Operation getO1() {
        return o1;
    }

    public Operation getO2() {
        return o2;
    }

    @Override
    public double eval() {
        return o1.eval() + o2.eval();
    }

    @Override
    public Operation dif() {
        return new Sum(o1.dif(), o2.dif());
    }

    @Override
    public String toString() {
        String s1 = o1.toString();
        String s2 = o2.toString();
        if (o2 instanceof Neg)
            return s1 + s2;
        else if (o1 instanceof Neg)
            return s2 + s1;
        else
            return s1 + " + " + s2;
    }

    @Override
    public Operation copy() {
        return new Sum(o1.copy(), o2.copy());
    }

    @Override
    public Operation simplify() {
        o1 = o1.simplify();
        o2 = o2.simplify();
        if (o1 instanceof  Const && o2 instanceof Const) {
            return new Const(o1.eval() + o2.eval());
        }
        if (o1 instanceof Const && o1.eval() == 0) {
            return o2;
        }
        if (o2 instanceof Const && o2.eval() == 0) {
            return o1;
        }
        else return this;
    }

    @Override
    public Operation toStand() {
        o1 = o1.toStand();
        o2 = o2.toStand();
        if (o1 instanceof  Const && o2 instanceof Const) {
            return new Const(o1.eval() + o2.eval());
        }
        if (o1 instanceof Const && o1.eval() == 0) {
            return o2;
        }
        if (o2 instanceof Const && o2.eval() == 0) {
            return o1;
        }
        return this;
    }
}
