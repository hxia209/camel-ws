package org.hui.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.hui.training.impl.CustomerImpl;

public class CustomerTest {

    CustomerImpl testImpl;

    @Before
    public void init(){
        testImpl = new CustomerImpl();
        testImpl.createCustomer("junit_test", 1);
    }

    @Test
    public void getAllCustomer(){
        assertEquals(testImpl.getAllCustomer().size(), 1);
    }

    @After
    public void destroy(){
        testImpl.deleteCustomer("junit_test");
    }
}
