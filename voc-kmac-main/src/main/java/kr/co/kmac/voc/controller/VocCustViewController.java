package kr.co.kmac.voc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * VOC ViewController 클래스
 * 
 * @ClassName VocMstViewController.java
 * @Description VOC ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/voc")
public class VocCustViewController {

    // 고객목록화면 오픈
    @GetMapping("/custlist")
    public String view(Model model) {
        return "pages/voc/cust_list";
    }

    // 고객상세화면 오픈
    @GetMapping("/custdetail")
    public String viewDetail(Model model) {
        return "pages/voc/cust_detail";
    }
}
