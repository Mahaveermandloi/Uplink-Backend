package com.linkup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.models.Chat;
import com.linkup.models.Message;
import com.linkup.models.User;
import com.linkup.repository.ChatRepository;
import com.linkup.repository.MessageRepository;

@Service
public class MessageServiceImplementation implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Message createMessage(User user, Integer chatId, Message req) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		Message message = new Message();
		message.setChat(chat);
		message.setContent(req.getContent());
		message.setImage(req.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());

		Message savedMessage = messageRepository.save(message);

		
//		check this below method udhar defined h ki nhi 
		
		chat.getMessages().add(savedMessage);

		
		chatRepository.save(chat);

		return savedMessage;
	}

	@Override
	public List<Message> findChatMessages(Integer chatId) throws Exception {
//		Chat chat = chatService.findChatById(chatId);
		return messageRepository.findByChatId(chatId);
	}

}