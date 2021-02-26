package com.jonasoliveira.workshopmongodb.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasoliveira.workshopmongodb.domain.Post;
import com.jonasoliveira.workshopmongodb.repository.PostRepository;
import com.jonasoliveira.workshopmongodb.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;
	
	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Post> findByTitle(String title){
		return repo.searchTitle(title);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate){
		//gerando data maxima até as 00:00 do dia seguinte
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}
}
