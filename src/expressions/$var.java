package expressions;


public class $var implements AbstractExpression {

    private String name;

    public $var(String name) {
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
