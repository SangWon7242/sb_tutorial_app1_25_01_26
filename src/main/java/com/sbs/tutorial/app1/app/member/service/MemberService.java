package com.sbs.tutorial.app1.app.member.service;

import com.sbs.tutorial.app1.app.member.entity.Member;
import com.sbs.tutorial.app1.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
  @Value("${custom.genFileDirPath}")
  private String genFileDirPath;

  private final MemberRepository memberRepository;

  public Member getMemberByUsername(String username) {
    return memberRepository.findByUsername(username).orElse(null);
  }

  public Member join(String username, String password, String email, MultipartFile profileImg) {
    String profileImgRelPath = "member/" + UUID.randomUUID().toString() + ".png";
    File profileImgFile = new File(genFileDirPath + "/" + profileImgRelPath);

    if(!profileImgFile.exists()) {
      profileImgFile.mkdirs(); // 해당 폴더가 존재하지 않으면 생성
    }

    try {
      profileImg.transferTo(profileImgFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Member member = Member.builder()
        .username(username)
        .password(password)
        .email(email)
        .profileImg(profileImgRelPath)
        .build();

    memberRepository.save(member);

    return member;
  }
}
