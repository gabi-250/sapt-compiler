package expressions;


public class Print implements AbstractExpression {

    private AbstractExpression exp;

    public Print(AbstractExpression exp) {
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
