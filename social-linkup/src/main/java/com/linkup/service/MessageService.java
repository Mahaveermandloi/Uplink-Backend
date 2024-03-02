package com.linkup.service;

import java.util.List;

import com.linkup.models.Message;
import com.linkup.models.User;

public interface MessageService {

	public Message createMessage(User user, Integer chatId,Message req) throws Exception;

	public List<Message> findChatMessages(Integer chatId) throws Exception;

}
