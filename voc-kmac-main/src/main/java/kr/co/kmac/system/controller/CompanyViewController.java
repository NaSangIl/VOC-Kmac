package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회사 ViewController 클래스
 * 
 * @ClassName CompanyViewController.java
 * @Description 회사 ViewController 클래스
 * @companyor mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system")
public class CompanyViewController {

    // 회사목록화면 오픈
    @GetMapping("/companylist")
    public String view(Model model) {
        return "pages/system/company_list";
    }

    // 회사상세화면 오픈
    @GetMapping("/companydetail")
    public String viewDetail(Model model) {
        return "pages/system/company_detail";
    }
}
