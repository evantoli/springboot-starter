package au.com.softwarekitchen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Actor extends AbstractModel<Long> {

    private String firstName;

    private String lastName;

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "actor_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "movie_id", nullable = false, updatable = false) })
    private final Set<Movie> movies = new HashSet<>();

    protected Actor() {}

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
    }

    public void addMovie(final Movie movie) {
        this.movies.add(movie);
    }

    @Override
    public String toString() {
        return toString(new StringBuilder());
    }

    public String toString(final StringBuilder sb) {
        sb.append('{');
        sb.append("firstName:'").append(firstName).append('\'');
        sb.append(", lastName:'").append(lastName).append('\'');
        sb.append(", movies:[");
        for (Movie movie : movies) {
            sb.append('{');
            sb.append("name:'").append(movie.getName()).append('\'');
            sb.append(", releaseDate:").append(movie.getReleaseDate());
            sb.append("},");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setLength(sb.length() - 1);
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }
}