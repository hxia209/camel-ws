package org.hui.training.impl;

import org.hui.training.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerImpl {

    private static List<Customer> customers = new ArrayList<>();
    public List<Customer> getAllCustomer() {
        return customers;
    }

    public String createCustomer(String name, int age) {
        customers.add(new Customer(name, age));
        return "customer " + name + " with age " + age + " added";
    }

    public String deleteCustomer(String name){
        customers.remove(customers.stream().filter(p -> p.getName().equals(name)).findAny().get());
        return "Customer " + name + " deleted";
    }
}
