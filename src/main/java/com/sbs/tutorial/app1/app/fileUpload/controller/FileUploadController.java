package com.sbs.tutorial.app1.app.fileUpload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class FileUploadController {
  @Value("${custom.genFileDirPath}")
  private String genFileDirPath;

  @RequestMapping("")
  @ResponseBody
  public String upload(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2) {

    File directory = new File(genFileDirPath);

    // 해당 디렉토리가 존재하지 않으면 설정한 경로로 폴더생성
    if(!directory.exists()) {
      directory.mkdir();
    }

    // 파일 저장
    try {
      img1.transferTo(new File(directory, "1.png"));
      img2.transferTo(new File(directory, "2.png"));
      return "파일 업로드 완료";
    } catch (IOException e) {
      e.printStackTrace();
      return "파일 업로드 실패 : " + e.getMessage();
    }
  }
}
