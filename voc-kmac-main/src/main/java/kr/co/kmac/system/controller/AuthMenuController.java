package kr.co.kmac.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.AuthMenuDto;
import kr.co.kmac.system.service.AuthMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 권한메뉴매핑정보 Controller
 * 
 * @ClassName AuthMenuController.java
 * @Description 권한
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "AuthMenuController", description = "권한메뉴매핑 API")
@RestController
@RequestMapping("/kmacvoc/v1/authmenu")
public class AuthMenuController extends BaseController
{
    @Autowired
    private AuthMenuService service;

    /**
     * 권한메뉴 목록 조회
     * 
     * @param param 권한 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 권한메뉴 목록 조회", description = "권한메뉴 목록 조회")
    @GetMapping("/list")
    public ResponseObject<AuthMenuDto.ListResponse> getAuthMenuList(HttpServletRequest req, AuthMenuDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getAuthMenuList(param));
    }

    /**
     * 권한메뉴 등록
     *ss
     * @param param 등록할 권한 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "권한메뉴 등록", description = "권한메뉴 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertAuthMenu(HttpServletRequest req, @RequestBody AuthMenuDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertAuthMenu(param));
    }

    /**end
     * 권한메뉴 삭제
     * 
     * @param param 삭제할 권한 객체
     */
    @Operation(summary = "권한메뉴 삭제", description = "권한메뉴정보를 삭제한다.")
    @PostMapping("/remove")
    public ResponseObject deleteAuthMenu(HttpServletRequest req, @RequestBody AuthMenuDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.deleteAuthMenu(param));
    }
}
