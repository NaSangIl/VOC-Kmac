package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 메뉴 ViewController 클래스
 * 
 * @ClassName MenuViewController.java
 * @Description 메뉴 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system/menu")
public class MenuViewController {

    // 화면 오픈
    @GetMapping
    public String view(Model model) {
        return "pages/system/menu";
    }
}
