package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Pow implements Operation {
    Operation o1;
    Operation o2;

    public Pow() {
    }

    public Pow(Operation o1, Operation o2) {
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
        return Math.pow(o1.eval(), o2.eval());
    }

    @Override
    public Operation dif() {
        return new Mult(this, new Sum(new Mult(o2.dif(), new Ln(o1)), new Mult(new Div(o2,o1),o1.dif())));
    }

    @Override
    public String toString() {
        String s1 = (o1 instanceof Var)||(o1 instanceof Const) ||(o1 instanceof Sin) ? o1.toString() : "(" + o1.toString() + ")";
        String s2 = (o2 instanceof Var)||(o2 instanceof Const) ||(o2 instanceof Sin) ? o2.toString() : "(" + o2.toString() + ")";
        return s1 + "^" + s2;
    }

    @Override
    public Operation copy() {
        return new Pow(o1.copy(), o2.copy());
    }

    @Override
    public Operation simplify() {
        o1.simplify();
        o2.simplify();
        if(o1 instanceof Const && o2 instanceof Const) {
            return new Const(Math.pow(o1.eval(), o2.eval()));
        }
        return this;
    }

    @Override
    public Operation toStand() {
        o1 = o1.toStand();
        o2 = o2.toStand();
        if(o1 instanceof Const && o2 instanceof Const) {
            return new Const(Math.pow(o1.eval(), o2.eval()));
        }
        if(o2 instanceof Const && o2.eval() == 1) {
            return o1;
        }
        if(o2 instanceof Const && o2.eval() == 0) {
            return new Const(1);
        }
        if(o1 instanceof Pow) {
            //(a^b)^c = a^(b*c);
            return new Pow(((Pow) o1).getO1(), new Mult(((Pow) o1).getO2(), o2));
        }
        return this;
    }
}
