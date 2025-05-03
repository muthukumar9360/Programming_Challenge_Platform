package com.coding.Programming_Platform.Repository;

import com.coding.Programming_Platform.Model.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DBRepository extends JpaRepository<Userinfo,String> {

    @Query(value = "SELECT * FROM userinfo WHERE username=:username AND password=:password AND email=:email",nativeQuery = true)
    Userinfo findByUsernameAndPassword(@Param("email") String email,@Param("username") String username,@Param("password") String password);

}
