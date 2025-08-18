package org.studyeasy.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studyeasy.SpringStarter.Models.Post;

public interface PostRepository extends JpaRepository<Post, Long> { //generics
    
}
