package com.linkup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linkup.models.Chat;
import com.linkup.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

	
	public List<Chat> findByUserId(Integer userId);
	
	
	@Query("select c from Chat c where :user Member of c.users And :reqUser Member of c.users")
	public Chat findChatByUsersId(@Param("users")User user ,  @Param("reqUser") User reqUser);
	
	
	
}