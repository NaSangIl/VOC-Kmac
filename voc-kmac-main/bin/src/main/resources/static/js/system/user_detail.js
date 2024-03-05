$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    // event 연결 ----------------
    $('.btn-wrap .btn-go-list').on('click', function(){ goList(); });           //목록
    $('.btn-wrap .btn-save-user').on('click', function(){ saveData(); });       //저장
    $('.btn-wrap .btn-delt-user').on('click', function(){ deleteData(); });     //삭제
    $('.btn-wrap .btn-check-user').on('click', function(){ checkUserId(); });   //중복체크
    $('.btn-wrap .btn-open-pw').on('click', function(){ popPw(); });            //비밀번호변경창 오픈
    $('.btn-update-pw').on('click', function(){ changePw(); });                 //비밀번호변경

    $('#registForm').find('#emailAddr3').on('change', function(){ setMailAddr(); }); //메일dropbox 변경시

    $('#registForm').find('#companyCd').on('change', function(){ $('#registForm').find('#dupChkYn').val(''); }); 	//회사코드 변경시 user 초기화
    $('#registForm').find('#userId').on('change', function(){
    	$('#registForm').find('#userId').val($('#registForm').find('#userId').val().replace(/\s+/g, ''));
    	$('#registForm').find('#dupChkYn').val('');
    });

	document.getElementById("userId").addEventListener("input", function(event) {
	    var inputValue = this.value;
	    var regex = /^[0-9a-z]*$/; // 숫자와 소문자 영문자만 포함하는 정규식

	    // 입력된 값이 숫자와 소문자 영문자로만 이루어져 있는지 확인
	    if (!regex.test(inputValue)) {
	        // 숫자와 소문자 영문자 이외의 문자가 입력된 경우 입력을 취소
	        this.value = inputValue.replace(/[^0-9a-z]/g, '');
	    }
	});

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#registForm').find('.d-companyCd'));
    DropdownUtil.makeCodeList('EMPLOYMENT_YN', $('#registForm').find('.d-employmentYn'));
    DropdownUtil.makeCodeList('EMAIL_ADDR', $('#registForm').find('.d-emailAddr'));
	DropdownUtil.makeAuthList($('#registForm').find('.d-userAuthCodes'));

    setTimeout(function() {
        let userSeq = localStorage.getItem("userSeq");
        if(userSeq > 0) searchData(userSeq);
        //localStorage.removeItem('userSeq');
    }, 200);
}


/**
 * 목록화면 이동
 */
let goList = function(){
    goPage('/system/userlist');
}

/**
 * 중복체크
 */
let checkUserId = function(){
    let param = {
			'userIdForCheck' : $('#registForm').find('#userId').val()
		, 	'companyCd' : $('#registForm').find('#companyCd').val()
	};
    $('#registForm').find('#dupChkYn').val('N');    //중복체크값 초기화

    if($('#registForm').find('#userId').val().replace(/\s+/g, '') == '') {
        alert('사용자ID를 입력하세요.');
        $('#registForm').find('#userId').focus();
        return false;
    }

    AjaxUtil.post(
        '/kmacvoc/v1/user/userIdForCheck',
        JSON.stringify(param),
        function(result){
            if(result && result.data){
                if(result.data?.rtnCnt === 0) {
                    $('#registForm').find('#dupChkYn').val('Y');
                    alert('사용 가능한 사용자ID 입니다.');
                } else {
                    alert('사용자ID가 중복됩니다.');
                }
            }
        }
    );
}

/**
 * 데이터 조회
 */
let searchData =  function(key){
    AjaxUtil.get(
        '/kmacvoc/v1/user/'+key,
        {},
        function(result){
            if(result && result.data){
                let d = result.data;
                if(d.telNo != '') {
                    d.telNo1 = d.telNo.split('-')[0];
                    d.telNo2 = d.telNo.split('-')[1];
                    d.telNo3 = d.telNo.split('-')[2];
                }
                if(d.emailAddr != '') {
                    d.emailAddr1 = d.emailAddr.split('@')[0];
                    d.emailAddr2 = d.emailAddr.split('@')[1];
                }
                d.pwChk = d.pw;
                d.joinDt = Util.dateFormat(d.joinDt,'-');

                $('#registForm').form('clear');
                $('#registForm').form('set.values', d);
                $('#registForm').find('#dupChkYn').val('Y');

                $('.btn-delt-user').removeClass('blind');   //삭제버튼 표시
                $('.btn-open-pw').removeClass('blind');   	//비밀번호변경버튼 표시
                $('.pwGrp').attr('readonly',true);          //비밀번호 입력창 readonly 처리
                $('#userId').attr('readonly',true);         //비밀번호 입력창 readonly 처리

                $('.btn-check-user').remove();          			//중복체크 삭제

                // 권한 셋팅
                if(!!d.userAuthCodes) {
                    let userAuthArr = d.userAuthCodes.split(',');
                    $('.d-userAuthCodes').dropdown("set selected", userAuthArr);
                }
            }
        }
    );
}

