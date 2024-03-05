package kr.co.kmac.main.controller;

import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.system.dto.MenuDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 메인 ViewController 클래스
 * 
 * @ClassName AuthViewController.java
 * @Description 메인 ViewController 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Controller
@RequestMapping("")
public class MainViewController {
    @Autowired
    private MenuService service;

    // 헤더화면 오픈
    @GetMapping("/main")
    public String view(Model model) {
        return "pages/main";
    }

    @RequestMapping("/common/authError")
    public String error(Model model) {
        return "error/403";
    }

    @GetMapping("/main/menu")
    public String getMenu(HttpServletRequest req, Model model) {
        //로그인 정보를 통해 권한에 해당하는 메뉴 조회
        MenuDto.AuthRequest param = MenuDto.AuthRequest.builder().build();
        UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
        param.setCompanyCd(loginInfo.getCompanyCd());
        param.setUserAuthCodeList(loginInfo.getUserAuthCodeList());

        model.addAttribute("menuList", service.getHeaderMenuList(param));

        return "fragments/header :: headerMenu";
    }
}
