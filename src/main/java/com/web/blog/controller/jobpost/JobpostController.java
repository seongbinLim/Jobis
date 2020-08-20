package com.web.blog.controller.jobpost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.jobposting.JobPost;
import com.web.blog.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

@ApiResponses(value = {
    @ApiResponse(
        code = 401,
        message = "Unauthorized",
        response = BasicResponse.class
    ),
    @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
    @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
    @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)
})

@CrossOrigin(origins = { "http://i3c104.p.ssafy.io:3000", "http://localhost:3000", "http://localhost:8081" })
@RestController
public class JobpostController {
    @GetMapping("/jobpost")
    @ApiOperation(value = "채용공고불러오기")
    public ResponseEntity<List<JobPost>> callIntrobyCompany(HttpSession session)throws Exception {

        User user = (User)session.getAttribute("user");
        String address = null;
        if (user == null) {
            address = "https://oapi.saramin.co.kr/job-search?access-key=yOCBitOkzcoqTJaB3JSlum1Y4qj5f" +
                    "89k9VzAIM95w2ifSSSjOna2&bbs_gb=0&stock=kospi+kosdaq+nasdaq&job_type=&edu_lv=&i" +
                    "nd_cd=";
        } else {
            address = "https://oapi.saramin.co.kr/job-search?access-key=yOCBitOkzcoqTJaB3JSlum1Y4qj5f" +
                    "89k9VzAIM95w2ifSSSjOna2&bbs_gb=0&stock=kospi+kosdaq+nasdaq&job_type=&edu_lv=&i" +
                    "nd_cd=" + user.getInterest1() + "," + user.getInterest2();
        }
        BufferedReader br;
        URL url;
        HttpURLConnection conn;
        String protocol = "GET";

        url = new URL(address);
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(protocol);
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System
            .out
            .println("br : " + br);
        String json = br.readLine();

        JSONParser parser = new JSONParser();

        //json데이터를 넣어 json Object로 만들어 준다
        JSONObject jsonObj = (JSONObject)parser.parse(json);

        JSONObject jobodject = (JSONObject)jsonObj.get("jobs");

        //jobs의 배열을 추출
        JSONArray jobInfoArray = (JSONArray)jobodject.get("job");
        List<JobPost> joblist = new ArrayList<>();
        for (int i = 0; i < jobInfoArray.size(); i++) {
            JobPost newjob = new JobPost();
            JSONObject tmp = (JSONObject)jobInfoArray.get(i);
            String open = (String)tmp.get("opening-timestamp");
            String end = (String)tmp.get("expiration-timestamp");

            Date startdate = changeTime(open);
            Date enddate = changeTime(end);

            System
                .out
                .println(tmp);
            newjob.setId((String)tmp.get("id").toString());
            newjob.setUrl((String)tmp.get("url").toString());
            newjob.setStartdate(startdate);
            newjob.setEnddate(enddate);
            if (tmp.containsKey("keyword")) {
                newjob.setKeyword((String)tmp.get("keyword").toString());
            }
            JSONObject tmp_use = (JSONObject)tmp.get("salary");
            if (tmp_use.containsKey("name")) {
                newjob.setSalary((String)tmp_use.get("name").toString());
            }

            tmp_use = (JSONObject)tmp.get("company");
            System
                .out
                .println(tmp_use);
            tmp_use = (JSONObject)tmp_use.get("detail");
            System
                .out
                .println(tmp_use);
            newjob.setCompany((String)tmp_use.get("name").toString());

            tmp_use = (JSONObject)tmp.get("position");
            System
                .out
                .println(tmp_use);

            if (tmp_use.containsKey("title")) {
                newjob.setTitle((String)tmp_use.get("title").toString());
            }

            JSONObject jobtypeObject = (JSONObject)tmp_use.get("job-type");
            if (jobtypeObject.containsKey("name")) {
                newjob.setJob_type((String)jobtypeObject.get("name").toString());
            }

            JSONObject industryObject = (JSONObject)tmp_use.get("industry");
            if (industryObject.containsKey("name")) {
                newjob.setIndustry((String)industryObject.get("name").toString());
            }

            JSONObject jobcategoryObject = (JSONObject)tmp_use.get("job-category");
            if (jobcategoryObject.containsKey("name")) {
                newjob.setJob_category((String)jobcategoryObject.get("name").toString());
            }

            JSONObject Experience_levelObject = (JSONObject)tmp_use.get("experience-level");
            if (Experience_levelObject.containsKey("name")) {
                newjob.setExperience_level(
                    (String)Experience_levelObject.get("name").toString()
                );
            }

            JSONObject Required_educationObject = (JSONObject)tmp_use.get(
                "required-education-level"
            );
            if (Required_educationObject.containsKey("name")) {
                newjob.setRequired_education_level(
                    (String)Required_educationObject.get("name").toString()
                );
            }

            joblist.add(newjob);
        }

        return new ResponseEntity<List<JobPost>>(joblist, HttpStatus.OK);

    }

    public Date changeTime(final String time) {
        long unixTime = Long.parseLong(time);
        unixTime = unixTime * 1000;
        Date date = new Date(unixTime);
        System
            .out
            .println(date.toString());
        return date;
    }
}