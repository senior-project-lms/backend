package com.lms.repositories;

import com.lms.entities.GlobalQA;
import com.lms.entities.GlobalQATag;
import jdk.nashorn.internal.objects.Global;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GlobalQARepository extends JpaRepository<GlobalQA,Long>{

    List<GlobalQA> findAllByVisibleAndAnswerOrderByCreatedAtDesc(boolean visible, boolean answer, Pageable pageable);

     GlobalQA findByPublicKeyAndVisible(String publicKey, boolean visible);

     List<GlobalQA> findAllByParentAndVisible(GlobalQA parent, boolean visible);

    int countByParentAndVisible(GlobalQA parent, boolean visible);


    List<GlobalQA> findTop10ByTagsInAndVisibleOrderByCreatedAtDesc(List<GlobalQATag> tags, boolean visible);
}
