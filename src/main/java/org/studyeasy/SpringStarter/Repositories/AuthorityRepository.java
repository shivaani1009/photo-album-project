package org.studyeasy.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.studyeasy.SpringStarter.Models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
