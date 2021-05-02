package com.mohamed.pizzarestaurant.controllers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.pizzarestaurant.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerController customerController;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void saveNewCustomer() throws Exception {
        Customer customer=new Customer();
        customer.setId(5L);
        customer.setLastName("Sohil");
        customer.setFirstName("Mohamed");
        customer.setPhoneNumber("0148795556");
        customer.setAddress("Darmstadt");
        customer.setToken("14sdo4I-4b87-12d3-a456-45795512550");
        //Mockito.when(customerController.saveNewCustomer(Mockito.any(Customer.class))).thenReturn(customer);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/Customer").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                             .accept(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(customer));
        MvcResult mvcResult=mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful()).andReturn();
        assertThat(HttpStatus.OK.value(),is(mvcResult.getResponse().getStatus()));
    }

    @Test
    public void loadCustomers() throws Exception {
        Customer customer=new Customer();
        customer.setId(5L);
        customer.setLastName("Sohil");
        customer.setFirstName("Mohamed");
        customer.setPhoneNumber("0148795556");
        customer.setAddress("Darmstadt");
        customer.setToken("14sdo4I-4b87-12d3-a456-45795512550");
        List<Customer> customerList=new ArrayList<>();
        customerList.add(customer);
        final String payload="[{id\":5,\"lastName\":\"Sohil\",\"firstName\":\"Mohamed\",\"address\":\"Darmstadt\",\"phoneNumber\":\"0148795556\",\"token\":\"14sdo4I-4b87-12d3-a456-45795512550\"}]";

      //Mockito.when(customerController.loadCustomers()).thenReturn(customerList);

        RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful()).andReturn();


    }

    @Test
    public void getCustomerById() throws Exception {
        Customer customer=new Customer();
        customer.setId(5L);
        customer.setLastName("Sohil");
        customer.setFirstName("Mohamed");
        customer.setPhoneNumber("0148795556");
        customer.setAddress("Darmstadt");
        customer.setToken("14sdo4I-4b87-12d3-a456-45795512550");

        Mockito.when(customerController.getCustomerById(5L)).thenReturn(customer);
        RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/customer/"+5)
                                                             .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(content().string(objectMapper.writeValueAsString(customer)))
                .andExpect(status().is2xxSuccessful()).andReturn();
        assertThat(HttpStatus.OK.value(),is(result.getResponse().getStatus()));

    }
}