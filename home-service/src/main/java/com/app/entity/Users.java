package com.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Column(length = 50, unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(name = "phone_no", length = 10)
	private String phoneNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date joinDate;

	@PrePersist
	private void prePersist() {
		if(this.joinDate==null) {
			joinDate = new Date();
		}
	}

}
