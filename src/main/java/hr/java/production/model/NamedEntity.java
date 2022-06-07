package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract class NamedEntity
 */
public abstract class NamedEntity implements Serializable {
    protected String name;
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedEntity)) return false;
        NamedEntity that = (NamedEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Constructor for NamedEntity
     * @param name name of NamedEntity
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    public NamedEntity(String name, Long id) {
        this.name = name;
        this.id=id;

    }
    public NamedEntity(Long id, String name) {
        this.id=id;
        this.name = name;
    }

    /**
     * Default constructor
     */
    public NamedEntity(){};

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
