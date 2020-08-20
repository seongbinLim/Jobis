package com.web.blog.controller.introduction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.web.blog.dao.introduction.HashTagDao;
import com.web.blog.dao.introduction.IntroductionDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.introduction.HashTag;
import com.web.blog.model.introduction.Introduction;
import com.web.blog.model.introduction.Tagging;
import com.web.blog.model.introduction.WriteRequest;
import com.web.blog.model.user.UserInfoRequest;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3c104.p.ssafy.io:3000", "http://localhost:3000", "http://localhost:8081" })
@RestController
public class IntroductionController {

    @Autowired
    IntroductionDao introDao;

    @Autowired
    HashTagDao tagDao;

    // 1. 자소서 작성
    @PostMapping("/introduction/create")
    @ApiOperation(value = "자소서작성")
    public Object createIntro(@RequestParam(required = true) String uid, @Valid @RequestBody WriteRequest request) {
        ResponseEntity response = null;

        store(request, uid);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;
    }

    // 자소서 & 해시태그 동시 저장
    @Transactional
    public int store(WriteRequest data, String uid) {
        Introduction newIntro = data.getIntroduction();
        List<HashTag> newTags = data.getHashTag();

        for (HashTag tag : newTags) {
            Tagging newTagging = new Tagging();
            tag.setCnt(1);

            // 이미 등록되어 있는 해시태그랑 uid 들어오면
            HashTag oldTag = tagDao.findHashtagByTagnameAndUid(tag.getTagname(), uid);
            if (oldTag != null) {
                // 카운트 올리기
                oldTag.setCnt(oldTag.getCnt() + 1);
                tag = oldTag;
                System.out.println("이미 있어! 이 uid랑 해시태그!");
            }

            Introduction tmp = introDao.findByIntrono(newIntro.getIntrono());
            newTagging.setIntroduction(newIntro);
            newTagging.setHashtag(tag);
            newIntro.addTagging(newTagging);
            tagDao.save(tag);
            introDao.save(newIntro);
        }

        return (newIntro.getIntrono());
    }

    // 2. 자소서 수정
    // 2.2 변경된 내용을 찾아서 자소서 수정을 저장하는 부분
    @PutMapping("/introduction/{introno}/{uid}")
    @ApiOperation(value = "자소서수정")
    public Object updateIntro(@PathVariable int introno, @Valid @RequestBody WriteRequest request, @PathVariable String uid) {

        // 설정되어있는 관계부터 삭제해야한다
        // cascade 되어있기 때문에 자소서 삭제하면 관계 테이블도 같이 삭제
        // 해시태그 count는 같이 안줄어들기 때문에 직접 해줘야한다.
        Introduction introTmp = introDao.findByIntrono(introno);
        
        List<HashTag> tagsTmp = new ArrayList<>();

        for(int i = 0;i < introTmp.getTaggings().size();i++){
            tagsTmp.add(introTmp.getTaggings().get(i).getHashtag());
            for(HashTag tags : tagsTmp){
                if(tags.getCnt() > 0){
                    tags.setCnt(tags.getCnt() -1);
                }
                tagDao.save(tags);
            }
        }

        introDao.deleteByIntrono(introno);

        // 새로운 관계 설정
        int resNo = store(request, uid);

        Introduction introT = introDao.findByIntrono(resNo);
        List<HashTag> tagT = new ArrayList<>();
        for(int i = 0;i<introT.getTaggings().size();i++){
            tagT.add(introT.getTaggings().get(i).getHashtag());
        }

        WriteRequest result = new WriteRequest(introT, tagT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 3. 자소서 삭제
    @DeleteMapping("/introduction/{introno}")
    @ApiOperation(value = "자소서삭제")
    public Object deleteIntro(@PathVariable int introno) {
        ResponseEntity response = null;

        Introduction introTmp = introDao.findByIntrono(introno);
        List<HashTag> tagsTmp = new ArrayList<>();

        for(int i = 0;i < introTmp.getTaggings().size();i++){
            tagsTmp.add(introTmp.getTaggings().get(i).getHashtag());
            for(HashTag tags : tagsTmp){
                if(tags.getCnt() > 0){
                    tags.setCnt(tags.getCnt() -1);
                }
                tagDao.save(tags);
            }
        }
      
       introDao.deleteByIntrono(introno);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;

    }

    // 4. 자소서 & 해시태그 불러오기(하나만 불러오기)
    @GetMapping("/introduction/{introno}")
    @ApiOperation(value = "introno로 자소서 상세조회")
    public ResponseEntity<WriteRequest> callIntro(@PathVariable int introno) {
        Introduction intro = introDao.findByIntrono(introno);
        List<HashTag> tag = new ArrayList<>();

        for(int i = 0;i<intro.getTaggings().size();i++){
            tag.add(intro.getTaggings().get(i).getHashtag());
        }

        WriteRequest result = new WriteRequest(intro, tag);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 내 자소서 다 불러오기
    @GetMapping("/introduction/uid/{uid}")
    @ApiOperation(value = "uid로 자소서보기")
    public ResponseEntity<List<WriteRequest>> callIntrobyUid(@PathVariable String uid) {
        List<Introduction> list = introDao.findByUid(uid);

        List<WriteRequest> result = new ArrayList<>();
        
        for(Introduction intro : list){
            List<HashTag> tagTmp = new ArrayList<>();
            for(int i = 0;i<intro.getTaggings().size();i++){
                tagTmp.add(intro.getTaggings().get(i).getHashtag());
            }
            result.add(new WriteRequest(intro, tagTmp));
        }

        return new ResponseEntity<List<WriteRequest>>(result, HttpStatus.OK);

    }

    // 같은 회사인 애들 다 가져와야함(같은회사고 같은 유저가 쓴거)
    @PostMapping("/introduction/company")
    @ApiOperation(value = "company로 자소서보기")
    public ResponseEntity<List<WriteRequest>> callIntrobyCompany(@RequestParam(required = true) String company,
            @RequestBody UserInfoRequest request) {
        String uid = request.getUid();
        List<Introduction> list = introDao.findByCompanyAndUid(company, uid);
        List<WriteRequest> result = new ArrayList<>();
        
        for(Introduction intro : list){
            List<HashTag> tagTmp = new ArrayList<>();
            for(int i = 0;i<intro.getTaggings().size();i++){
                tagTmp.add(intro.getTaggings().get(i).getHashtag());
            }
            result.add(new WriteRequest(intro, tagTmp));
        }

        return new ResponseEntity<List<WriteRequest>>(result, HttpStatus.OK);

    }

    @GetMapping("/introduction/startdate/{startdate}")
    @ApiOperation(value = "startdate로자소서보기")
    public ResponseEntity<List<Introduction>> callIntrobyStartdate(@PathVariable Date startdate) {
        List<Introduction> list = introDao.findByStartdate(startdate);
        return new ResponseEntity<List<Introduction>>(list, HttpStatus.OK);

    }

    @GetMapping("/introduction/enddate/{enddate}")
    @ApiOperation(value = "enddate로자소서보기")
    public ResponseEntity<List<Introduction>> callIntrobyEnddate(@PathVariable Date enddate) {
        List<Introduction> list = introDao.findByEnddate(enddate);
        return new ResponseEntity<List<Introduction>>(list, HttpStatus.OK);

    }
}