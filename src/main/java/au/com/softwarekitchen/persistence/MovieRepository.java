package au.com.softwarekitchen.persistence;

import au.com.softwarekitchen.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    Page<Movie> findByName(String lastName, Pageable pageable);

    Page<Movie> findDistinctByActors_IdIn(@Param("actorIds") Collection<Long> actorIds, Pageable pageable);
}