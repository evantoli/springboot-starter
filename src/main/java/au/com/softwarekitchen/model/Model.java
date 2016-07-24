package au.com.softwarekitchen.model;

import org.springframework.hateoas.Identifiable;

import java.io.Serializable;
import java.time.Instant;

public interface Model<ID extends Serializable> extends Identifiable<ID> {

    ID getId();

    Instant getCreatedAt();

    String getCreatedBy();

    Instant getLastModified();

    String getLastModifiedBy();
}
