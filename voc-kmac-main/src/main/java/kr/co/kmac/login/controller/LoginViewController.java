package kr.co.kmac.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 로그인 ViewController 클래스
 * 
 * @ClassName LoginViewController.java
 * @Description 로그인 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("")
public class LoginViewController {
    // 로그인 화면 오픈
    @GetMapping("/")
    public String login(Model model) {
        return "pages/login";
    }


    /**
     * @Description 로그아웃 처리
     * @author mjkim
     * @since 2023. 9. 18.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if(session != null) session.invalidate();
        return "redirect:/";
    }
}
