package br.com.tech.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users", schema = "public")
@Data
@Builder
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String registrationId;
	
	@Column(unique = true)
	private String sub;
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
	private List<Role> roles;
	
	@Column
	private LocalDateTime lastLoginDateTime;
	
	
	public User() {}

	public User(String id, String name, String email, String registrationId, String sub, List<Role> roles, LocalDateTime lastLoginDateTime) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.registrationId = registrationId;
		this.sub = sub;
		this.roles = roles;
		this.lastLoginDateTime = lastLoginDateTime;
	}
}