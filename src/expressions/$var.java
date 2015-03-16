package expressions;

/**
 * Created by Sam on 16/03/2015.
 */
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
