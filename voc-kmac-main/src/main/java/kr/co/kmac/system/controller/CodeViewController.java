package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 코드 ViewController 클래스
 * 
 * @ClassName CodeViewController.java
 * @Description 코드 ViewController 클래스
 * @codeor mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system/code")
public class CodeViewController {

    // 화면 오픈
    @GetMapping
    public String view(Model model) {
        return "pages/system/code";
    }
}
