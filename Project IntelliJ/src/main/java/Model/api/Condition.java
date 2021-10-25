package Model.api;

import java.util.List;
import java.util.StringJoiner;

public class Condition {

    private final String name;
    private final Operator operator;
    private final Object value;

    public Condition(String name, Operator operator, Object value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String toString() { return name + operator; }

    public String getName() {
        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public static String searchConditions(List<Condition> conditions, String alias) {
        StringJoiner conditionJoiner = new StringJoiner(" AND ");
        for(Condition cn : conditions) {
            String tmp = alias + "." + cn.toString() + "?";
            conditionJoiner.add(tmp);
        }
        return conditionJoiner.toString();
    }
}
