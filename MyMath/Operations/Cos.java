package MyMath.Operations;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public class Cos implements Operation {
    Operation o;

    public Cos() {
    }

    public Cos(Operation o) {
        this.o = o;
    }

    @Override
    public double eval() {
        return Math.cos(o.eval());
    }

    @Override
    public Operation dif() {
        return new Neg(new Mult(new Sin(o), o.dif()));
    }

    @Override
    public Operation copy() {
        return new Cos(o.copy());
    }

    @Override
    public String toString() {
        return "cos(" + o.toString() + ")";
    }

    @Override
    public Operation simplify() {
        o.simplify();
        if (o instanceof Const) {
            return new Const(o.eval());
        }
        return this;
    }

    @Override
    public Operation toStand() {
        o = o.toStand();
        return new Sin(new Sum(o, new Const(Math.PI/2)));
    }
}
