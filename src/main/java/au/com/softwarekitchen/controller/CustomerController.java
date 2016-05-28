package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Customer;
import au.com.softwarekitchen.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value="/{customerId}", method= RequestMethod.GET)
    public Resource<Customer> getCustomer(@PathVariable Long customerId) {
        final Customer customer = customerRepository.findOne(customerId);
        final Resource<Customer> customerResource = new Resource<>(customer);
        customerResource.add(linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel());
        return customerResource;
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public PagedResources<Resource<Customer>> getCustomers(Pageable pageable) {

        final Page<Customer> customers = customerRepository.findAll(pageable);
        final PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(
                customers.getSize(), customers.getNumber(), customers.getTotalElements(), customers.getTotalPages());

        final List<Resource<Customer>> customerResources = customers.getContent().stream().map(customer -> {
            final Resource<Customer> customerResource = new Resource<>(customer);
            customerResource.add(linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel());
            return customerResource;
        }).collect(Collectors.toList());

        final PagedResources<Resource<Customer>> pageCustomerResources = new PagedResources<>(
                customerResources, pageMetadata, new Link[0]);

        return pageCustomerResources;
    }

    @RequestMapping(value="/{customerId}", method=RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerRepository.delete(customerId);
    }
}
