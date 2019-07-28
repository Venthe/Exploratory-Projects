package eu.venthe.dddcore.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntity implements Serializable {
    protected UUID uuid = UUID.randomUUID();

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || otherObject.getClass() != this.getClass()) {
            return false;
        }

        if (otherObject == this) {
            return true;
        }

        AbstractEntity otherAbstractEntity = (AbstractEntity) otherObject;
        return this.uuid.equals(otherAbstractEntity.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
