package com.balarawool.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

import static com.balarawool.optional.OptionalConciseExamples.Discount.Type.FLAT;
import static com.balarawool.optional.OptionalConciseExamples.Discount.Type.PERCENTAGE;
import static com.balarawool.optional.OptionalConciseExamples.LoyaltyStatus.GOLD;
import static com.balarawool.optional.OptionalConciseExamples.LoyaltyStatus.PLATINUM;
import static com.balarawool.optional.OptionalConciseExamples.LoyaltyStatus.SILVER;

public class OptionalConciseExamples {
    private static final Map<String, LoyaltyStatus> loyaltyStatusMap = Map.of("Peter Parker", SILVER, "Bruce Wayne", PLATINUM, "Scott Lang", GOLD,
                                                                              "Tony Stark", PLATINUM, "Bruce Banner", GOLD);

    public static void main(String[] args) {

        // Example 1
        Customer customer = new Customer("Peter Parker");
        customer.printStatus();
        customer.printStatusConcise();
    }

    @Data
    @AllArgsConstructor
    static class Customer {
        private String name;

        public Optional<LoyaltyStatus> getLoyaltyStatus() {
            return Optional.ofNullable(loyaltyStatusMap.get(name));
        }

        public void printStatus() {
            Optional<LoyaltyStatus> loyaltyStatusOptional = getLoyaltyStatus();
            if(loyaltyStatusOptional.isPresent()) {
                System.out.println("Customer’s loyalty status is: " + loyaltyStatusOptional.get());
            } else {
                System.out.println("No loyalty status found for customer.");
            }
        }

        public void printStatusConcise() {
            getLoyaltyStatus().ifPresentOrElse(loyaltyStatus -> System.out.println("Customer’s loyalty status is: " + loyaltyStatus),
                    () -> System.out.println("No loyalty status found for customer."));
        }

        public int getCustomerLoyaltyStatusPoints() {
            Optional<LoyaltyStatus> loyaltyStatusOptional = getLoyaltyStatus();
            if(loyaltyStatusOptional.isPresent()) {
                LoyaltyStatus loyaltyStatus = loyaltyStatusOptional.get();
                return loyaltyStatus.getPoints();
            }
            return 0;
        }

        public int getCustomerLoyaltyStatusPointsConcise() {
            return getLoyaltyStatus()
                    .map(LoyaltyStatus::getPoints)
				    .orElse(0);
        }

        public boolean isFreeShippingAvailable() {
            Optional<LoyaltyStatus> loyaltyStatusOptional = getLoyaltyStatus();
            if(loyaltyStatusOptional.isPresent()) {
                LoyaltyStatus loyaltyStatus = loyaltyStatusOptional.get();
                return loyaltyStatus.equals(GOLD) || loyaltyStatus.equals(PLATINUM);
            }
            return false;
        }

        public boolean isFreeShippingAvailableConcise() {
            return getLoyaltyStatus().filter(loyaltyStatus -> loyaltyStatus.equals(GOLD) || loyaltyStatus.equals(PLATINUM)).isPresent();
        }


        public Optional<Discount> retrieveDiscount() {
            Optional<LoyaltyStatus> loyaltyStatusOptional = getLoyaltyStatus();
            if(loyaltyStatusOptional.isPresent()) {
                LoyaltyStatus loyaltyStatus = loyaltyStatusOptional.get();
                return loyaltyStatus.getDiscount();
            }
            return Optional.empty();
        }

        public Optional<Discount> retrieveDiscountConcise() {
            return getLoyaltyStatus().flatMap(LoyaltyStatus::getDiscount);
        }

    }

    enum LoyaltyStatus {
        SILVER,
        GOLD,
        PLATINUM;

        public int getPoints() {
            switch (this) {
                case SILVER: return 100;
                case GOLD: return 200;
                case PLATINUM: return 500;
                default: return 0;
            }
        }

        public Optional<Discount> getDiscount() {
            switch (this) {
                case GOLD: return Optional.of(new Discount(FLAT, 100));
                case PLATINUM: return Optional.of(new Discount(PERCENTAGE, 20));
                default: return Optional.empty();
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Discount {
        private Type type;
        private int value;

        enum Type {
            FLAT,
            PERCENTAGE
        }
    }

    @Data
    @AllArgsConstructor
    @Getter
    static class ShippingObject {
        private int length;
        private int width;
        private int height;
    }

    public static Optional<ShippingObject> createShippingObject(DimensionsRetriever retriever) {
        Optional<Integer> length = retriever.retrieveLength();
        Optional<Integer> width = retriever.retrieveWidth();
        Optional<Integer> height = retriever.retrieveHeight();

        if(length.isPresent() && width.isPresent() && height.isPresent()) {
            return Optional.of(new ShippingObject(length.get(), width.get(), height.get()));
        }
        return Optional.empty();
    }

    public static Optional<ShippingObject> createShippingObjectConcise(DimensionsRetriever retriever) {
        return retriever.retrieveLength().flatMap(length ->
                    retriever.retrieveWidth().flatMap(width ->
                        retriever.retrieveHeight().map(height -> new ShippingObject(length, width, height))));
    }

    interface DimensionsRetriever {
        Optional<Integer> retrieveLength();
        Optional<Integer> retrieveWidth();
        Optional<Integer> retrieveHeight();
    }
}
