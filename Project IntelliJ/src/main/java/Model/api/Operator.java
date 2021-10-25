package Model.api;

public enum Operator {

    GT, LT, EQ, NE, GE, LE, MATCH;

    @Override
    public String toString() {
        switch(this)
        {
            case LT:  return " < ";
            case EQ:  return " = ";
            case GE:  return " >= ";
            case NE:  return " != ";
            case GT:  return " > ";
            case LE:  return " <= ";
            case MATCH:  return " LIKE ";
        }
        return null;
    }
}
