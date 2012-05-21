package edu.chip.carranet.overlay.persistance;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 23, 2010
 */
public class PersistedResource implements Serializable{
    final static int version = 0x01;
    String owner;
    byte[] payload;

    public PersistedResource(String owner, byte[] payload) {
        this.owner = owner;
        this.payload = Arrays.copyOf(payload, payload.length);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = Arrays.copyOf(payload, payload.length);
    }

    public static PersistedResource fromBytes() {
        return null;
    }

    public byte[] toBytes() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistedResource that = (PersistedResource) o;

        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (!Arrays.equals(payload, that.payload)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (payload != null ? Arrays.hashCode(payload) : 0);
        return result;
    }
}

