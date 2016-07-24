package au.com.softwarekitchen.persistence;

import au.com.softwarekitchen.model.Actor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {

    List<Actor> findByLastName(String lastName);
}