package kr.co.kmac.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 현황 ViewController 클래스
 * 
 * @ClassName StatisticsViewController.java
 * @Description 현황 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsViewController {

    // 기간별VOC현황 화면 오픈
    @GetMapping("/period")
    public String viewPeriod(Model model) {
        return "pages/statistics/statistics_period";
    }

    // 유형별VOC현황 화면 오픈
    @GetMapping("/voctype")
    public String viewVoctype(Model model) {
        return "pages/statistics/statistics_voctype";
    }

    // 채널별VOC현황 화면 오픈
    @GetMapping("/channel")
    public String viewChannel(Model model) {
        return "pages/statistics/statistics_channel";
    }

    // 처리유형별VOC현황 화면 오픈
    @GetMapping("/acttype")
    public String viewActtype(Model model) {
        return "pages/statistics/statistics_acttype";
    }

    // 처리기간별VOC현황 화면 오픈
    @GetMapping("/actperiod")
    public String viewActperiod(Model model) {
        return "pages/statistics/statistics_actperiod";
    }
}
