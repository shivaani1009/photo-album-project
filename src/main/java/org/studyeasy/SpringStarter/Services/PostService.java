package org.studyeasy.SpringStarter.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.Models.Post;
import org.studyeasy.SpringStarter.Repositories.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(Long id){ //optional means it will return null even if it hits nth
        return postRepository.findById(id);
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public Post save(Post post){
        if(post.getId()==null){ //for new record
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }
}
