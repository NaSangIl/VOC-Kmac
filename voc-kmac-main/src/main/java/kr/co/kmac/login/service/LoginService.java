package kr.co.kmac.login.service;

import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.system.dto.LoginLogDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.mapper.UserMapper;
import kr.co.kmac.system.service.LoginLogService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 로그인 서비스 클래스
 * 
 * @ClassName LoginService.java
 * @Description 로그인 서비스 클래스
 * @author dongkum
 * @since 2022. 3. 7.
 */
@Service
public class LoginService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LoginLogService loginLogService;

	/**
	 * @Description 로그인 체크
	 * @author dongkum
	 * @since 2022. 3. 7.
	 */
	public UserDto.LoginInfo loginCheck(UserDto.LoginRequest param) {
		UserDto.LoginInfo loginInfo = UserDto.LoginInfo.builder().build();

		// 필수항목체크
		if(ObjectUtils.isEmpty(param.getCompanyCd())) {
			throw new ApiBusinessException("error.1047", new String[] {"회사코드"});
		}
		if(ObjectUtils.isEmpty(param.getUserId())) {
			throw new ApiBusinessException("error.1047", new String[] {"아이디"});
		}
		if(ObjectUtils.isEmpty(param.getPw())) {
			throw new ApiBusinessException("error.1047", new String[] {"비밀번호"});
		}

		int idChk = userMapper.getIdCheck(param);
		if (idChk <= 0) {
			// 아이디가 존재하지 않을 경우
			throw new ApiBusinessException("error.9200", "아이디가 존재하지 않습니다.");
		}

		int idPwdChk = userMapper.getIdPwdCheck(param);
		if (idPwdChk <= 0) {
			// 아이디, 패스워드가 존재하지 않을 경우
			throw new ApiBusinessException("error.9200", "패스워드가 일치하지 않습니다.");
		}

		UserDto.Info userInfo = userMapper.getLogin(param);

		// 로그용 data setting
		LoginLogDto.Info loginLogParam = LoginLogDto.Info.builder().build();
		loginLogParam.setCompanyCd(param.getCompanyCd());
		loginLogParam.setUserId(param.getUserId());

		if(ObjectUtils.isEmpty(userInfo)) {
			loginLogParam.setSuccessYn("N");
			loginLogService.insertLoginLog(loginLogParam);
			throw new ApiBusinessException("error.9000","로그인 정보를 찾을 수 없습니다.");
		}

		loginLogParam.setSuccessYn("Y");
		loginLogService.insertLoginLog(loginLogParam);

		BeanUtils.copyProperties(userInfo, loginInfo);

		return loginInfo;
	}


}
