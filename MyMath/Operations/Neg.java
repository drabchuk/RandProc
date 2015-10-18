package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Neg implements Operation {
    Operation operation;

    public Neg() {
    }

    public Neg(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public double eval() {
        return -operation.eval();
    }

    @Override
    public Operation dif() {
        return new Mult(new Const(-1), operation.dif());
    }

    @Override
    public String toString() {
        String s = (operation instanceof Var)||(operation instanceof Const) ? operation.toString() : "(" + operation.toString() + ")";
        return "-" + s;
    }

    @Override
    public Operation copy() {
        return new Neg(operation.copy());
    }

    @Override
    public Operation simplify() {
        operation.simplify();
        if (operation instanceof Const) {
            return new Const(-operation.eval());
        }
        return this;
    }

    @Override
    public Operation toStand() {
        operation = operation.toStand();
        if (operation instanceof Neg) {
            return ((Neg) operation).operation;
        }
        if (operation instanceof Const) {
            return new Const(-operation.eval());
        }
        return this;
    }
}
