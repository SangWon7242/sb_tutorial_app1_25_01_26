package com.sbs.tutorial.app1.app.member.controller;

import com.sbs.tutorial.app1.app.member.entity.Member;
import com.sbs.tutorial.app1.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/join")
  public String showJoin() {
    return "member/join";
  }

  @PostMapping("/join")
  @ResponseBody
  public String join(String username, String password, String email, MultipartFile profileImg) {
    Member oldMember = memberService.getMemberByUsername(username);

    if (oldMember != null) {
      return "이미 가입된 회원입니다.";
    }

    Member member = memberService.join(username, "{noop}" + password, email, profileImg);


    return "가입완료";
  }
}
