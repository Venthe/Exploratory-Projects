package eu.venthe.dddcore.model;

import java.io.Serializable;

public interface ValueObject extends Serializable {
    boolean sameAs(ValueObject valueObject);
    ValueObject copy();
}
