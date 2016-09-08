package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

import java.util.Arrays;

public class ActorResource extends Resource<Actor> {

    @JsonUnwrapped
    private final Resources<EmbeddedWrapper> embeddedResources;

    public ActorResource(Actor content, Link... links) {
        this(content, Arrays.asList(links));
    }

    public ActorResource(Actor content, Iterable<Link> links) {
        this(content, null, links);
    }

    public ActorResource(Actor content, final Resources<EmbeddedWrapper> embeddedResources, Link... links) {
        this(content, embeddedResources, Arrays.asList(links));
    }

    public ActorResource(Actor content, final Resources<EmbeddedWrapper> embeddedResources, Iterable<Link> links) {
        super(content, links);
        this.embeddedResources = embeddedResources;
    }

    public Resources<EmbeddedWrapper> getEmbeddedResources() {
        return embeddedResources;
    }
}
