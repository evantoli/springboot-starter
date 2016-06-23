package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Customer;
import au.com.softwarekitchen.persistence.CustomerRepository;
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
@ExposesResourceFor(Customer.class)
@RequestMapping(value="/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResource getCustomer(@PathVariable final Long customerId) {

        final Customer customer = customerRepository.findOne(customerId);
        final CustomerResource customerResource = customerResourceAssembler.toResource(customer);
        return customerResource;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Gets all customers as paged resources.")
    public PagedResources<CustomerResource> getCustomers(
            @PageableDefault(size = 50) final Pageable pageable,
            final PagedResourcesAssembler<Customer> pagedResourcesAssembler) {

        final Page<Customer> customers = customerRepository.findAll(pageable);
        final PagedResources<CustomerResource> pagedResources = pagedResourcesAssembler.toResource(customers, customerResourceAssembler);
        return pagedResources;
    }

    @RequestMapping(value="/{customerId}", method=RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerRepository.delete(customerId);
    }
}
