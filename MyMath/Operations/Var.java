package MyMath.Operations;

/**
 * Created by Δενθρ on 05.10.2015.
 */
public class Var implements Operation {
    double value;
    String name;

    public Var(String name) {
        this.name = name;
    }

    public Var() {
    }

    public Var(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public Var(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return value;
    }

    @Override
    public Operation dif() {
        return new Dif(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Operation copy() {
        return new Var(value, name);
    }

    @Override
    public Operation simplify() {
        return this;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Operation toStand() {
        return this;
    }
}
