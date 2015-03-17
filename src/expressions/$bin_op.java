package expressions;


public class $bin_op implements AbstractExpression {

    private char op;
    private AbstractExpression lhs;
    private AbstractExpression rhs;

    public $bin_op(char op, AbstractExpression lhs, AbstractExpression rhs) {
        this.op = op;
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
        return lhs.pp() + " " + op + " " + rhs.pp();
    }
}
