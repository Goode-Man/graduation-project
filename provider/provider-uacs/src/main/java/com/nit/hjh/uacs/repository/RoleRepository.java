package com.nit.hjh.uacs.repository;

import com.nit.hjh.uacs.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value = "select rid from role where rname= :rname",nativeQuery = true)
    int findRidByRname(String rname);

    Role findByRid(int rid);
}
