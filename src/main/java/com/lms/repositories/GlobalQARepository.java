package com.lms.repositories;

import com.lms.entities.GlobalQA;
import jdk.nashorn.internal.objects.Global;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GlobalQARepository extends JpaRepository<GlobalQA,Long>{

    List<GlobalQA> findAllByVisible(boolean visible, Pageable pageable);

     GlobalQA findByPublicKeyAndVisible(String publicKey, boolean visible);

     List<GlobalQA> findAllByParentAndVisible(GlobalQA parent, boolean visible);
}
