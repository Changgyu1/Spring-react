package com.kh;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.kakao.service.KaKaoService;
import com.kh.naver.service.NaverService;

import lombok.RequiredArgsConstructor;

// 응답받은 결과를 담기위한 곳
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final KaKaoService kakaoService;
    private final NaverService naverService;
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        model.addAttribute("naverUrl", naverService.getNaverLogin());
        return "index";
    }
    
    
}
