package com.web.blog.controller.account;

import java.io.IOException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.UserInfoRequest;
import com.web.blog.model.user.DesRequest;
import com.web.blog.model.user.User;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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
public class AccountController {

    @Autowired UserDao userDao;

    @Autowired public JavaMailSender javaMailSender;
    @Autowired public SpringTemplateEngine templateEngine;

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(
        @RequestParam(required = true)final String uid,
        @RequestParam(required = true)final String password,
        HttpSession session
    ) {

        Optional<User> userOpt = userDao.findUserByUidAndPassword(uid, password);
        ResponseEntity response = null;
        System
            .out
            .println("in here");

        if (userOpt.isPresent()) {

            // 로그인 세션 저장
            session.setAttribute("user", userOpt);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/account/logout")
    @ApiOperation(value = "로그아웃")
    public Object logout(HttpSession session) {

        ResponseEntity response = null;
        session.invalidate();

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;

    }

    @PostMapping("/account/join")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request)throws IOException, MessagingException {

        ResponseEntity response = null;

        System
            .out
            .println("Join in");

        String email = request.getEmail();
        String uid = request.getUid();
        String password = request.getPassword();
        String name = request.getName();
        String phone = request.getPhone();
        String interest1 = request.getInterest1();
        String interest2 = request.getInterest2();

        String description = request.getDescription();
        if (description == null) {
            description = "";
        }

        String img = request.getImg();
        if (img == null) {
            img = "";
        }

        Optional<User> userOptByUid = userDao.findByUid(uid);
        Optional<User> userOptByEmail = userDao.findByEmail(email);

        if (!userOptByUid.isPresent() && !userOptByEmail.isPresent()) {

            User user = new User(
                uid,
                password,
                email,
                name,
                phone,
                interest1,
                interest2,
                description,
                img
            );

            User insertResult = userDao.save(user);

            System
                .out
                .println(insertResult);

            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";

            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            if (userOptByUid.isPresent()) {

                final BasicResponse result = new BasicResponse();
                result.status = false;
                result.data = "Uid";
                response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else if (userOptByEmail.isPresent()) {

                final BasicResponse result = new BasicResponse();
                result.status = false;
                result.data = "Email";
                response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else {

                final BasicResponse result = new BasicResponse();
                result.status = false;
                result.data = "Uid&Email";
                response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        return response;
    }

    // 유저 정보 반환
    @PostMapping("/account/userinfo")
    @ApiOperation(value = "유저 정보 반환하기")
    public Object userinfo(@Valid @RequestBody UserInfoRequest request)throws IOException, MessagingException {
        String uid = request.getUid();
        User user = userDao.findUserByUid(uid);

        if (user == null) 
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        else 
            return new ResponseEntity<User>(user, HttpStatus.OK);

        }
    
    //탈퇴하기
    @DeleteMapping("/account/delete")
    @ApiOperation(value = " 탈퇴하기")
    public Object delete(HttpSession session)throws IOException, MessagingException {

        User user = (User)session.getAttribute("user");
        String uid = user.getUid();
        userDao.deleteByUid(uid);
        session.invalidate();

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    //데이터 수정
    @PutMapping("/account/update")
    @ApiOperation(value = "수정하기")
    public Object update(@Valid @RequestBody SignupRequest request)throws IOException, MessagingException {

        String email = request.getEmail();
        String uid = request.getUid();
        String password = request.getPassword();
        String name = request.getName();
        String phone = request.getPhone();
        String interest1 = request.getInterest1();
        String interest2 = request.getInterest2();
        String description = request.getDescription();
        String img = request.getImg();

        User user = new User(
            uid,
            password,
            email,
            name,
            phone,
            interest1,
            interest2,
            description,
            img
        );
        userDao.save(user);

        // 화면에 표시할 유저 정보.
        User userData = userDao.findUserByEmail(email);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //내 소개 수정
    @PutMapping("/account/update/description")
    @ApiOperation(value = "소개 수정하기")
    public Object desUpdate(@Valid @RequestBody DesRequest request)throws IOException, MessagingException {

        String uid = request.getUid();
        String description = request.getDescription();

        User user = userDao.findUserByUid(uid);
        user.setDescription(description);
        userDao.save(user);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account/email_auth")
    @ApiOperation(value = "이메일인증")
    public Object sendMail(@RequestParam(required = true)final String email)throws MessagingException, IOException {
        String sendTo = email; //= email;//보내려는 이메일 주소 작성
        String mailTitle = "인증이다";
        int randomNum = (int)(Math.random() * (9999 - 1000)) + 1000;
        String mailContent = "인증번호 : " + Integer.toString(randomNum);
        System
            .out
            .println("메일 인증 시도");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mailTitle);
        helper.setTo(sendTo);
        helper.setText(mailContent, true);
        helper.setFrom("tt");

        javaMailSender.send(message);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = Integer.toString(randomNum);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}