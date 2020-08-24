package com.balarawool.optional;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.balarawool.optional.OptionalExamples.Address.Type.EMAIL;
import static com.balarawool.optional.OptionalExamples.Country.BE;
import static com.balarawool.optional.OptionalExamples.Country.FR;
import static com.balarawool.optional.OptionalExamples.Country.IN;
import static com.balarawool.optional.OptionalExamples.Country.NL;
import static com.balarawool.optional.OptionalExamples.Country.US;

public class OptionalExamples {

    private static User USER = new User();
    private static String USERNAME = "friendly_user";
    private static List<Player> allPlayers = List.of(new Player(NL, 800), new Player(IN, 1000), new Player(US, 1100), new Player(BE, 800),
                                                     new Player(IN, 800), new Player(NL, 1100), new Player(FR, 700), new Player(BE, 1000));
    private static int RATING_MIN_VALUE = 0;

    public static void main(String[] args) {

        // Example - fluent APIs
        // Optional enables creation and usage of fluent APIs (especially while dealing with Stream-s) as shown below
        List<Score> scores = List.of(new Score("Peter Parker", 43), new Score("Bruce Wayne", 110), new Score("Scott Lang", 56),
                                     new Score("Peter Parker", 100), new Score("Bruce Wayne", 86), new Score("Tony Stark", 98));

        // Present an award to first centurion
        scores.stream()
                .filter(Score::centuryOrHigher)
                .findFirst()
                .ifPresentOrElse(s -> System.out.println("Award goes to " + s.getPlayer()),
                                () -> System.out.println("Nobody gets an award"));


        // Example - using ifPresent() instead of get()
        Person person = new Person("Bruce Wayne", new Address(EMAIL, "bruce.wayne@wayneenterprises.com"));
        // Using get()
        Optional<String> email = person.retrieveEmail();
        if(email.isPresent()) {
            sendEmail(email.get());
        }
        // Using ifPresent()
        person.retrieveEmail().ifPresent(emailId -> sendEmail(emailId));


        // Example - orElse() vs orElseGet()
        getUser().getName().orElse(generateRandomName()); // generateRandomName() gets executed
        getUser().getName().orElseGet(() -> generateRandomName()); // generateRandomName() does not get executed

    }

    private static void sendEmail(String emailId) {
        System.out.println("Email sent to " + emailId);
    }

    @Data
    @AllArgsConstructor
    static class Score {
        private String player;
        private int value;

        public boolean centuryOrHigher() {
            return value >= 100;
        }
    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;
        private Address address;

        public Optional<String> retrieveEmail() {
            return address.getType().equals(EMAIL)
			    ? Optional.of(address.getValue())
			    : Optional.empty();
        }
    }

    @Data
    @AllArgsConstructor
    static class Address {
        private Type type;
        private String value;

        static enum Type{
            POSTAL,
            EMAIL
        }
    }

    @Data
    @AllArgsConstructor
    static class User {

        public Optional<String> getName() {
            // Imagine fancy logic to retrieve username
            return Optional.ofNullable(USERNAME);
        }
    }

    private static User getUser() {
        // Imagine fancy logic to retrieve user
        return USER;
    }

    private static String generateRandomName() {
        // Imagine fancy logic to generate random username, for now hardcoded to Guest
        System.out.println("Generating random username");
        return "Guest";
    }

    public static List<Player> searchFor(Country country, Optional<Integer> minimumRatingOptional) {
        int minimumRating = minimumRatingOptional.orElse(RATING_MIN_VALUE);
        return allPlayers.stream()
                .filter(p -> p.getCountry().equals(country))
                .filter(p -> p.getRating() >= minimumRating)
                .collect(Collectors.toList());
    }
    public static List<Player> searchFor(Country country) {
        return searchFor(country, RATING_MIN_VALUE);
    }
    public static List<Player> searchFor(Country country, int minimumRating) {
        return allPlayers.stream()
                .filter(p -> p.getCountry().equals(country))
                .filter(p -> p.getRating() >= minimumRating)
                .collect(Collectors.toList());
    }
    @Data
    @AllArgsConstructor
    static class Player {
        private Country country;
        private int     rating;
    }

    enum Country {
        NL,
        IN,
        US,
        BE,
        FR,
    }

}
