package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.hal.ResourcesMixin;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ActorResourceAssembler
        extends ResourceAssemblerSupport<Actor, ActorResource> {

    public ActorResourceAssembler() {
        super(ActorController.class, ActorResource.class);
    }

    public ActorResource toResource(final Actor actor, final Long version) {

        if (version == null || version < 20160701L) {
            return toResourceDeprecated20160701(actor, version);
        } else {
            return toResource(actor);
        }
    }

    @Override
    public ActorResource toResource(final Actor actor) {

        final ActorResource resource = new ActorResource(actor);

        resource.add(linkTo(methodOn(ActorController.class)
                .getActor(actor.getId(), null)).withSelfRel());

        resource.add(linkTo(methodOn(MovieController.class)
                .getMovies(new Long[] {actor.getId()}, null, null)).withRel("movies"));


        return resource;
    }

    @Deprecated
    public ActorResource toResourceDeprecated20160701(final Actor actor, final Long version) {

        final EmbeddedWrappers wrappers = new EmbeddedWrappers(true);
        final List<EmbeddedWrapper> embeddedWrappers = actor.getMovies().stream()
                .map(m -> wrappers.wrap(new MovieResource(m,
                        linkTo(methodOn(MovieController.class).getMovie(m.getId())).withSelfRel())))
                .collect(Collectors.toList());
        final Resources<EmbeddedWrapper> embeddedResources =
                new Resources<>(embeddedWrappers);

        final ActorResource resource = new ActorResource(actor, embeddedResources);

        resource.add(linkTo(methodOn(ActorController.class)
                .getActor(actor.getId(), version)).withSelfRel());

        return resource;
    }

}
