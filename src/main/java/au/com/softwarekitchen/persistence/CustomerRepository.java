package au.com.softwarekitchen.persistence;

import au.com.softwarekitchen.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}