package com.web.blog.dao.introduction;

import javax.transaction.Transactional;

import com.google.common.base.Optional;
import com.web.blog.model.introduction.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagDao extends JpaRepository<HashTag, String> {

    // create
    public HashTag save(HashTag newtag);

    // select
    public HashTag findHashtagByTagno(int tagno);
    public HashTag findHashtagByTagnoAndUid(int tagno, String uid);

    public HashTag findHashtagByTagname(String tagname);
    public HashTag findHashtagByTagnameAndUid(String tagname, String uid);

    //delete
    @Transactional 
    public Optional<HashTag> deleteByTagno(int tagno);
}