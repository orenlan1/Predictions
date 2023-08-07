package world.property.api;


public interface PropertyDefinition {
    String getName();
    AbstractPropertyDefinition.PropertyType getType();
    Object generateValue();
}
