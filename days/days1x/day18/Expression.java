package days.days1x.day18;

import java.util.*;
import java.util.function.LongBinaryOperator;

public class Expression {
    public static long eval(String expr, boolean addFirst) {
        // Add spaces to offset parens
        expr = expr.replace("(", "( ");
        expr = expr.replace(")", " )");

        // Split the expression into tokens by spaces
        List<String> ex = new ArrayList<>(Arrays.asList(expr.split("\\s+")));

        // Evaluate the expression, considering precidence and return the result
        return eval(ex, addFirst);
    }

    private static long eval(List<String> expr, boolean addFirst) {
        // System.out.println("Evaluating: " + expr);

        // replace any sub expressions in parentheses with the their computed value
        evalParens(expr, addFirst);

        // If precidence rules are in play, add first
        if (addFirst) {
            evalOp(expr, "+", (a,b) -> a+b);
        }
        
        // System.out.println("Reduced to: " + expr);

        long acc = 0;
        Operation op = Operation.NONE;
        
        // Once everything left is at the same precidence, simply evaluate left->right
        for (String token : expr) {
            // If the next token is an operator, switch to that operation
            if (token.equals("*")) {
                op = Operation.MULT;
            }
            else if (token.equals("+")) {
                op = Operation.ADD;
            }
            // If the next token is a number, perform the proper operation on it
            else if (token.matches("^\\d+$")) {
                int val = Integer.parseInt(token);
                switch (op) {
                    case NONE:
                        acc = val;
                        break;
                    case MULT:
                        acc = acc * val;
                        break;
                    case ADD:
                        acc = acc + val;
                        break;
                }
            }
            else {
                System.err.println("Unexpected Token: " + token);
            }
        }

        // System.out.println("Value: " + acc);
        return acc;
    }

    private static void evalParens(List<String> expr, boolean addFirst) {
        int pStart;
        // For any parentheticals, find where the parenthetical starts
        while((pStart = expr.indexOf("(")) >= 0) {
            // and where it ends
            int pEnd = findMatch(expr, pStart);

            // Extract parenthetical expression
            List<String> subExpr = new ArrayList<>(expr.subList(pStart + 1, pEnd));
            for (int i = pEnd; i > pStart; i--) {
                expr.remove(i);
            }

            // evaluate the contents of the parenthetical
            long val = eval(subExpr, addFirst);

            // replace the parenthetical with its evaluated value            
            expr.set(pStart, "" + val);
        }
    }

    private static void evalOp(List<String> expr, String op, LongBinaryOperator func) {
        int aPos;
        // For the requested operation, find where the operation is
        while ((aPos = expr.indexOf(op)) >= 0) {
            long lh = Long.parseLong(expr.get(aPos - 1));
            long rh = Long.parseLong(expr.get(aPos + 1));

            // perform the operation
            long val = func.applyAsLong(lh, rh);

            // Replace the operation and its operands with the evaluated value
            expr.remove(aPos);
            expr.remove(aPos);
            expr.set(aPos - 1, "" + val);
        }
    }

    private static int findMatch(List<String> expr, int pos) {
        int pCount = 1;

        // Count opening and closing parentheses to find the match to the opening at pos
        while (pCount > 0 && pos < expr.size()) {
            pos++;
            String token = expr.get(pos);
            if (token.equals(")")) pCount--;
            else if (token.equals("(")) pCount++;
        }

        // Return the location of the matching closing paren
        return pos;
    }

    // The possible operations
    private static enum Operation {
        MULT,
        ADD,
        NONE
    }
}