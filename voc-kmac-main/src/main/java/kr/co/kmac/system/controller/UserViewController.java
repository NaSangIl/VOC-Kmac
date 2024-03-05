package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 ViewController 클래스
 *
 * @ClassName UserViewController.java
 * @Description 사용자 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system")
public class UserViewController {

    // 사용자목록화면 오픈
    @GetMapping("/userlist")
    public String view(Model model) {
        return "pages/system/user_list";
    }

    // 사용자상세화면 오픈
    @GetMapping("/userdetail")
    public String viewDetail(Model model) {
        return "pages/system/user_detail";
    }

    // 사용자목록화면 오픈 (회사)
    @GetMapping("/companyuserlist")
    public String viewCompany(Model model) {
        return "pages/system/company_user_list";
    }

    // 사용자상세화면 오픈 (회사)
    @GetMapping("/companyuserdetail")
    public String viewDetailComapny(Model model) {
        return "pages/system/company_user_detail";
    }
}
