package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.hateoas.HALResource;
import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.model.Movie;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ActorResource extends HALResource<Actor> {

    public ActorResource(Actor content, Link... links) {
        super(content, links);
        content.getMovies().forEach(m -> embedResource("movies", new MovieResource(m)));
    }

    public ActorResource(Actor content, Iterable<Link> links) {
        super(content, links);
        content.getMovies().forEach(m -> embedResource("movies", new MovieResource(m)));
    }
}
