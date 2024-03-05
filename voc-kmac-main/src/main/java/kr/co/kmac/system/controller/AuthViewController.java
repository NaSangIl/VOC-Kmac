package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 권한 ViewController 클래스
 *
 * @ClassName AuthViewController.java
 * @Description 권한 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system")
public class AuthViewController {

    // 권한관리 화면 오픈
    @GetMapping("/auth")
    public String view(Model model) {
        return "pages/system/auth";
    }

    // 권한사용자매핑관리 목록화면 오픈
    @GetMapping("/authuserlist")
    public String viewList(Model model) {
        return "pages/system/authuser_list";
    }

    // 권한사용자매핑관리 목록화면 오픈
    @GetMapping("/authuserdetail")
    public String viewDetail(Model model) {
        return "pages/system/authuser_detail";
    }

    // 권한사용자매핑관리 목록화면 오픈 (회사)
    @GetMapping("/companyauthuserlist")
    public String viewListCompany(Model model) {
        return "pages/system/company_authuser_list";
    }

    // 권한사용자매핑관리 목록화면 오픈 (회사)
    @GetMapping("/companyauthuserdetail")
    public String viewDetailCompany(Model model) {
        return "pages/system/company_authuser_detail";
    }

    // 권한메뉴매핑관리 목록화면 오픈
    @GetMapping("/authmenu")
    public String viewAuthmenu(Model model) {
        return "pages/system/authmenu";
    }
}
