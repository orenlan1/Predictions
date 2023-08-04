package world.properties;

import generated.PRDProperty;

public class Property {
    private String name;
    private enum Type {DECIMAL, FLOAT, BOOLEAN, STRING};
    private double from, to;
    Object value;

    private Type type;

    public Property(PRDProperty prdProperty) {
        name = prdProperty.getPRDName();
        from = prdProperty.getPRDRange().getFrom();
        to = prdProperty.getPRDRange().getTo();
        value = prdProperty.getPRDValue();
        type = Type.valueOf(prdProperty.getType());
    }


}
