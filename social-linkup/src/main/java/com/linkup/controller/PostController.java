package com.linkup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.linkup.models.Post;
import com.linkup.models.User;
import com.linkup.response.ApiResponse;
import com.linkup.service.PostService;
import com.linkup.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	UserService userService;

	@PostMapping("/api/posts/user")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post)
			throws Exception {

		User reqUser = userService.findUserByJwt(jwt);

		Post createdPost = postService.createNewPost(post, reqUser.getId());
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization") String jwt,
			@PathVariable Integer postId) throws Exception {

		User reqUser = userService.findUserByJwt(jwt);

		String message = postService.deletePost(postId, reqUser.getId());
		ApiResponse res = new ApiResponse(message, true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/api/posts/{postId}")
	public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
		Post post = postService.findPostById(postId);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/api/posts/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) {
		List<Post> posts = postService.findPostByUserId(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/api/posts")
	public ResponseEntity<List<Post>> findAllPost() {
		List<Post> posts = postService.findAllPosts();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PutMapping("/api/posts/{postId}")
	public ResponseEntity<Post> savePostHandler(@RequestHeader("Authorization") String jwt,

			@PathVariable Integer postId) throws Exception {

		User reqUser = userService.findUserByJwt(jwt);

		Post post = postService.savePost(postId, reqUser.getId());
		return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
	}

	@PutMapping("/api/posts/like/{postId}/user/{userId}")
	public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User reqUser = userService.findUserByJwt(jwt);
		Post post = postService.likePost(postId, reqUser.getId());
		return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
	}

}