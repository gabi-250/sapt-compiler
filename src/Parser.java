import expressions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static Pattern patternIdentifier = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*");
    private static Pattern patternInteger = Pattern.compile("^[0-9]+");
    private static int i = -1;
    private static Character[] whitespaceCharacters = {' ', '\t', '\n', '\r'};
    private static Character[] opCharacters = {'+', '-', '<', '>'};

    private static void skip_ws(String s) {
        String substr = s.substring(i);
        int j = 0;
        while (j < substr.length()) {
            if (Arrays.asList(whitespaceCharacters).contains(substr.charAt(j))) {
                i++;
                j++;
            } else {
                break;
            }
        }
    }

    public static ArrayList<AbstractExpression> parse(String s) {
        i = 0;
        ArrayList<AbstractExpression> parseTree = new ArrayList<>();
        while (i < s.length()) {
            skip_ws(s);
            if (i == s.length()) {
                break;
            }
            AbstractExpression r = parse_stmt(s);
            if (r != null) {
                parseTree.add(r);
                continue;
            }
            System.out.println("Unknown syntax at position" + i);
            System.out.println(s.substring(i, s.indexOf('\n', i)));
            System.exit(1);
        }
        return parseTree;
    }

    private static AbstractExpression parse_stmt(String s) {
        AbstractExpression r;
        r = parse_assign(s);
        if (r != null) {
            return r;
        }
        r = parse_while(s);
        if (r != null) {
            return r;
        }
        r = parse_print(s);
        if (r != null) {
            return r;
        }
        return null;
    }

    private static Assignment parse_assign(String s) {
        Matcher m = patternIdentifier.matcher(s.substring(i));
        if (!m.find()) {
            return null;
        }
        String varn = m.group(0); //variable name
        i += varn.length();
        skip_ws(s);
        if ((i < s.length()) && (s.charAt(i) != '=')) {
            //did we think 'while'/'print' was a variable name by mistake? if so, move counter back
            if ((varn.equals("while")) || (varn.equals("print"))) {
                i -= 6;
            }
            return null;
        }
        i += 1;
        skip_ws(s);
        AbstractExpression r = parse_expr(s);
        if (r == null) {
            return null;
        }
        skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != ';')) {
            return null;
        }
        i++;
        return new Assignment(varn, r);
    }

    private static AbstractExpression parse_expr(String s) {
        Matcher mInt = patternInteger.matcher(s.substring(i));
        AbstractExpression lhs;
        if (mInt.find()) {
            lhs = new Int(Integer.valueOf(mInt.group(0)));
            i += mInt.group(0).length();
        } else {
            Matcher mID = patternIdentifier.matcher(s.substring(i));
            if (!mID.find()) {
                return null;
            }
            lhs = new Variable(mID.group(0));
            i += mID.group(0).length();
        }
        skip_ws(s);
        if ((i == s.length()) || (!Arrays.asList(opCharacters).contains(s.charAt(i)))) {
            return lhs;
        }
        char op = s.charAt(i);
        i++;
        skip_ws(s);
        AbstractExpression rhs = parse_expr(s);
        if (rhs == null) {
            return null;
        }
        return new BinaryOP(op, lhs, rhs);
    }

    private static WhileLoop parse_while(String s) {
        WhileLoop w;
        if (!s.substring(i).startsWith("while")) {
            return null;
        }
        i += 5; //len("while")
        skip_ws(s);
        AbstractExpression cond = parse_expr(s);
        if (cond == null) {
            return null;
        }
        skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != '{')) {
            return null;
        }
        i++;
        ArrayList<AbstractExpression> body = new ArrayList<>();
        while (i < s.length()) {
            skip_ws(s);
            AbstractExpression r = parse_stmt(s);
            if (r != null) {
                body.add(r);
                continue;
            }
            if ((i == s.length()) || (s.charAt(i) != '}')) {
                return null;
            }
            break;
        }
        i++;
        w = new WhileLoop(cond, body);
        return w;
    }

    private static Print parse_print(String s) {
        Print p;
        if (!s.substring(i).startsWith("print")) {
            return null;
        }
        i += 5; // len("print")
        skip_ws(s);
        AbstractExpression exp = parse_expr(s);
        if (exp == null) {
            return null;
        }
        skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != ';')) {
            return null;
        }
        i++;
        p = new Print(exp);
        return p;
    }

    public static void main(String[] args)
    {
        if (args.length == 1) {
            StringBuilder sb = new StringBuilder();
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            for (AbstractExpression st : parse(sb.toString()))
                System.out.println(st.pp());
        }
    }
}
