package eu.venthe.dddcore.model;

public abstract class AbstractValueObject<VALUE_OBJECT_TYPE extends ValueObject> implements ValueObject {

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }

        @SuppressWarnings("unchecked")
        VALUE_OBJECT_TYPE other = (VALUE_OBJECT_TYPE) object;

        return this.sameAs(other);
    }

    @Override
    public abstract int hashCode();
}
