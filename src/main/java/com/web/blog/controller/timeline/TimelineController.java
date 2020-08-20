package com.web.blog.controller.timeline;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import javax.validation.Valid;

import com.web.blog.dao.timeline.TimelineDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.timeline.Timeline;
import com.web.blog.model.timeline.TimelineRequest;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3c104.p.ssafy.io:3000", "http://localhost:3000", "http://localhost:8081" })
@RestController
public class TimelineController {
    @Autowired
    TimelineDao dao;
    //1. 타임라인 생성
    @PostMapping("/timeline/create")
    @ApiOperation(value = "타임라인작성")
    public Object createTimeline(@Valid @RequestBody TimelineRequest request){

        Timeline timeline = new Timeline();
        timeline.setUid(request.getUid());
        timeline.setTltype(request.getTltype());
        timeline.setTlname(request.getTlname());
        dao.save(timeline);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    //2. 타임라인 수정
    @PutMapping("/timeline/{timelineno}")
    @ApiOperation(value = "타임라인수정")
    public Object updateTimeline(@PathVariable int timelineno,@Valid @RequestBody TimelineRequest request){

        Timeline timeline = new Timeline();
        timeline.setTimelineno(timelineno);
        timeline.setUid(request.getUid());
        timeline.setTltype(request.getTltype());
        timeline.setTlname(request.getTlname());
        dao.save(timeline);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    //3. 타임라인 읽어오기
    @GetMapping("/timeline/uid/{uid}")
    @ApiOperation(value="타임라인보기")
    public Object callTimelinebyUid(@PathVariable String uid) {
        List<Timeline> list = dao.findByUid(uid);
        
        return new ResponseEntity<List<Timeline>>(list, HttpStatus.OK);

    }

    //4. 타임라인 삭제
    @DeleteMapping("/timeline/{timelineno}")
    @ApiOperation(value="타임라인삭제")
    public Object deleteTimeline(@PathVariable int timelineno){
        Timeline timeline = (Timeline)dao.findByTimelineno(timelineno);
        if (timeline!= null) {

            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            dao.delete(timeline);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = "notExist";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
 

        }
    }
}