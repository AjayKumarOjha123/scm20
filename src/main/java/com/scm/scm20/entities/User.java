package com.scm.scm20.entities;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
	@Id
	private String userId;
	@Column(name = "user_name", nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@Getter(value = AccessLevel.NONE)
	private String password;
	private String about;
	private String phoneNumber;
	private String profilePic;

	// information
	@Getter(value = AccessLevel.NONE)
	private boolean enabled = true;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;

	// Self Google Github
	private Providers provider = Providers.SELF;
	private String providerUserId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return roles;
	}

	// username hamara email id hai es project me

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

}
