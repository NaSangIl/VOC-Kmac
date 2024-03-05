package kr.co.kmac.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회사지정코드마스터 ViewController 클래스
 * 
 * @ClassName CustomCodeViewController.java
 * @Description 회사지정코드마스터 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/system")
public class CustomCodeViewController {

    // VOC유형관리 목록화면 오픈
    @GetMapping("/voctypelist")
    public String view(Model model) {
        model.addAttribute("customType", "VOC_TYPE");
        return "pages/system/voctype_list";
    }

    // VOC유형관리 상세화면 오픈
    @GetMapping("/voctypedetail")
    public String viewDetail(Model model) {
        model.addAttribute("customType", "VOC_TYPE");
        return "pages/system/voctype_detail";
    }

    // VOC처리유형관리 목록화면 오픈
    @GetMapping("/vocacttypelist")
    public String actView(Model model) {
        model.addAttribute("customType", "VOC_ACT_TYPE");
        return "pages/system/vocacttype_list";
    }

    // VOC처리유형관리 상세화면 오픈
    @GetMapping("/vocacttypedetail")
    public String actViewDetail(Model model) {
        model.addAttribute("customType", "VOC_ACT_TYPE");
        return "pages/system/vocacttype_detail";
    }
}
