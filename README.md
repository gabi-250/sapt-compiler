# APT Parser
##Grammar
In BNF:
```
Stmt ::= Assign | While | Print
Assign ::= ID = Expr ;
Expr ::= INT + Expr | INT - Expr | INT < Expr | INT > Expr | INT <= Expr | INT == EXPR | INT >= Expr | INT
While ::= WHILE Expr { stmt* }
Print ::= PRINT Expr ;
```

##Parser Usage
Make sure to import the expression classes:
```java
import expressions.*;
```
Load your source code in to a `String` (for example, using a `FileReader` and `StringBuilder`), then invoke the parser as follows:
```java
ArrayList<AbstractExpression> p = Parser.parse(s);
```
If the parser ran without any errors, the `ArrayList` `p` will now contain a parsed set of expressions from your source code.
##Expressions
All expressions implement the interface *`AbstractExpression`*; which has one method `String pp()` returning the respective source code for that expression. Each expression also has a getter for each of its attributes.
###List of expressions
| Expression class        | Name           | Example  | Attributes
| ------------- |-------------| -----| ------------|
| `Variable`      | Variable | `i` | `name` |
| `Int`     | Integer      |  42 | `val` |
| `Assignment` | Assignment      | `i = 5` or `i = j + 5` | `name`,`exp` |
| `BinaryOP` | Binary Operation      | `i < 5` | `lhs`,`op`,`rhs` |
| `WhileLoop` | While loop      | `while i < 100 { i++ }` | `cond`,`body`|
| `Print` | Print out      | `print i` | `exp` |


##Example implementation
####ParserTester.java
```java
import expressions.AbstractExpression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParserTester {

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
        ArrayList<AbstractExpression> p = Parser.parse(s);
        //test
        System.out.printf("We parsed %d expressions:\n", p.size());
        for (AbstractExpression e : p) {
            System.out.println(e.pp() + "\n--");
        }
    }
}
```
Running **`java ParserTester.java p1.hll`** will produce the following output:
```
We parsed 3 expressions:
i = 0 + 2;
--
j = i + 1;
--
while i < 100{
  i = i - 1;
  j = j + 1 + 2;
  print i;
}
--
```
