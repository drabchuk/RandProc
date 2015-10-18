package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Dif implements Operation{
    Operation operation;

    public Dif() {
    }

    public Dif(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double eval() {
        return 0;
    }

    @Override
    public Operation dif() {
        //return operation.dif();
        return new Dif(this);
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation instanceof Var ? "d" + operation.toString() : "d(" + operation.toString() + ")";
    }

    @Override
    public Operation copy() {
        return new Dif(operation.copy());
    }

    @Override
    public Operation simplify() {
        operation.simplify();
        return this;
    }

    @Override
    public Operation toStand() {
        operation = operation.toStand();
        return this;
    }
}
