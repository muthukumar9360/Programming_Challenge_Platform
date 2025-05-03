package com.coding.Programming_Platform.Service;

import com.coding.Programming_Platform.Model.CodingSets;
import com.coding.Programming_Platform.Model.Userinfo;
import com.coding.Programming_Platform.Repository.CodingSet;
import com.coding.Programming_Platform.Repository.DBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private DBRepository repo;

    @Autowired
    private CodingSet csrepo;

    public Userinfo validateLogin(String email,String username, String password) {
        Userinfo user = repo.findByUsernameAndPassword(email,username, password);
        if (user != null) {
            user.setLastlogin(LocalDateTime.now());
            repo.save(user);
        }
        return user;
    }

    public void saveuser(Userinfo user){
        repo.save(user);
    }

    public List<CodingSets> getAvailableSets(){
        return csrepo.findAll();
    }
}
