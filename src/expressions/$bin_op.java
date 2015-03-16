package expressions;

/**
 * Created by Sam on 16/03/2015.
 */
public class $bin_op implements AbstractExpression {

    private String op;
    private AbstractExpression lhs;
    private AbstractExpression rhs;

    public $bin_op(String op, AbstractExpression lhs, AbstractExpression rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getOp() {
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
