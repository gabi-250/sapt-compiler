package expressions;


public class Assignment implements AbstractExpression {

    private String name;
    private AbstractExpression exp;

    public Assignment(String name, AbstractExpression exp) {
        this.name = name;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public AbstractExpression getExp() {
        return exp;
    }

    @Override
    public String pp() {
        return name + " = " + exp.pp() + ";";
    }

}
