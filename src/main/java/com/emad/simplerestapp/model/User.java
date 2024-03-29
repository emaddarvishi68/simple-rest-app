package com.emad.simplerestapp.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.USERS)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 63)
    private String name;
    @Column(nullable = false, length = 63)
    private String username;
    @Column(nullable = false, length = 127)
    private String email;
    @Column(nullable = false, length = 63)
    private String phone;
    @Column(nullable = false, length = 127)
    private String website;
    @Embedded
    private Address address;
    @Embedded
    private Company company;

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address implements Serializable {
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;

        @Data
        @Embeddable
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Geo implements Serializable {
            String lat;
            String lng;
        }
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Company implements Serializable {
        @Column(insertable = false, updatable = false)
        String name;
        String catchPhrase;
        String bs;
    }

}
