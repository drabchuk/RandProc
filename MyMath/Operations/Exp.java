package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Exp implements Operation {
    Operation operation;

    public Exp() {
    }

    public Exp(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double eval() {
        return Math.exp(operation.eval());
    }

    @Override
    public Operation dif() {
        return new Mult(new Exp(operation), operation.dif());
    }

    @Override
    public String toString() {
        return "exp(" + operation.toString()+ ")";
    }

    @Override
    public Operation copy() {
        return new Exp(operation.copy());
    }

    @Override
    public Operation simplify() {
        operation = operation.simplify();
        if (operation instanceof Const) {
            return new Const(Math.exp(operation.eval()));
        }
        return this;
    }

    @Override
    public Operation toStand() {
        operation = operation.toStand();
        if (operation instanceof Const) {
            return new Const(Math.exp(operation.eval()));
        }
        return this;
    }
}
