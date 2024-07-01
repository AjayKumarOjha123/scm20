package com.scm.scm20.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImagePublicId;

    //private List<String>socialLinks=new ArrayList<>()

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> socialLinks = new ArrayList<>();
}
