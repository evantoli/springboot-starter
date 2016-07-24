package au.com.softwarekitchen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 *
 */
@MappedSuperclass
public abstract class AbstractModel<ID extends Serializable> implements Model<ID> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected ID id;

    @JsonIgnore
    @CreatedDate
    protected Instant createdAt;

    @JsonIgnore
    @CreatedBy
    protected String createdBy;

    @JsonIgnore
    @LastModifiedDate
    protected Instant lastModified;

    @JsonIgnore
    @LastModifiedBy
    protected String lastModifiedBy;

    public AbstractModel() {
    }

    public AbstractModel(ID id ) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }

    public void setId(final ID id) {
        this.id = id;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Instant lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
