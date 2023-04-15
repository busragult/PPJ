package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String[] readFile() {
        String[] temp = new String[100];
        int i = 0;
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                temp[i++] = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String[] data = new String[i];
        System.arraycopy(temp, 0, data, 0, i);
        return data;
    }

    public static void main(String[] args) {
        String[] input = readFile();
        String[][] constants = new String[input.length][2];
        int countConstants = 0;
        String[][] functions = new String[input.length][3];
        int countFunctions = 0;
        String[] command = input[input.length - 1].split(" ");

        for (int i = 0; i < command.length; i++) {
            if (command[i].charAt(0) == '(' && command[i].charAt(command[i].length() - 1) == ')') {
                command[i] = command[i].substring(1, command[i].length() - 1);
            } else if (command[i].charAt(0) == '(') {
                command[i] = command[i].substring(1);
            } else if (command[i].charAt(command[i].length() - 1) == ')') {
                command[i] = command[i].substring(0, command[i].length() - 1);
            }
        }

        // Constants and Functions
        for (int j = 0; j < input.length - 1; j++) {
            String s = input[j];
            String[] tokens = s.split(" ");

            if (tokens[1].equals("=")) {
                constants[countConstants][0] = tokens[0];

                constants[countConstants][1] = tokens[2];
                for (int i = 3; i < tokens.length; i++) {
                    constants[countConstants][1] = constants[countConstants][1] + " " + tokens[i];
                }

//                constants[countConstants][1] = "0";
                countConstants++;
            } else if (tokens[2].equals("=")) {
                functions[countFunctions][0] = tokens[0];
                functions[countFunctions][1] = tokens[1];
                functions[countFunctions][2] = tokens[3];
                for (int i = 4; i < tokens.length; i++) {
                    functions[countFunctions][2] = functions[countFunctions][2] + " " + tokens[i];
                }

                countFunctions++;
            }
        }

        // Create values for constant's int values
        int[] values = new int[countConstants];
        Arrays.fill(values, -1);
        // If constant have int value
        for (int i = 0; i < countConstants; i++) {
            if (constants[i][1].split(" ").length == 1) {
                try {
                    values[i] = Integer.parseInt(constants[i][1]);
                } catch (NumberFormatException e) {
                    for (int j = 0; j < countConstants; j++) {
                        if (constants[j][0].equals(constants[i][1])) {
                            if(values[j] != -1) {
                                values[i] = values[j];
                            }
                        }
                    }
                }
            }
        }

        // OPERATORS
        boolean func_flag, const_flag = false, repeat_flag = false;
        for (int i = 0; i < countConstants; i++) {
            String[] expression = constants[i][1].split(" ");
            if (expression.length != 1) {
                func_flag = false;
                for (String s : expression) {
                    if (s.equals("+") || s.equals("-") || s.equals("*")
                            || s.equals("/") || s.equals("`div`")) {
                        continue;
                    }
                    for (int k = 0; k < countFunctions; k++) {
                        if (s.equals(functions[k][0]) || s.equals("(" + functions[k][0]) || s.equals(functions[k][0] + ")")) {
                            func_flag = true;
                            break;
                        }
                    }
                    const_flag = false;
                    if (!func_flag) {
                        for (String value : expression) {
                            for (int l = 0; l < countConstants; l++) {
                                if (value.equals(constants[l][0]) || value.equals("(" + constants[l][0])
                                        || value.equals(constants[l][0] + ")")) {
                                    if (values[l] == -1) {
                                        const_flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                if (!const_flag && !func_flag) {
                    repeat_flag = true;
                    boolean parentheses;
                    int expression_length = expression.length;
                    do {
                        parentheses = false;
                        for (int k = 0; k < expression_length; k++) {
                            if (expression[k].charAt(0) == '(') {
                                parentheses = true;
                                break;
                            }
                        }
                        StringBuilder par = new StringBuilder();
                        if (parentheses) {
                            setParentheses(expression, expression_length, par);
                            String[] par_expression = par.toString().split(" ");

                            expression_length = operationBack(constants, countConstants, values, expression, expression_length, par_expression);
                        }
                    } while (parentheses);

                    expressionOperation(constants, countConstants, values, expression, expression_length);
                    values[i] = Integer.parseInt(expression[expression_length - 1]);
                }
            }
            // If something changed when we reach the end of constants, repeat the loop
            if (i + 1 == countConstants && repeat_flag) {
                i = -1;
                repeat_flag = false;
                // Change constants which have value
                for (int j = 0; j < values.length; j++) {
                    if (values[j] != -1) {
                        constants[j][1] = String.valueOf(values[j]);
                    }
                }
            }
        }

        // Change constants which have value
        for (int i = 0; i < values.length; i++) {
            if (values[i] != -1) {
                constants[i][1] = String.valueOf(values[i]);
            }
        }

        // FUNCTIONS
        for (int i = 0; i < countConstants; i++) {
            String[] expression = constants[i][1].split(" ");
            if (expression.length != 1) {
                boolean parentheses;
                int expression_length = expression.length;
                do { // parentheses
                    parentheses = false;
                    for (int k = 0; k < expression_length; k++) {
                        if (expression[k].charAt(0) == '(') {
                            parentheses = true;
                            break;
                        }
                    }

                    if (parentheses) {
                        StringBuilder par = new StringBuilder();
                        setParentheses(expression, expression_length, par);

                        String[] par_expression = par.toString().split(" ");
                        func_flag = false;
                        for (String s : par_expression) {
                            if (s.equals("+") || s.equals("-") || s.equals("*")
                                    || s.equals("/") || s.equals("`div`")) {
                                continue;
                            }
                            for (int k = 0; k < countFunctions; k++) {
                                if (s.equals(functions[k][0]) || s.equals("(" + functions[k][0])
                                        || s.equals(functions[k][0] + ")")) {
                                    func_flag = true;
                                    break;
                                }
                            }
                        }


                        if (func_flag) {
                            for (int j = 0; j < par_expression.length; j++) {
                                for (int k = 0; k < countFunctions; k++) {
                                    if (par_expression[j].equals(functions[k][0]) || par_expression[j].equals("(" + functions[k][0])) {
                                        func_flag = false;
                                        for (int l = 0; l < countFunctions; l++) {
                                            if (par_expression[j + 1].equals(functions[k][0]) || par_expression[j + 1].equals("(" + functions[k][0])
                                                    || par_expression[j + 1].equals(functions[k][0] + ")")) {
                                                func_flag = true;
                                                break;
                                            }
                                        }

                                        if (!func_flag) {
                                            for (int l = 0; l < countConstants; l++) {
                                                if (par_expression[j + 1].equals(constants[k][0]) || par_expression[j + 1].equals("(" + constants[k][0])
                                                        || par_expression[j + 1].equals(constants[k][0] + ")")) {
                                                    par_expression[j + 1] = String.valueOf(values[k]);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            expression_length = operationBack(constants, countConstants, values, expression, expression_length, par_expression);
                        }
                    }
                } while (parentheses);

                // without parentheses
                for (int j = 0; j < expression_length; j++) {
                    if (expression[j].equals("+") || expression[j].equals("-") || expression[j].equals("*")
                            || expression[j].equals("/") || expression[j].equals("`div`")) {
                        continue;
                    }
                    for (int k = 0; k < countFunctions; k++) {
                        if (expression[j].equals(functions[k][0]) || expression[j].equals("(" + functions[k][0])
                                || expression[j].equals(functions[k][0] + ")")) {
                            String cons = expression[j + 1];
                            String[] exp = functions[k][2].split(" ");
                            for (int l = 0; l < exp.length; l++) {
                                if (exp[l].equals(functions[k][1])) {
                                    exp[l] = cons;
                                }
                            }

                            expressionOperation(constants, countConstants, values, exp, exp.length);
                            expression[j] = exp[exp.length - 1];
                            int back = 1;
                            if (expression_length - (j + 2) >= 0)
                                System.arraycopy(expression, j + 2, expression, j + 2 - back, expression_length - (j + 2));
                            expression_length -= back;

                            for (int l = expression_length; l < expression.length; l++) {
                                expression[l] = "";
                            }
                        }
                    }
                }
                expressionOperation(constants, countConstants, values, expression, expression_length);
                values[i] = Integer.parseInt(expression[expression_length - 1]);
            }
        }

        int command_length = command.length;

        if (command_length == 4) {
            for (int i = 0; i < countConstants; i++) {
                if (command[command_length - 1].equals(constants[i][0])) {
                    command[command_length - 1] = String.valueOf(values[i]);
                }
            }
        }
        for (int i = 3; i < command_length; i++) {
            for (int k = 0; k < countFunctions; k++) {
                if (command[i].equals(functions[k][0])) {
                    try {
                        String cons = command[i + 1];
                        String[] exp = functions[k][2].split(" ");
                        int exp_length = exp.length;
                        setArgOfFunction(functions, k, cons, exp);

                        StringBuilder par = new StringBuilder();
                        for (int j = 0; j < exp.length; j++) {
                            if (exp[j].charAt(0) == '(') {
                                par.append(exp[j]);
                                for (int l = j + 1; l < exp.length; l++) {
                                    par.append(" ").append(exp[l]);
                                }
                            }
                        }

                        String[] par_expression2 = par.toString().split(" ");
                        par_expression2[0] = par_expression2[0].substring(1);
                        par_expression2[1] = par_expression2[1].substring(0, par_expression2[1].length() - 1);
                        for (int l = 0; l < countFunctions; l++) {
                            if (par_expression2[0].equals(functions[l][0]) || par_expression2[0].equals("(" + functions[l][0])
                                    || par_expression2[0].equals(functions[l][0] + ")")) {
                                String cons2 = par_expression2[1];
                                String[] exp2 = functions[l][2].split(" ");

                                setArgOfFunction(functions, l, cons2, exp2);

                                exp_length = operationBack(constants, countConstants, values, exp, exp_length, exp2);
                            }
                        }

                        for (int j = 0; j < countFunctions; j++) {
                            if (exp[0].equals(functions[j][0]) || exp[0].equals("(" + functions[j][0])
                                    || exp[0].equals(functions[j][0] + ")")) {
                                String cons2 = exp[1];
                                String[] exp2 = functions[j][2].split(" ");

                                setArgOfFunction(functions, j, cons2, exp2);

                                expressionOperation(constants, countConstants, values, exp2, exp2.length);

                                int back = 0;
                                for (int l = 0; l < exp_length; l++) {
                                    if (exp[l].equals(functions[j][0])) {
                                        exp[l] = exp2[exp2.length - 1];
                                        for (int m = l + 1; m < exp_length; m++) {
                                            back++;
                                            if (exp[m].charAt(exp[m].length() - 1) == ')') {
                                                break;
                                            }
                                        }
                                        exp_length = carryBack(exp, exp_length, back, l);
                                    }
                                }
                            }
                        }

                        if (exp_length == 1) {
                            command[3] = exp[0];
                            command_length = 4;
                            for (int j = command_length; j < command.length; j++) {
                                command[j] = "";
                            }
                        }
                    } catch (NumberFormatException e) {
                        for (int j = 0; j < countFunctions; j++) {
                            if (command[i + 1].equals(functions[j][0])) {
                                String cons = functions[j][1];
                                String[] func = functions[j][2].split(" ");

                                for (int l = 0; l < countFunctions; l++) {
                                    if (command[i].equals(functions[l][0])) {
                                        String cons2 = functions[l][1];
                                        String[] func2 = functions[l][2].split(" ");
                                        int func2_length = func2.length;

                                        for (int m = 0; m < func2_length; m++) {
                                            if (func2[m].contains(cons2)) {
                                                String[] temp = new String[func.length];
                                                if (temp.length >= 0) System.arraycopy(func, 0, temp, 0, temp.length);

                                                String value = "";
                                                if (func2[m + 1].charAt(func2[m + 1].length() - 1) == ')') {
                                                    if (func2[m + 1].charAt(func2[m + 1].length() - 2) == ')') {
                                                        value = func2[m + 1].substring(0, func2[m + 1].length() - 2);
                                                    } else {
                                                        value = func2[m + 1].substring(0, func2[m + 1].length() - 1);
                                                    }
                                                }

                                                for (int n = 0; n < temp.length; n++) {
                                                    if (temp[n].equals(cons)) {
                                                        temp[n] = value;
                                                    } else if (temp[n].equals(cons + ")")) {
                                                        temp[n] = value + ")";
                                                    }
                                                }

                                                expressionOperation(constants, countConstants, values, temp, temp.length);

                                                int back = 1;
                                                for (int n = 0; n < func2_length; n++) {
                                                    if (func2[n].contains(cons2)) {
                                                        if (func2[n].charAt(0) == '(' && func2[n].charAt(1) == '(') {
                                                            func2[n] = "(" + temp[temp.length - 1];
                                                        } else if (func2[n + 1].charAt(func2[n + 1].length() - 1) == ')' && func2[n + 1].charAt(func2[n + 1].length() - 2) == ')') {
                                                            func2[n] = temp[temp.length - 1] + ")";
                                                        } else {
                                                            func2[n] = temp[temp.length - 1];
                                                        }

                                                        func2_length = carryBack(func2, func2_length, back, n);

                                                        break;
                                                    }
                                                }
                                                func_flag = false;
                                                for (int n = 0; n < func2_length; n++) {
                                                    for (int o = 0; o < countFunctions; o++) {
                                                        if (func2[n].contains(cons2)) {
                                                            func_flag = true;
                                                            break;
                                                        }
                                                    }
                                                }

                                                if (!func_flag) {
                                                    StringBuilder par = new StringBuilder();
                                                    for (int n = 0; n < func2_length; n++) {
                                                        if (func2[n].charAt(0) == '(') {
                                                            par = new StringBuilder(func2[n]);
                                                            for (int o = n + 1; o < func2_length; o++) {
                                                                par.append(" ").append(func2[o]);
                                                                if (func2[o].charAt(func2[o].length() - 1) == ')') {
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    String[] par_expression = par.toString().split(" ");
                                                    expressionOperation(constants, countConstants, values, par_expression, par_expression.length);

                                                    back = 0;
                                                    func2_length = toBack(func2, func2_length, back, par_expression);

                                                    expressionOperation(constants, countConstants, values, func2, func2_length);
                                                    command[3] = func2[func2_length - 1];
                                                    command_length = 4;

                                                    for (int n = command_length; n < command.length; n++) {
                                                        command[n] = "";
                                                    }
                                                    System.out.println(command[3]);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        /*
        // main = print ((6 + 7)/((4 + 5)/(1 + 2)))
        // I didn't think without function and constant. I can check this with an if statement then I can make operations.
        if (countConstants == 0 && countFunctions == 0) { }
         */

        if (command_length == 4) {
            System.out.println(command[3]);
        }

        // Print all
        /*
        System.out.println("CONSTANTS");
        for (int i = 0; i < countConstants; i++) {
            System.out.println(constants[i][0] + " " + values[i]);
        }
        System.out.println("\nFUNCTIONS");
        for (int i = 0; i < countFunctions; i++) {
            System.out.println(functions[i][0] + " " + functions[i][1] + " " + functions[i][2]);
        }
         */
    }

    public static int carryBack(String[] func2, int func2_length, int back, int n) {
        if (func2_length - (n + 1 + back) >= 0)
            System.arraycopy(func2, n + 1 + back, func2, n + 1 + back - back, func2_length - (n + 1 + back));
        func2_length -= back;

        for (int o = func2_length; o < func2.length; o++) {
            func2[o] = "";
        }
        return func2_length;
    }

    public static void setArgOfFunction(String[][] functions, int k, String cons, String[] exp) {
        for (int l = 0; l < exp.length; l++) {
            if (exp[l].equals(functions[k][1])) {
                exp[l] = cons;
            } else if (exp[l].equals("(" + functions[k][1])) {
                exp[l] = "(" + cons;
            } else if (exp[l].equals(functions[k][1] + ")")) {
                exp[l] = cons + ")";
            }
        }
    }

    public static void setParentheses(String[] expression, int expression_length, StringBuilder par) {
        for (int k = 0; k < expression_length; k+=2) {
            if (expression[k].charAt(0) == '(') {
                par.append(expression[k]);
                for (int j = k + 1; j < expression_length; j++) {
                    par.append(" ");
                    par.append(expression[j]);
                    if (expression[j].charAt(expression[j].length() - 1) == ')') {
                        break;
                    }
                }
            }
        }
    }

    public static int toBack(String[] func2, int func2_length, int back, String[] par_expression) {
        for (int n = 0; n < func2_length; n++) {
            if (func2[n].charAt(0) == '(') {
                func2[n] = par_expression[par_expression.length - 1];
                for (int o = n + 1; o < func2_length; o++) {
                    back++;
                    if (func2[o].charAt(func2[o].length() - 1) == ')') {
                        break;
                    }
                }
                func2_length = carryBack(func2, func2_length, back, n);
            }
        }
        return func2_length;
    }

    public static int operationBack(String[][] constants, int countConstants, int[] values, String[] expression, int expression_length, String[] par_expression) {
        expressionOperation(constants, countConstants, values, par_expression, par_expression.length);
        int back = 0;
        expression_length = toBack(expression, expression_length, back, par_expression);
        return expression_length;
    }

    public static void expressionOperation(String[][] constants, int countConstants, int[] values, String[] expression, int length) {
        for (int k = 1; k < length; k+=2) {
            int val1 = -1, val2 = -1;
            for (int l = 0; l < countConstants; l++) {
                if (expression[k - 1].equals(constants[l][0]) || expression[k - 1].equals("(" + constants[l][0])
                        || expression[k - 1].equals(constants[l][0] + ")")) {
                    val1 = values[l];
                }
                if (expression[k + 1].equals(constants[l][0]) || expression[k + 1].equals("(" + constants[l][0])
                        || expression[k + 1].equals(constants[l][0] + ")")) {
                    val2 = values[l];
                }
            }
            if (val1 == -1) {
                if (expression[k - 1].charAt(0) == '(') {
                    val1 = Integer.parseInt(expression[k - 1].substring(1));
                } else if (expression[k - 1].charAt(expression[k - 1].length() - 1) == ')'){
                    val1 = Integer.parseInt(expression[k - 1].substring(0, expression[k - 1].length() - 1));
                } else {
                    val1 = Integer.parseInt(expression[k - 1]);
                }
            }
            if (val2 == -1) {
                if (expression[k + 1].charAt(0) == '(') {
                    val2 = Integer.parseInt(expression[k - 1].substring(1, expression[k + 1].length()));
                } else if (expression[k + 1].charAt(expression[k + 1].length() - 1) == ')'){
                    val2 = Integer.parseInt(expression[k + 1].substring(0, expression[k + 1].length() - 1));
                } else {
                    val2 = Integer.parseInt(expression[k + 1]);
                }
            }

            switch (expression[k]) {
                case "+":
                    expression[k + 1] = String.valueOf(val1 + val2);
                    break;
                case "-":
                    expression[k + 1] = String.valueOf(val1 - val2);
                    break;
                case "*":
                    expression[k + 1] = String.valueOf(val1 * val2);
                    break;
                case "/":
                    expression[k + 1] = String.valueOf(val1 / val2);
                    break;
                case "`div`":
                    expression[k + 1] = String.valueOf((float) val1 / val2);
                    break;
                default:
                    break;
            }
        }
    }
}
