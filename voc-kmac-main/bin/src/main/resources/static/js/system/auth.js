let $Grid        = null;
$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    // 초기 화면 구성 ----------------

    // dropdown 생성 ----------------
    DropdownUtil.makeCompList($('.d-companyCd'));  // 회사코드
    DropdownUtil.makeCodeList('USE_YN', $('#registForm').find('.d-useYn'));

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });
    $('.btn-wrap .btn-add').on('click', function(){ popAddAuth(); });
    $('.btn-save').on('click', function(){ saveData(); });
    $('.btn-wrap .btn-dtl').on('click', function(){ popDtlAuth(); });
    $('.btn-delt').on('click', function(){ deleteData(); });

    //더블클릭시 상세화면 이동
    $('#listDataTableAuth tbody').on('dblclick', 'tr', function (e) {
        popDtlAuth();
    });

    setTimeout(function() {
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'authCd',   className: "text-center"   },
        { data: 'authNm',   className: "text-center"   },
        { data: 'authDesc',   className: "text-left"   },
		{ data: 'systemYn',   className: "text-center" },
        { data: 'regUserNm',   className: "text-center" },
        { data: 'regDt',   className: "text-center" },
        { data: 'modUserNm',   className: "text-center" },
        { data: 'modDt',   className: "text-center" },
    ],
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/auth/list';
    let param = $('#searchForm').form('get.values');

    $Grid = gridUtil.loadGrid("listDataTableAuth", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 권한상세팝업 오픈
 */
let popAddAuth = function(){
	$('.btn-delt').addClass('blind');
    $('#registForm').form('clear');
    $('#registForm').find('#authSeq').val('0');
    $('.ui.modal-auth').modal('show');
}

/**
 * 권한상세 팝업
 */
let popDtlAuth =  function(){

	$('.btn-delt').addClass('blind');
    AjaxUtil.get(
        '/kmacvoc/v1/auth/'+$selectedRowData.authSeq,
        {},
        function(result){
            if(result && result.data){
                $('#registForm').form('clear');
                $('#registForm').form('set.values', result.data);
                $('.btn-delt').removeClass('blind');
                $('.ui.modal-auth').modal('show');
            }
        }
    );
}

/**
 * 데이터 저장
 */
let saveData = function(){
    let $frm = $('#registForm');

    if($frm.find('#authCd').val() == '') {
        alert('권한코드를 입력해주세요.');
        $frm.find('#authCd').focus();
        return;
    }
    if($frm.find('#authNm').val() == '') {
        alert('권한명을 입력해주세요.');
        $frm.find('#authNm').focus();
        return;
    }

	if($frm.find('#useYn').val() == '') {
        alert('사용여부를 선택해주세요.');
        $frm.find('#useYn').focus();
        return;
    }

	if($frm.find('#systemYn').val() == '') {
        alert('시스템권한을 선택해주세요.');
        $frm.find('#systemYn').focus();
        return;
    }

    let formData = $('#registForm').serializeObject();
    let url = formData.authSeq == '0' ? '/kmacvoc/v1/auth/add' : '/kmacvoc/v1/auth/modify';

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                $('.ui.modal-auth').modal('hide');
                loadGrid(); // grid 조회
            }
        }
    );
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('권한정보를 삭제하시겠습니까?')) return;

    let authSeq = $('#registForm').find('#authSeq').val();
    let url = '/kmacvoc/v1/auth/remove/'+authSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                $('.ui.modal-auth').modal('hide');
                loadGrid(); // grid 조회
            }
        }
    );
}
