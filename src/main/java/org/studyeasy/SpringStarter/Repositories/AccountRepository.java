package org.studyeasy.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.studyeasy.SpringStarter.Models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> { //generics
    
}
