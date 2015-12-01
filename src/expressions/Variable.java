package expressions;


public class Variable implements AbstractExpression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String pp() {
        return name;
    }

}
