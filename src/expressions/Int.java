package expressions;


public class Int implements AbstractExpression {

    private int val;

    public Int(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String pp() {
        return String.valueOf(val);
    }
}
