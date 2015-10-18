package MyMath;

import MyMath.Operations.*;
import com.sun.org.apache.bcel.internal.ExceptionConstants;
import com.sun.org.apache.xpath.internal.operations.Plus;
import javafx.beans.binding.DoubleExpression;

import java.io.IOException;
import java.text.ParseException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Δενθρ on 05.10.2015.
 */

public class Function {
    HashMap<String, Operation> standardOperationMap = buildStandardOperationMap();
    //final String[] STAND_OPER_NAMES = {"(", ")", "+", "-", "*", "/", "sin", "cos", "tg", "tan", "atg", "arctg", "arctan", "exp", "ln", "log", "^", };
    String interpretation;
    HashMap<String, Var> vars = new HashMap<String, Var>();
    Operation func;

    public Function(String interpretation) {
        try {
            this.interpretation = interpretation;
            Stack<Operation> varStack = new Stack<Operation>();
            Stack<String> operationsStack = new Stack<String>();

            String literal = "";
            boolean inLiteralStatus = false;

            String interpretationInCasts = "(" + interpretation + ")";
            char previousChar = '(';
            for (char c : interpretationInCasts.toCharArray()) {
                if (c == '(') {
                    if (inLiteralStatus) {
                        inLiteralStatus = false;
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                    }
                    operationsStack.add(String.valueOf(c));
                    literal = "";
                } else if (c == ')') {
                    if (inLiteralStatus) {
                        inLiteralStatus = false;
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                    }
                    String op;
                    try {
                        while (!(op = operationsStack.pop()).equals("(")) {
                            executeOperation(op, varStack, operationsStack);
                        }
                    } catch (EmptyStackException e) {
                        System.out.println("Empty stack");
                    }
                    literal = "";
                } else if (c == '+') {
                    String currentOperation = String.valueOf(c);
                    if (inLiteralStatus) {
                        if (inLiteralStatus) {
                            if (standardOperationMap.containsKey(literal)) {
                                operationsStack.push(literal);
                            } else if (vars.containsKey(literal)) {
                                varStack.push(vars.get(literal));
                            } else {
                                try {
                                    double con = Double.parseDouble(literal);
                                    varStack.push(new Const(con));
                                } catch (NumberFormatException nfe) {
                                    vars.put(literal, new Var(literal));
                                    varStack.push(vars.get(literal));
                                }
                            }
                            inLiteralStatus = false;
                        }
                    }
                    int currentPriority = priority(currentOperation);
                    String op;
                    while (priority(op = operationsStack.pop()) < currentPriority) {
                        executeOperation(op, varStack, operationsStack);
                    }
                    operationsStack.push(op);
                    operationsStack.push(currentOperation);
                    literal = "";
                } else if (c == '-') {
                    if (inLiteralStatus) {
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                        //executeOperation("-", varStack, operationsStack);
                    }
                    if (previousChar != '(') {
                        String currentOperation = "+";
                        int currentPriority = priority(currentOperation);
                        String op;
                        while (priority(op = operationsStack.pop()) < currentPriority) {
                            executeOperation(op, varStack, operationsStack);
                        }
                        //operationsStack.push(op);
                        operationsStack.push("+");
                        //operationsStack.push(String.valueOf(c));
                        operationsStack.push("neg");
                    } else {
                        operationsStack.push("neg");
                    }

                    literal = "";
                } else if (c == '*') {
                    String currentOperation = String.valueOf(c);
                    if (inLiteralStatus) {
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                    }
                    int currentPriority = priority(currentOperation);
                    String op;
                    while (priority(op = operationsStack.pop()) < currentPriority) {
                        executeOperation(op, varStack, operationsStack);
                    }
                    operationsStack.push(op);
                    operationsStack.push(currentOperation);
                    literal = "";
                } else if (c == '/') {
                    String currentOperation = String.valueOf(c);
                    if (inLiteralStatus) {
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                    }
                    int currentPriority = priority(currentOperation);
                    String op;
                    while (priority(op = operationsStack.pop()) < currentPriority) {
                        executeOperation(op, varStack, operationsStack);
                    }
                    operationsStack.push(op);
                    operationsStack.push(String.valueOf(c));
                    literal = "";
                } else if (c == '^') {
                    String currentOperation = String.valueOf(c);
                    if (inLiteralStatus) {
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                    }
                    int currentPriority = priority(currentOperation);
                    String op;
                    while (priority(op = operationsStack.pop()) < currentPriority) {
                        executeOperation(op, varStack, operationsStack);
                    }
                    operationsStack.push(op);
                    operationsStack.push(String.valueOf(c));
                    literal = "";
                } else if (c == 'd') {
                    if (inLiteralStatus) {
                        literal += "d";
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.push(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                    } else {
                        operationsStack.add(String.valueOf(c));
                        literal = "";
                    }
                } else if (c == ' ') {
                    if (inLiteralStatus) {
                        if (standardOperationMap.containsKey(literal)) {
                            operationsStack.add(literal);
                        } else if (vars.containsKey(literal)) {
                            varStack.push(vars.get(literal));
                        } else {
                            try {
                                double con = Double.parseDouble(literal);
                                varStack.push(new Const(con));
                            } catch (NumberFormatException nfe) {
                                vars.put(literal, new Var(literal));
                                varStack.push(vars.get(literal));
                            }
                        }
                        inLiteralStatus = false;
                    }
                    literal = "";
                } else {
                    literal += c;
                    inLiteralStatus = true;
                }
                previousChar = c;
            }
            System.out.println("Function was created");
            try {
                func = varStack.pop();
            } catch (EmptyStackException e) {
                func = new Var("null");
            }
        } catch (RuntimeException e) {
            System.out.println("invalid expression");
            interpretation = "";
            func = new Const(0);
        }
    }

    public Function(HashMap<String, Var> vars, Operation func) {
        this.vars = vars;
        this.func = func;
    }

    public Function(Operation func) {
        this.func = func;
    }

    public Function differentiate() {
        Operation copied_func = func.copy();
        /*Dif d_of_func = new Dif(func);
        Operation d_of_func_cloned = new Dif(new Const(0));
        d_of_func_cloned = d_of_func.copy();*/
        return new Function(vars, copied_func.dif());
    }

    private int priority(String s) {
        if (s.equals("-") || s.equals("d") || s.equals("ln") || s.equals("exp")|| s.equals("sin") || s.equals("cos")) return 1;
        else if (s.equals("^")) return 2;
        else if (s.equals("*") || s.equals("/")) return 3;
        else if (s.equals("(")) return 5;
        else return 4;
    }

    public void change_variable(String variable, String function) {
        Operation ch = new Function(function).func;
    }

    private void executeOperation(String op, Stack<Operation> varStack, Stack<String> operationsStack) {
        if (op.equals("+")) {
            /*String operation_will_destroyed;
            if (!operationsStack.empty()) {
                while (!(operation_will_destroyed = operationsStack.pop()).equals("(")) {
                    executeOperation(operation_will_destroyed, varStack, operationsStack);
                }
            }*/
            Operation arg1 = varStack.pop();
            Operation arg2 = varStack.pop();
            varStack.push(new Sum(arg2, arg1));
        } else if (op.equals("-")) {
            Operation arg = varStack.pop();
            varStack.push(new Neg(arg));
            //if (!operationsStack.peek().equals("(")) {
            operationsStack.push("+");
            //}
        } else if (op.equals("neg")) {
            Operation arg = varStack.pop();
            varStack.push(new Neg(arg));
        } else if (op.equals("*")) {
            Operation arg1 = varStack.pop();
            Operation arg2 = varStack.pop();
            varStack.push(new Mult(arg2, arg1));

        } else if (op.equals("/")) {
            Operation arg1 = varStack.pop();
            Operation arg2 = varStack.pop();
            varStack.push(new Div(arg2, arg1));

        } else if (op.equals("d")) {
            Operation arg = varStack.pop();
            varStack.push(new Dif(arg));

        } else if (op.equals("^")) {
            Operation arg1 = varStack.pop();
            Operation arg2 = varStack.pop();
            varStack.push(new Pow(arg2, arg1));
        } else if (op.equals("ln")) {
            Operation arg1 = varStack.pop();
            varStack.push(new Ln(arg1));
        } else if (op.equals("exp")) {
            Operation arg1 = varStack.pop();
            varStack.push(new Exp(arg1));
        } else if (op.equals("sin")) {
            Operation arg1 = varStack.pop();
            varStack.push(new Sin(arg1));
        } else if (op.equals("cos")) {
            Operation arg1 = varStack.pop();
            varStack.push(new Cos(arg1));
        }
    }

    private HashMap<String, Operation> buildStandardOperationMap() {
        HashMap<String, Operation> standardOperationMap = new HashMap<String, Operation>();
        standardOperationMap.put("+", new Sum());
        standardOperationMap.put("-", new Neg());
        standardOperationMap.put("*", new Mult());
        standardOperationMap.put("/", new Div());
        standardOperationMap.put("^", new Pow());
        standardOperationMap.put("sin", new Sin());
        standardOperationMap.put("cos", new Cos());
        //standardOperationMap.put("asin", new );
        //standardOperationMap.put("arcsin", new );
        //standardOperationMap.put("cos", new );
        //standardOperationMap.put("acos", new );
        //standardOperationMap.put("arccos", new );
        //standardOperationMap.put("tg", new );
        //standardOperationMap.put("tan", new );
        //standardOperationMap.put("atg", new );
        //standardOperationMap.put("atan", new );
        //standardOperationMap.put("arctg", new );
        //standardOperationMap.put("arctan", new );
        //standardOperationMap.put("ctg", new );
        //standardOperationMap.put("ctan", new );
        //standardOperationMap.put("actg", new );
        //standardOperationMap.put("arcctg", new );
        //standardOperationMap.put("arcctan", new );
        standardOperationMap.put("ln", new Ln());
        standardOperationMap.put("exp", new Exp());
        standardOperationMap.put("d", new Dif());
        return standardOperationMap;
    }

    public void setVariables(Map.Entry<String, Double>... variables) {
        for(Map.Entry variable: variables) {
            vars.get(variable.getKey()).setValue((Double) variable.getValue());
        }
    }

    public void setVariable(String name, Double value) {
        vars.get(name).setValue(value);
    }

    public double eval() {
        return func.eval();
    }

    public void simplify() {
        func = func.simplify();
    }

    public void toStand() {
        Operation stand = func;
        do {
            func = stand;
            stand = stand.toStand();
        } while(stand!=func);
    }

    @Override
    public String toString() {
        return func.toString();
    }
}
