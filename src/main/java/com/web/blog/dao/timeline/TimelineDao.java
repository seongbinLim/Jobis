package com.web.blog.dao.timeline;

import java.util.List;

import com.web.blog.model.timeline.Timeline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineDao extends JpaRepository<Timeline, String> {
    //c,u
    public Timeline save(Timeline timeline);
    //r
    public List<Timeline> findByUid(String uid);
    public Timeline findByTimelineno(int timelineno);
    //d
    public void deleteByTimelineno(int timelineno);
    public void delete(Timeline timeline);

}