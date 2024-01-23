package com.tritonkor.grouptester.persistence.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

public  abstract class Entity {
  protected final UUID id;
  protected List<String> errors;

  protected boolean isValid;

  public  Entity(UUID id) {
    errors = new ArrayList<>();
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entity entity = (Entity) o;
    return Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


}
