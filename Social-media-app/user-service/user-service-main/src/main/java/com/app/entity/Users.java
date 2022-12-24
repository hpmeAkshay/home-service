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
		@Column
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
		@Column(name = "creation_date", insertable = false)
		private Date creationDate;
	
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "modified_date")
		private Date modifiedDate;
		 
		@PrePersist
		private void prePersistCreationDate() {
			if(this.creationDate==null) {
				creationDate = new Date();
			}
			modifiedDate=new Date();
		}
}
