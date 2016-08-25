package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Movie;
import au.com.softwarekitchen.persistence.MovieRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@ExposesResourceFor(Movie.class)
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieResourceAssembler movieResourceAssembler;

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieResource getMovie(@PathVariable final Long movieId) {

        final Movie movie = movieRepository.findOne(movieId);
        final MovieResource movieResource = movieResourceAssembler.toResource(movie);
        return movieResource;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets all movies as paged resources.")
    public PagedResources<MovieResource> getMovies(
            @RequestParam(name = "actorIds", required = false) final Long [] actorIds,
            @PageableDefault(size = 50) final Pageable pageable,
            final PagedResourcesAssembler<Movie> pagedResourcesAssembler) {

        final Page<Movie> movies;
        if (actorIds != null && actorIds.length > 0) {
            movies = movieRepository.findDistinctByActors_IdIn(Arrays.asList(actorIds), pageable);
        } else {
            movies = movieRepository.findAll(pageable);
        }
        final PagedResources<MovieResource> pagedResources = pagedResourcesAssembler.toResource(movies, movieResourceAssembler);
        return pagedResources;
    }

    @RequestMapping(value="/{movieId}", method=RequestMethod.DELETE)
    public void deleteActor(@PathVariable Long movieId) {
        movieRepository.delete(movieId);
    }
}
