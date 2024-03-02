package com.linkup.models;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity; // Changed from jakarta.persistence.Entity
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany; // Changed from jakarta.persistence.ManyToMany
import javax.persistence.ManyToOne; // Changed from jakarta.persistence.ManyToOne

@Entity // Changed from jakarta.persistence.Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    @ManyToOne
    private User user;

    @ManyToMany // Adjusted here
    private List<User> liked = new ArrayList<>();

    private LocalDateTime createdAt;

    public Comment() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getLiked() {
		return liked;
	}

	public void setLiked(List<User> liked) {
		this.liked = liked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
    

}
