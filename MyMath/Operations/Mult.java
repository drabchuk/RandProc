package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Mult implements Operation {
    Operation o1;
    Operation o2;

    public Mult() {
    }

    public Mult(Operation o1, Operation o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public Operation getO1() {
        return o1;
    }

    public Operation getO2() {
        return o2;
    }

    public Operation findOperation(Operation what) {
        if (o1 instanceof Mult) {
            Operation found = findOperation(what);
            if (found != null)
                return found;
        }
        if (o2 instanceof Mult) {
            Operation found = findOperation(what);
            if (found != null)
                return found;
        }
        return null;
    }

    public Operation findMultiplier(Var v) {
        if (o1 instanceof Mult) {
            Operation found = findMultiplier(v);
            if (found != null)
                return found;
        }
        if (o1 instanceof Pow && ((Pow) o1).getO1() instanceof Var && ((Pow) o1).getO1() == v) {
            return o1;
        }
        if (o2 instanceof Mult) {
            Operation found = findMultiplier(v);
            if (found != null)
                return found;
        }
        if (o2 instanceof Pow && ((Pow) o2).getO1() instanceof Var && ((Pow) o2).getO1() == v) {
            return o2;
        }
        return null;
    }

    @Override
    public double eval() {
        return o1.eval() * o2.eval();
    }

    @Override
    public Operation dif() {
        return new Sum(new Mult(o1, o2.dif()), new Mult(o2, o1.dif()));
    }

    @Override
    public String toString() {
        String s1 = (o1 instanceof Var) || (o1 instanceof Const) || (o1 instanceof Dif) || (o1 instanceof Mult) ? o1.toString() : "(" + o1.toString() + ")";
        String s2 = (o2 instanceof Var) || (o2 instanceof Const) || (o2 instanceof Dif) || (o2 instanceof Mult) ? o2.toString() : "(" + o2.toString() + ")";
        return s1 + " * " + s2;
    }

    @Override
    public Operation copy() {
        return new Mult(o1.copy(), o2.copy());
    }

    @Override
    public Operation simplify() {
        o1 = o1.simplify();
        o2 = o2.simplify();
        if (o1 instanceof Const && o1.eval() == 0 || o2 instanceof Const && o2.eval() == 0) {
            return new Const(0);
        }
        if (o1 instanceof Const && o2 instanceof Const) {
            return new Const(o1.eval() * o2.eval());
        }
        if (o1 instanceof Var && o1 instanceof Var && o1 == o2) {
            return new Pow(o1, new Const(2));
        }
        if (o1 instanceof Pow && o2 instanceof Var && o2 == ((Pow) o1).getO1()) {
            return new Pow(o1, new Sum(((Pow) o1).getO2(), new Const(1)));
        }
        if (o2 instanceof Pow && o1 instanceof Var && o1 == ((Pow) o2).getO1()) {
            return new Pow(o2, new Sum(((Pow) o2).getO2(), new Const(1)));
        }
        if (o2 instanceof Pow && o1 instanceof Pow && ((Pow) o1).getO1() == ((Pow) o2).getO1()) {
            return new Pow(o2, new Sum(((Pow) o2).getO2(), ((Pow) o1).getO2()));
        }
        if (o1 instanceof Div) {
            return new Div(new Mult(o2, ((Div) o1).getO1()), ((Div) o1).getO2());
        }
        if (o2 instanceof Div) {
            return new Div(new Mult(o1, ((Div) o2).getO1()), ((Div) o2).getO2());
        }
        return this;
    }

    @Override
    public Operation toStand() {
        o1 = o1.toStand();
        o2 = o2.toStand();
        if (o1 instanceof Const && o1.eval() == 0 || o2 instanceof Const && o2.eval() == 0) {
            return new Const(0);
        }
        if (o1 instanceof Const && o2 instanceof Const) {
            return new Const(o1.eval() * o2.eval());
        }
        if (o1 instanceof Var && o1 instanceof Var && o1 == o2) {
            return new Pow(o1, new Const(2));
        }
        if (o1 instanceof Pow && o2 instanceof Var && o2 == ((Pow) o1).getO1()) {
            return new Pow(o1, new Sum(((Pow) o1).getO2(), new Const(1)));
        }
        if (o2 instanceof Pow && o1 instanceof Var && o1 == ((Pow) o2).getO1()) {
            return new Pow(o2, new Sum(((Pow) o2).getO2(), new Const(1)));
        }
        if (o2 instanceof Pow && o1 instanceof Pow && ((Pow) o1).getO1() == ((Pow) o2).getO1()) {
            return new Pow(((Pow) o1).getO1(), new Sum(((Pow) o2).getO2(), ((Pow) o1).getO2()));
        }
        if (o1 instanceof Sum) {
            if (o2 instanceof Sum) {
                //(_+_)*(_+_)
                Operation a = ((Sum) o1).getO1();
                Operation b = ((Sum) o1).getO2();
                Operation c = ((Sum) o2).getO1();
                Operation d = ((Sum) o2).getO2();
                Operation ac = new Mult(a, b);
                Operation ad = new Mult(a, d);
                Operation bc = new Mult(b, c);
                Operation bd = new Mult(b, d);
                return new Sum(new Sum(ac, ad), new Sum(bc, bd));
            } else {
                //(a+b)*(c);
                Operation a = ((Sum) o1).getO1();
                Operation b = ((Sum) o1).getO2();
                Operation ac = new Mult(a, o2);
                Operation bc = new Mult(b, o2);
                return new Sum(ac, bc);
            }
        } else {
            if (o2 instanceof Sum) {
                //(a)*(b+c)
                Operation b = ((Sum) o2).getO1();
                Operation c = ((Sum) o2).getO2();
                Operation ab = new Mult(o1, b);
                Operation ac = new Mult(o1, c);
                return new Sum(ab, ac);
            } else {
                //a*b
                return this;
            }
        }
    }
}
