package au.com.softwarekitchen.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by van on 24/07/2016.
 */
@Entity
public class Movie extends AbstractModel<Long> {

    private String name;

    private LocalDate releaseDate;

    private String desccription;

    @JsonIgnore
    @ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "movie_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "actor_id", nullable = false, updatable = false) })
    private final Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(final String name, final LocalDate releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDesccription() {
        return desccription;
    }

    public void setDesccription(String desccription) {
        this.desccription = desccription;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors.clear();
        this.actors.addAll(actors);
    }

    @Override
    public String toString() {
        return toString(new StringBuilder());
    }

    public String toString(final StringBuilder sb) {
        sb.append('{');
        sb.append("name:'").append(name).append('\'');
        sb.append(", releaseDate:").append(releaseDate);
        sb.append(", desccription:'").append(desccription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
