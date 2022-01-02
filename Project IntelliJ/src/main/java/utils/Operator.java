package utils;

/**
 * Questa enumerazione contiene gli operatori principali da utilizzare nelle query SQL.
 */
public enum Operator {

    /**
     * Rappresentano le sigle degli operatori.
     */
    GT, LT, EQ, NE, GE, LE, MATCH;

    /**
     * Implementa la funzionalità che permette di convertire le sigle in stringhe contenenti gli operatori SQL.
     * @return stringa contenente l'operatore SQL
     */
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
