package MyMath.Operations;

/**
 * Created by ����� on 05.10.2015.
 */
public interface Operation {
    double eval();
    Operation dif();
    Operation copy();
    Operation simplify();
    Operation toStand();
}
