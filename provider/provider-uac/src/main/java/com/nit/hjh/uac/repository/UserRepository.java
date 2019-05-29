package com.nit.hjh.uac.repository;

import com.nit.hjh.uac.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUsername(String username);

    @Modifying
    @Query(value = "update user set password= :password where id= :uid",nativeQuery = true)
    boolean updatePasswordByUid(String uid,String password);

    @Override
    <S extends User> S save(S s);
}
