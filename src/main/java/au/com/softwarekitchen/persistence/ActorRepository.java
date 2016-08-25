package au.com.softwarekitchen.persistence;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {

    Page<Actor> findByLastName(String lastName, Pageable pageable);

    Page<Actor> findDistinctByMovies_IdIn(@Param("movieIds") Collection<Long> movieIds, Pageable pageable);

}