package com.linkup.request;

import com.linkup.models.User;

import lombok.Data;

@Data
public class CreateChatRequest {

	private Integer userId;

	private User user2;

}
