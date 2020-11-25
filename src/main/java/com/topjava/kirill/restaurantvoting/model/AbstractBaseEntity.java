package com.topjava.kirill.restaurantvoting.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class AbstractBaseEntity implements Persistable<Integer> {

    public abstract Integer getId();

    public abstract void setId(Integer id);

    @Override
    public boolean isNew() {
        return getId() == null;
    }
}
