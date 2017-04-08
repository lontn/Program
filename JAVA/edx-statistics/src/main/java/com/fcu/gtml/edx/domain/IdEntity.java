package com.fcu.gtml.edx.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @since $Id: bddb32446d08fc8fc88cf4abd350f0d48f3a842d $
 */
public abstract class IdEntity<K extends Serializable> extends Entity {

    private static final long serialVersionUID = 1L;
    private int hash;

    protected K id;

    abstract public K getId();

    abstract public void setId(K id);

    @Override
    public int hashCode() {
        if (hash == 0)
            hash = new HashCodeBuilder().append(this.getClass()).append(id)
                    .toHashCode();
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (this.id == null)
            return false;
        if (!(this.getClass().isInstance(other)))
            return false;

        @SuppressWarnings("unchecked")
        IdEntity<K> o = (IdEntity<K>) other;
        if (o.id == null)
            return false;

        return this.id.equals(o.id);
    }
}
