package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.model.Movie;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ActorResourceAssembler extends ResourceAssemblerSupport<Actor, ActorResource> {

    public ActorResourceAssembler() {
        super(ActorController.class, ActorResource.class);
    }

    @Override
    public ActorResource toResource(final Actor actor) {

        final ActorResource resource = new ActorResource(actor);

        resource.add(linkTo(methodOn(ActorController.class)
                .getActor(actor.getId())).withSelfRel());

        resource.add(linkTo(methodOn(MovieController.class)
                .getMovies(new Long[] {actor.getId()}, null, null)).withRel("movies"));

        return resource;
    }
}
