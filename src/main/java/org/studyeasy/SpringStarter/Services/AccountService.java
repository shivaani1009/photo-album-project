package org.studyeasy.SpringStarter.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.Models.Account;
import org.studyeasy.SpringStarter.Models.Authority;
import org.studyeasy.SpringStarter.Repositories.AccountRepository;
import org.studyeasy.SpringStarter.util.constants.Roles;

@Service 
public class AccountService implements UserDetailsService{

    @Value("/resources/static/**")
    private String photo_prefix;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    }

    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getRole()==null){
            account.setRole(Roles.USER.getRole());
        }
        if(account.getPhoto()==null){
            String path = photo_prefix.replace("**","images/client1.png");
            account.setPhoto("");
        }
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);
        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("Account not found");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));

        for(Authority _auth: account.getAuthorities()){
            grantedAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
        }
        return new User(account.getEmail(),account.getPassword(),grantedAuthority);
    }

    public Optional<Account> findOneByEmail(String email){
        return accountRepository.findOneByEmailIgnoreCase(email);
    }
    
}
