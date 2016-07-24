package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Movie;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class MovieResource extends Resource<Movie> {

    public MovieResource(Movie content, Link... links) {
        super(content, links);
    }

    public MovieResource(Movie content, Iterable<Link> links) {
        super(content, links);
    }
}
