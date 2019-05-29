package com.nit.hjh.sms.repository;

import com.nit.hjh.sms.model.entity.Sms;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsRepository extends JpaRepository<Sms,String> {

    @Override
    <S extends Sms> List<S> findAll(Example<S> example);

    List<Sms> findAllByBizid(String Bizid);

    List<Sms> findAllByPhone(String phone);

    @Query(value = "select * from sms where sendtime like '?1%'",nativeQuery = true)
    List<Sms> findAllBySendDate(String sendDate);

    @Override
    <S extends Sms> S save(S s);
}
