package expressions;

/**
 * Created by Sam on 16/03/2015.
 */
public class $print implements AbstractExpression {

    private AbstractExpression exp;

    public $print(AbstractExpression exp) {
        this.exp = exp;
    }

    public AbstractExpression getExp() {
        return exp;
    }

    @Override
    public String pp() {
        return "print " + exp.pp() + ";";
    }
}
