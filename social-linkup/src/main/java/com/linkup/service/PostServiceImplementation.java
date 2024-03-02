package com.linkup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.linkup.models.Post;
import com.linkup.models.User;
import com.linkup.repository.PostRepository;
import com.linkup.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserService userService;

	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {

		User user = userService.findUserById(userId);

		Post newPost = new Post();

		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setCreateAt(LocalDateTime.now());
		newPost.setUser(user);

		return postRepository.save(newPost);

	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {

		return postRepository.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {

		Optional<Post> opt = postRepository.findById(postId);

		if (opt.isEmpty()) {
			throw new Exception("post not found with id " + postId);

		}
		return opt.get();

	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);

		User user = userService.findUserById(userId);

		if (post.getUser().getId() != user.getId()) {
			throw new Exception("You cant delete another users post");
		}
		postRepository.delete(post);
		return "Post deleted Successfully";

	}

	@Override
	public List<Post> findAllPosts() {
	

		return postRepository.findAll();

	}

	@Override
	public Post savePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);

		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		} else {
			user.getSavedPost().add(post);
			userRepository.save(user);

		}

		return post;

	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);

		User user = userService.findUserById(userId);

		if (post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		} else {

			post.getLiked().add(user);
		}

		return postRepository.save(post);

	}

}