package kr.co.kmac.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 게시판 ViewController 클래스
 * 
 * @ClassName BbsViewController.java
 * @Description 게시판 ViewController 클래스
 * @author mjkim
 * @since 2023. 11. 13.
 *
 */
@Controller
@RequestMapping("/bbs")
public class BbsViewController {

    // 게시판목록화면 오픈
    @GetMapping("/bbslist")
    public String list(Model model) {
        return "pages/bbs/bbs_list";
    }

    // 게시판 수정용 화면 오픈
    @GetMapping("/bbsdetail")
    public String detail(Model model) {
        return "pages/bbs/bbs_detail";
    }

    // 게시판상세화면 오픈
    @GetMapping("/bbsview")
    public String view(Model model) {
        return "pages/bbs/bbs_view";
    }
}
