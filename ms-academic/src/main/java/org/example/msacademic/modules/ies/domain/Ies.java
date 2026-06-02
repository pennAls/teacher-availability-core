package org.example.msacademic.modules.ies.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ies")
public class Ies {

        //id
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true)
        private String name;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private String phone;

        public Ies() {}

        public Ies(String name, String address, String phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public UUID getId() { return id; }
        public String getName() { return name; }
        public String getAddress() { return address; }
        public String getPhone() { return phone; }


}
