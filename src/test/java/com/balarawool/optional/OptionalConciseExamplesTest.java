package com.balarawool.optional;

import com.balarawool.optional.OptionalConciseExamples.Customer;
import com.balarawool.optional.OptionalConciseExamples.DimensionsRetriever;
import com.balarawool.optional.OptionalConciseExamples.Discount;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.balarawool.optional.OptionalConciseExamples.Discount.Type.FLAT;
import static com.balarawool.optional.OptionalConciseExamples.Discount.Type.PERCENTAGE;
import static com.balarawool.optional.OptionalConciseExamples.createShippingObject;
import static com.balarawool.optional.OptionalConciseExamples.ShippingObject;
import static com.balarawool.optional.OptionalConciseExamples.createShippingObjectConcise;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalConciseExamplesTest {

    @Test
    public void testExample2() {
        // Example 2
        Customer customer1 = new Customer("Peter Parker");
        assertEquals(100, customer1.getCustomerLoyaltyStatusPoints());
        assertEquals(100, customer1.getCustomerLoyaltyStatusPointsConcise());

        Customer customer2 = new Customer("Bruce Wayne");
        assertEquals(500, customer2.getCustomerLoyaltyStatusPoints());
        assertEquals(500, customer2.getCustomerLoyaltyStatusPointsConcise());

        Customer customer3 = new Customer("Scott Lang");
        assertEquals(200, customer3.getCustomerLoyaltyStatusPoints());
        assertEquals(200, customer3.getCustomerLoyaltyStatusPointsConcise());
    }

    @Test
    public void testExample3() {
        // Example 3
        Customer customer1 = new Customer("Peter Parker");
        assertFalse(customer1.isFreeShippingAvailable());
        assertFalse(customer1.isFreeShippingAvailableConcise());

        Customer customer2 = new Customer("Bruce Wayne");
        assertTrue(customer2.isFreeShippingAvailable());
        assertTrue(customer2.isFreeShippingAvailableConcise());

        Customer customer3 = new Customer("Scott Lang");
        assertTrue(customer3.isFreeShippingAvailable());
        assertTrue(customer3.isFreeShippingAvailableConcise());
    }

    @Test
    public void testExample4() {
        // Example 4
        Customer customer1 = new Customer("Peter Parker");
        assertTrue(customer1.retrieveDiscount().isEmpty());
        assertTrue(customer1.retrieveDiscountConcise().isEmpty());

        Customer customer2 = new Customer("Bruce Wayne");
        Optional<Discount> discount2 = customer2.retrieveDiscount();
        assertTrue(discount2.isPresent());
        assertEquals(PERCENTAGE, discount2.get().getType());
        assertEquals(20, discount2.get().getValue());

        discount2 = customer2.retrieveDiscountConcise();
        assertTrue(discount2.isPresent());
        assertEquals(PERCENTAGE, discount2.get().getType());
        assertEquals(20, discount2.get().getValue());

        Customer customer3 = new Customer("Scott Lang");
        Optional<Discount> discount3 = customer3.retrieveDiscount();
        assertTrue(discount3.isPresent());
        assertEquals(FLAT, discount3.get().getType());
        assertEquals(100, discount3.get().getValue());

        discount3 = customer3.retrieveDiscountConcise();
        assertTrue(discount3.isPresent());
        assertEquals(FLAT, discount3.get().getType());
        assertEquals(100, discount3.get().getValue());
    }

    @Test
    public void testExample5() {
        // Example 2
        DimensionsRetriever retriever1 = new DimensionsRetriever() {

            @Override
            public Optional<Integer> retrieveLength() {
                return Optional.of(500);
            }

            @Override
            public Optional<Integer> retrieveWidth() {
                return Optional.of(200);
            }

            @Override
            public Optional<Integer> retrieveHeight() {
                return Optional.of(100);
            }
        };
        Optional<ShippingObject> shippingObjectOptional1 = createShippingObject(retriever1);
        assertTrue(shippingObjectOptional1.isPresent());
        ShippingObject shippingObject1 = shippingObjectOptional1.get();
        assertEquals(500, shippingObject1.getLength());
        assertEquals(200, shippingObject1.getWidth());
        assertEquals(100, shippingObject1.getHeight());

        shippingObjectOptional1 = createShippingObjectConcise(retriever1);
        assertTrue(shippingObjectOptional1.isPresent());
        shippingObject1 = shippingObjectOptional1.get();
        assertEquals(500, shippingObject1.getLength());
        assertEquals(200, shippingObject1.getWidth());
        assertEquals(100, shippingObject1.getHeight());

        DimensionsRetriever retriever2 = new DimensionsRetriever() {

            @Override
            public Optional<Integer> retrieveLength() {
                return Optional.of(500);
            }

            @Override
            public Optional<Integer> retrieveWidth() {
                return Optional.of(200);
            }

            @Override
            public Optional<Integer> retrieveHeight() {
                return Optional.empty();
            }
        };
        Optional<ShippingObject> shippingObjectOptional2 = createShippingObject(retriever2);
        assertTrue(shippingObjectOptional2.isEmpty());

        shippingObjectOptional2 = createShippingObjectConcise(retriever2);
        assertTrue(shippingObjectOptional2.isEmpty());
    }

}
