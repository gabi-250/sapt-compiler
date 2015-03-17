import expressions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sam on 16/03/2015.
 */
public class Parser {

    private static Pattern patternIdentifier = Pattern.compile("[a-zA-Z_][a-zA-Z_0-9]*");
    private static Pattern patternInteger = Pattern.compile("[0-9]+");
    private static int i = -1;
    private static char[] whitespaceCharacters = {' ', '\t', '\n', '\r'};
    private static char[] opCharacters = {'+', '-', '<', '>'};

    private static int skip_ws(String s) {

        while (i < s.length()) {
            if (Arrays.asList(whitespaceCharacters).contains(s.charAt(i))) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    public static ArrayList<AbstractExpression> parse(String s) {
        i = 0;
        ArrayList<AbstractExpression> p = new ArrayList<AbstractExpression>();
        while (i < s.length()) {
            i = skip_ws(s);
            if (i == s.length()) {
                break;
            }
            AbstractExpression r = parse_stmt(s);
            if (r != null) {
                p.add(r);
                continue;
            }
            System.out.println("Unknown syntax at position" + i);
            System.out.println(s.substring(i, s.indexOf('\n', i)));
            System.exit(1);
        }
        return p;
    }

    private static AbstractExpression parse_stmt(String s) {
        AbstractExpression r = null;
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
        return r;
    }

    private static $assign parse_assign(String s) {
        Matcher m = patternIdentifier.matcher(s);
        if (!m.matches()) {
            return null;
        }
        String varn = m.group(0); //variable name
        i += varn.length();
        i = skip_ws(s);
        if ((i < s.length()) && (s.charAt(i) != '=')) {
            return null;
        }
        i += 1;
        i = skip_ws(s);
        AbstractExpression r = parse_expr(s);
        if (r == null) {
            return null;
        }
        i = skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != ';')) {
            return null;
        }
        i++;

        $assign a = new $assign(varn, r);
        return a;
    }

    private static AbstractExpression parse_expr(String s) {
        Matcher mInt = patternInteger.matcher(s);
        AbstractExpression lhs = null;
        if (mInt.matches()) {
            lhs = new $int(Integer.valueOf(mInt.group(0)));
            i += mInt.group(0).length();
        } else {
            Matcher mID = patternIdentifier.matcher(s.substring(i));
            if (!mID.matches()) {
                return null;
            }
            lhs = new $var(mID.group(0));
            i += mID.group(0).length();
        }
        i = skip_ws(s);
        if ((i == s.length()) || (!Arrays.asList(opCharacters).contains(s.charAt(i)))) {
            return lhs;
        }
        char op = s.charAt(i);
        i++;
        i = skip_ws(s);
        AbstractExpression rhs = parse_expr(s);
        if (rhs == null) {
            return null;
        }
        $bin_op b = new $bin_op(op, lhs, rhs);
        return b;
    }

    private static $while parse_while(String s) {
        $while w = null;
        if (!s.substring(i).startsWith("while")) {
            return null;
        }
        i += 5; //len("while")
        i = skip_ws(s);
        AbstractExpression cond = parse_expr(s);
        if (cond == null) {
            return null;
        }
        i = skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != '{')) {
            return null;
        }
        i++;
        ArrayList<AbstractExpression> body = new ArrayList<AbstractExpression>();
        while (i < s.length()) {
            i = skip_ws(s);
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
        w = new $while(cond, body);
        return w;
    }

    private static $print parse_print(String s) {
        $print p = null;
        if (!s.substring(i).startsWith("print")) {
            return null;
        }
        i += 5; // len("print")
        i = skip_ws(s);
        AbstractExpression exp = parse_expr(s);
        if (exp == null) {
            return null;
        }
        i = skip_ws(s);
        if ((i == s.length()) || (s.charAt(i) != ';')) {
            return null;
        }
        i++;
        p = new $print(exp);
        return p;
    }

    public static void main(String[] args) {
        String s = null;
        //read file > s
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
                s = sb.toString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("IO Exception!");
            System.exit(1);
        }
        //parse
        ArrayList<AbstractExpression> p = parse(s);
        for (AbstractExpression e : p) {
            System.out.println(e.pp());
        }
    }

}
