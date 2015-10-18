package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Ln implements Operation {
    Operation operation;

    public Ln() {
    }

    public Ln(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double eval() {
        return Math.log(operation.eval());
    }

    @Override
    public Operation dif() {
        return new Div(operation.dif(), operation);
    }

    @Override
    public Operation copy() {
        return new Ln(operation.copy());
    }

    @Override
    public String toString() {
        return "ln(" + operation.toString() + ")";
    }

    @Override
    public Operation simplify() {
        operation.simplify();
        if(operation instanceof Const) {
            return new Const(Math.log(operation.eval()));
        }
        return this;
    }

    @Override
    public Operation toStand() {
        operation = operation.toStand();
        if(operation instanceof Const) {
            return new Const(Math.log(operation.eval()));
        }
        return this;
    }
}
