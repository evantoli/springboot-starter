package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.persistence.ActorRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Actor.class)
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorResourceAssembler actorResourceAssembler;

    @RequestMapping(value = "/{actorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActorResource getActor(@PathVariable final Long actorId) {

        final Actor actor = actorRepository.findOne(actorId);
        final ActorResource actorResource = actorResourceAssembler.toResource(actor);
        return actorResource;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets all actors as paged resources.")
    public PagedResources<ActorResource> getActors(
            @PageableDefault(size = 50) final Pageable pageable,
            final PagedResourcesAssembler<Actor> pagedResourcesAssembler) {

        final Page<Actor> actors = actorRepository.findAll(pageable);
        final PagedResources<ActorResource> pagedResources = pagedResourcesAssembler.toResource(actors, actorResourceAssembler);
        return pagedResources;
    }

    @RequestMapping(value="/{actorId}", method=RequestMethod.DELETE)
    public void deleteActor(@PathVariable Long actorId) {
        actorRepository.delete(actorId);
    }
}
