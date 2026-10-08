package kh.edu.istad.common.domain.entity;

import java.util.Objects;

public class BaseEntity <ID>{

    private ID id;


    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
