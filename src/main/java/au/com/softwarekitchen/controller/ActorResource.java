package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ActorResource extends Resource<Actor> {

    @JsonUnwrapped
    private final Resources<EmbeddedWrapper> embeddedResources;

    public ActorResource(Actor content, Link... links) {
        this(content, Arrays.asList(links));
    }

    public ActorResource(Actor content, Iterable<Link> links) {
        super(content, links);

        final EmbeddedWrappers wrappers = new EmbeddedWrappers(true);
        final List<EmbeddedWrapper> embeddedWrappers = content.getMovies().stream()
                .map(m -> wrappers.wrap(new MovieResource(m), "movies"))
                .collect(Collectors.toList());
        embeddedResources = new Resources<>(embeddedWrappers, linkTo(MovieController.class).withSelfRel());
    }
}
