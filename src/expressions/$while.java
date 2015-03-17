package expressions;

import java.util.ArrayList;

public class $while implements AbstractExpression {

    private AbstractExpression cond;
    private ArrayList<AbstractExpression> body;

    public $while(AbstractExpression cond, ArrayList<AbstractExpression> body) {
        this.cond = cond;
        this.body = body;
    }

    public AbstractExpression getCond() {
        return cond;
    }

    public ArrayList<AbstractExpression> getBody() {
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
