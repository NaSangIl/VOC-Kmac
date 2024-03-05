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
public class VocMstViewController {

    // VOC목록화면 오픈
    @GetMapping("/voclist")
    public String view(Model model) {
        return "pages/voc/voc_list";
    }

    // VOC상세화면 오픈
    @GetMapping("/vocdetail")
    public String viewDetail(Model model) {
    	model.addAttribute("vocViewType", "R");
        return "pages/voc/voc_detail";
    }

    // VOC등록화면 오픈
    @GetMapping("/regist")
    public String viewRegist(Model model) {
    	model.addAttribute("vocViewType", "I");
        return "pages/voc/voc_detail";
    }

    // VOC상세화면 오픈
    @GetMapping("/vocdetailpop")
    public String viewDetailPop(Model model) {
        return "pages/voc/voc_detail_pop";
    }
}
