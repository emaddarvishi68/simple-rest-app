package com.emad.simplerestapp.model;

import com.emad.simplerestapp.model.helper.TableName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.USERS)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @ToString
    public static class Address implements Serializable{
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;

        @Data
        @Embeddable
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class Geo implements Serializable{
            String lat;
            String lng;
        }
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Company implements Serializable{
        String companyName;
        String catchPhrase;
        String bs;
    }

}
