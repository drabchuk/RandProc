package MyMath.Operations;

/**
 * Created by Δενθρ on 06.10.2015.
 */
public class Sin implements Operation {
    Operation o;

    public Sin() {
    }

    public Sin(Operation o) {
        this.o = o;
    }

    @Override
    public double eval() {
        return Math.sin(o.eval());
    }

    @Override
    public Operation dif() {
        return new Mult(new Cos(o), o.dif());
    }

    @Override
    public Operation copy() {
        return new Sin(o.copy());
    }

    @Override
    public String toString() {
        return "sin(" + o.toString() + ")";
    }

    @Override
    public Operation simplify() {
        o.simplify();
        if (o instanceof Const) {
            return new Const(Math.sin(o.eval()));
        }
        return this;
    }

    @Override
    public Operation toStand() {
        o = o.toStand();
        if (o instanceof Const) {
            return new Const(Math.sin(o.eval()));
        }
        if (o instanceof Sum) {
            //sin(_+_)
            Operation a = ((Sum) o).getO1();
            Operation b = ((Sum) o).getO2();
            if (a instanceof Const) {
                if (b instanceof Const)
                    //a&b - constants
                    return new Sin(new Const(a.eval() + b.eval()));
                else {
                    //a - const, b - !const
                    double phase = a.eval();
                    if (b instanceof Neg) {
                        //sin(const - b) = sin(b + (pi - const))
                        phase = Math.PI - phase;
                        phase /= Math.PI;
                        phase -= Math.floor(phase);
                        phase *= Math.PI;
                        return new Sin(new Sum(new Const(phase), ((Neg) b).getOperation()));
                    } else {
                        // a = a - 2*pi*n
                        phase /= Math.PI;
                        phase -= Math.floor(phase);
                        phase *= Math.PI;
                        return new Sin(new Sum(new Const(phase), b));
                    }
                    //return this;
                }
            } else if (b instanceof Const) {
                //a - !const  b - const
                double phase = b.eval();
                if (a instanceof Neg) {
                    //sin(const - b) = sin(b + (pi - const))
                    phase = Math.PI - phase;
                    phase /= Math.PI;
                    phase -= Math.floor(phase);
                    phase *= Math.PI;
                    return new Sin(new Sum(((Neg) a).getOperation(), new Const(phase)));
                } else {
                    // a = a - 2*pi*n
                    phase /= Math.PI;
                    phase -= Math.floor(phase);
                    phase *= Math.PI;
                    return new Sin(new Sum(a, new Const(phase)));
                }
        } else {
            //a - !const, b - !const
            //sin(a + b) = sin(a)sin(b + pi/2) + sin(a + pi/2)sin(b)
            return new Sum(new Mult(new Sin(a), new Sin(new Sum(b, new Const(Math.PI / 2)))), new Mult(new Sin(new Sum(a, new Const(Math.PI / 2))), new Sin(b)));
        }
    }// !sin(_+_)

    else if(o instanceof Neg)
            //sin(-a) = sin(a + pi)
            return new

    Sin(new Sum(((Neg)o

    ).operation,new

    Const(Math.PI)

    ));
    return this;
}
}
