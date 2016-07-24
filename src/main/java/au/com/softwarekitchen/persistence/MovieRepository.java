package au.com.softwarekitchen.persistence;

import au.com.softwarekitchen.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    List<Movie> findByName(String lastName);
}