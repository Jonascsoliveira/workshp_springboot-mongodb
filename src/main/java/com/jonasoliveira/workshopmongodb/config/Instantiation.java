package com.jonasoliveira.workshopmongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.jonasoliveira.workshopmongodb.domain.Post;
import com.jonasoliveira.workshopmongodb.domain.User;
import com.jonasoliveira.workshopmongodb.dto.AuthorDTO;
import com.jonasoliveira.workshopmongodb.dto.CommentDTO;
import com.jonasoliveira.workshopmongodb.repository.PostRepository;
import com.jonasoliveira.workshopmongodb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("20/02/2021"), "Vamos viajar ?", "Viagem top a São Paulo!!!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("20/02/2021"), "Hoje e dia de picanha", "Delicioso jantar com pinha marinada",new AuthorDTO(alex));	

		CommentDTO c1 = new CommentDTO("Boa viagem", sdf.parse("21/12/2020"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Tudo de bom!", sdf.parse("20/02/2021"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Que churras top!!!", sdf.parse("25/02/2021"), new AuthorDTO(maria));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().add(post1);
		userRepository.save(maria);
		
		alex.getPosts().add(post2);
		userRepository.save(alex);
		
	}

}
