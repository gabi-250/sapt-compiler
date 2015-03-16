package expressions;

/**
 * Created by Sam on 16/03/2015.
 */
public class $while implements AbstractExpression {

    private AbstractExpression cond;
    private AbstractExpression[] body;

    public $while(AbstractExpression cond, AbstractExpression[] body) {
        this.cond = cond;
        this.body = body;
    }

    public AbstractExpression getCond() {
        return cond;
    }

    public AbstractExpression[] getBody() {
        return body;
    }

    @Override
    public String pp() {
        String p = "";
        p += "while " + cond.pp() + "{" + "\n";
        for (AbstractExpression x : body) {
            p += "  " + x.pp() + "\n";
        }
        p += "}";

        return p;
    }
}