/**
 * 데이터 저장
 */
let saveData = function(){
    let $frm = $('#registForm');
    if($frm.find('#companyCd').val() == '') {
        alert('회사를 선택해주세요.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#userId').val() == '') {
        alert('사용자ID는 필수항목입니다.');
        $frm.find('#userId').focus();
        return;
    }
    if($frm.find('#dupChkYn').val() != 'Y') {
        alert('사용자ID의 중복확인은 필수입니다.'); return;
    }
    if($frm.find('#userNm').val() == '') {
        alert('사용자명은 필수항목입니다.');
        $frm.find('#userNm').focus();
        return;
    }
    if($frm.find('#pw').val() == '') {
        alert('암호는 필수항목입니다.');
        $frm.find('#pw').focus();
        return;
    }
    if($frm.find('#pwChk').val() == '') {
        alert('암호확인은 필수항목입니다.');
        $frm.find('#pwChk').focus();
        return;
    }
    if($frm.find('#pw').val() != $frm.find('#pwChk').val()) {
        alert('암호가 일치하지 않습니다.');
        $frm.find('#pw').focus();
        return;
    }
    if($frm.find('#employmentYn').val() == '') {
        alert('재직여부는 필수항목입니다.');
        $frm.find('#employmentYn').focus();
        return;
    }

    $frm.find('#telNo').val($frm.find('#telNo1').val() + '-' + $frm.find('#telNo2').val() + '-' + $frm.find('#telNo3').val());
    $frm.find('#emailAddr').val($frm.find('#emailAddr1').val() + '@' + $frm.find('#emailAddr2').val());
    if($frm.find('#telNo').val() == '--') $frm.find('#telNo').val('');
    if($frm.find('#emailAddr').val() == '@') $frm.find('#emailAddr').val('');

    let formData = $('#registForm').serializeObject();
    let url = formData.userSeq == '0' ? '/kmacvoc/v1/user/add' : '/kmacvoc/v1/user/modify';

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
				localStorage.setItem("userSeq", 0 < formData.userSeq ? formData.userSeq : result?.data?.rtnKey);
				goPage('/system/userdetail');
            }
        }
    );
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('사용자 정보를 삭제하시겠습니까?')) return;

    let userSeq = $('#registForm').find('#userSeq').val();
    let url = '/kmacvoc/v1/user/remove/'+userSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                goList();
            }
        }
    );
}

/**
 * 메일주소 dropbox 선택시 셋팅
 */
let setMailAddr = function(){
    let v = $('#registForm').find('#emailAddr3').val();
    if(v == 'DIRECT_INPUT') {
        v = '';
    }
    $('#registForm').find('#emailAddr2').val(v);
}

/**
 * 비밀번호 변경창 오픈
 */
let popPw = function(){
    $('#pwChg').val('');
    $('#pwChgChk').val('');
    $('.ui.modal-pw').modal('show');
}

/**
 * 비밀번호 변경
 */
let changePw = function() {
    if(!confirm('비밀번호를 변경하시겠습니까?')) return;

    if($('#pwChg').val() == '') {
        alert('암호는 필수항목입니다.');
        $('#pwChg').focus();
        return;
    }
    if($('#pwChgChk').val() == '') {
        alert('암호확인은 필수항목입니다.');
        $('#pwChgChk').focus();
        return;
    }
    if($('#pwChg').val() != $('#pwChgChk').val()) {
        alert('암호가 일치하지 않습니다.');
        $('#pwChg').focus();
        return;
    }

    let url = '/kmacvoc/v1/user/modify/pw';
    let formData = {};
    let userSeq = $('#registForm').find('#userSeq').val();
    let pw = $('#pwUpdateForm').find('#pwChg').val();
    formData['userSeq'] = userSeq;
    formData['pw'] = pw;

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
				localStorage.setItem("userSeq", userSeq);
				goPage('/system/userdetail');
            }
        }
    );
}

