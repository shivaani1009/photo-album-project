package org.studyeasy.SpringStarter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringStarter.Models.Account;
import org.studyeasy.SpringStarter.Models.Authority;
import org.studyeasy.SpringStarter.Models.Post;
import org.studyeasy.SpringStarter.Services.AccountService;
import org.studyeasy.SpringStarter.Services.AuthorityService;
import org.studyeasy.SpringStarter.Services.PostService;
import org.studyeasy.SpringStarter.util.constants.Privileges;
import org.studyeasy.SpringStarter.util.constants.Roles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner{
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String...args) throws Exception{

        for(Privileges auth: Privileges.values()){
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivilege());
            authorityService.save(authority);
        }


        Account account01= new Account();
        Account account02= new Account();
        Account account03= new Account();
        Account account04= new Account();

        account01.setEmail("account01@email.com");
        account01.setPassword("password");
        account01.setFirstName("user01");
        account01.setLastName("lastname");

        account02.setEmail("account02@email.com");
        account02.setPassword("password");
        account02.setFirstName("user02");
        account02.setLastName("lastname");
        account02.setRole(Roles.ADMIN.getRole());

        account03.setEmail("account03@email.com");
        account03.setPassword("password");
        account03.setFirstName("user03");
        account03.setLastName("lastname");
        account03.setRole(Roles.EDITOR.getRole());

        account04.setEmail("account04@email.com");
        account04.setPassword("password");
        account04.setFirstName("user02");
        account04.setLastName("lastname");
        account04.setRole(Roles.EDITOR.getRole());
        Set<Authority> authorities = new HashSet<>();
        authorityService.findById(Privileges.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        authorityService.findById(Privileges.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        account04.setAuthorities(authorities);



        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);
        List<Post> posts = postService.getAll();

        if(posts.size() == 0){
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("Post 01 body..........");
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("Post 02 body..........");
            post02.setAccount(account02);
            postService.save(post02);
        }
    }
}