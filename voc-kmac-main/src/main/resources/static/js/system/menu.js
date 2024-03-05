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
    DropdownUtil.makeCodeList('USE_YN', $('#registForm').find('.d-useYn'));
    DropdownUtil.makeCodeList('USE_YN', $('#registForm').find('.d-menuYn'));

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });
    $('.btn-wrap .btn-add').on('click', function(){ popAddMenu(); });
    $('.btn-save').on('click', function(){ saveData(); });
    $('.btn-wrap .btn-dtl').on('click', function(){ popDtlMenu(); });
    $('.btn-delt').on('click', function(){ deleteData(); });

    //더블클릭시 상세화면 이동
    $('#listDataTableMenu tbody').on('dblclick', 'tr', function (e) {
        popDtlMenu();
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
        { data: 'menuLevl',   className: "text-center"   },
        { data: 'menuId',   className: "text-center"   },
        { data: 'menuNm',   className: "text-center"   },
        { data: 'parentMenuNm',   className: "text-center"   },
        { data: 'menuUrl',   className: "text-center"   },
        { data: 'menuOrdr',   className: "text-center"   },
        { data: 'menuYn',   className: "text-center"   },
        { data: 'useYn',   className: "text-center"   },
        { data: 'regUserNm',   className: "text-center" },
        { data: 'regDt',   className: "text-center" },
        { data: 'modUserNm',   className: "text-center" },
        { data: 'modDt',   className: "text-center" },
    ],
    scrollX: '100%',
    scrollXInner: '2000px',
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/menu/list';
    let param = $('#searchForm').form('get.values');

    $Grid = gridUtil.loadGrid("listDataTableMenu", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 메뉴상세팝업 오픈
 */
let popAddMenu = function(){
    $('#registForm').form('clear');
    $('#registForm').find('#menuSeq').val('0');
    $('#registForm').find('.d-companyCd').dropdown('set selected', $SessionInfo.getCompanyCd());
    $('.ui.modal-menu').modal('show');
}

/**
 * 메뉴상세 팝업
 */
let popDtlMenu =  function(){
    if($selectedRowData.menuSeq == undefined) {
        alert('목록을 선택해 주세요.');
        return;
    }
    AjaxUtil.get(
        '/kmacvoc/v1/menu/'+$selectedRowData.menuSeq,
        {},
        function(result){
            if(result && result.data){
                $('#registForm').form('clear');
                $('#registForm').form('set.values', result.data);
                $('.btn-delt').removeClass('blind');
                $('.ui.modal-menu').modal('show');
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
        alert('회사코드는 필수항목입니다.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#menuId').val() == '') {
        alert('메뉴아이디는 필수항목입니다.');
        $frm.find('#menuId').focus();
        return;
    }
    if($frm.find('#menuNm').val() == '') {
        alert('메뉴명은 필수항목입니다.');
        $frm.find('#menuNm').focus();
        return;
    }

    let formData = $('#registForm').serializeObject();
    let url = formData.menuSeq == '0' ? '/kmacvoc/v1/menu/add' : '/kmacvoc/v1/menu/modify';

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                $('.ui.modal-menu').modal('hide');
                loadGrid(); // grid 조회
            }
        }
    );
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('메뉴정보를 삭제하시겠습니까?')) return;

    let menuSeq = $('#registForm').find('#menuSeq').val();
    let url = '/kmacvoc/v1/menu/remove/'+menuSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                $('.ui.modal-menu').modal('hide');
                loadGrid(); // grid 조회
            }
        }
    );
}
