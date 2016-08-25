package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.model.Movie;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MovieResourceAssembler extends ResourceAssemblerSupport<Movie, MovieResource> {

    public MovieResourceAssembler() {
        super(MovieController.class, MovieResource.class);
    }

    @Override
    public MovieResource toResource(final Movie movie) {

        final MovieResource resource = new MovieResource(movie);

        resource.add(linkTo(methodOn(MovieController.class)
                .getMovie(movie.getId())).withSelfRel());

        resource.add(linkTo(methodOn(ActorController.class)
                .getActors(new Long[] {movie.getId()}, null, null)).withRel("actors"));

        return resource;
    }
}
