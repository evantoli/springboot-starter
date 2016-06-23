package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Customer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toResource(final Customer customer) {

        final CustomerResource customerResource = new CustomerResource(customer);
        customerResource.add(linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel());
        return customerResource;
    }
}
