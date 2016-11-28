package expressions;


public class BinaryOP implements AbstractExpression {

    private char op;
    private String stringOp;
    private AbstractExpression lhs;
    private AbstractExpression rhs;

    public BinaryOP(char op, AbstractExpression lhs, AbstractExpression rhs) {
        this.op = op;
        this.stringOp = null;
        this.lhs = lhs;
        this.rhs = rhs;
    }

     public BinaryOP(String stringOp, AbstractExpression lhs, AbstractExpression rhs) {
        this.op = '\0';
        this.stringOp = stringOp;
        this.lhs = lhs;
        this.rhs = rhs;
    }


    public char getOp() {
        return op;
    }

    public AbstractExpression getLhs() {
        return lhs;
    }

    public AbstractExpression getRhs() {
        return rhs;
    }

    @Override
    public String pp() {
        if (stringOp != null) {
            return lhs.pp() + " " + stringOp + " " + rhs.pp();
        }
        return lhs.pp() + " " + op + " " + rhs.pp();
    }
}
