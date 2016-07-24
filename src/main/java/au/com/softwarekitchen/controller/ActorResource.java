package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ActorResource extends Resource<Actor> {

    public ActorResource(Actor content, Link... links) {
        super(content, links);
    }

    public ActorResource(Actor content, Iterable<Link> links) {
        super(content, links);
    }
}
