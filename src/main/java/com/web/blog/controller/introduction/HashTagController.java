package com.web.blog.controller.introduction;

import java.util.ArrayList;
import java.util.List;

import com.web.blog.dao.introduction.HashTagDao;
import com.web.blog.dao.introduction.IntroductionDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.introduction.HashTag;
import com.web.blog.model.introduction.Introduction;
import com.web.blog.model.introduction.WriteRequest;
import com.web.blog.model.introduction.Tagging;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3c104.p.ssafy.io:3000", "http://localhost:3000", "http://localhost:8081" })
@RestController
public class HashTagController {

    @Autowired
    private HashTagDao tagDao;

    @Autowired
    private IntroductionDao introDao;

    // 해시태그로 자소서 항목들 전체 조회!
    @ApiOperation(value = "해시태그로 자소서 항목 전체 조회")
    @GetMapping("/hashtag/{tagno}")
    public ResponseEntity<List<WriteRequest>> searchTags(@PathVariable int tagno){
        HashTag tag = tagDao.findHashtagByTagno(tagno);
        List<Tagging> tagging = tag.getTaggings();
        List<HashTag> tagTmp = new ArrayList<>();
        tagTmp.add(tag);

        List<WriteRequest> result = new ArrayList<>();
        for(Tagging tmp : tagging){
            Introduction intro = tmp.getIntroduction();
            result.add(new WriteRequest(intro, tagTmp));
        }

        if(!result.isEmpty())
            return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    // 해시태그 삭제 시 자소서 항목 전체 삭제!
    @ApiOperation(value = "해시태그로 자소서 항목 전체 삭제")
    @DeleteMapping("/hashtag/{tagno}")
    public ResponseEntity<String> deleteTags(@PathVariable int tagno){
        HashTag tag = tagDao.findHashtagByTagno(tagno);
        List<Tagging> tagging = tag.getTaggings();
        for(Tagging tmp : tagging){
            Introduction intro = tmp.getIntroduction();
            introDao.deleteByIntrono(intro.getIntrono());
        }
        tagDao.deleteByTagno(tagno);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
}