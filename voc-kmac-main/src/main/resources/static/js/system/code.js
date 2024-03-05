let $Grid1        = null;
let $Grid2        = null;
let $selectedRowData1 = {};
let $selectedRowData2 = {};

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
    DropdownUtil.makeCodeList('USE_YN', $('#registCodeForm1').find('.d-useYn'));
    DropdownUtil.makeCodeList('USE_YN', $('#registCodeForm2').find('.d-useYn'));

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid1(); });
    $('.btn-wrap .btn-add1').on('click', function(){ popAddCode('1'); });
    $('.btn-save1').on('click', function(){ saveData('1'); });
    $('.btn-wrap .btn-dtl1').on('click', function(){ popDtlCode('1'); });
    $('.btn-delt1').on('click', function(){ deleteData('1'); });

    $('.btn-wrap .btn-add2').on('click', function(){ popAddCode('2'); });
    $('.btn-save2').on('click', function(){ saveData('2'); });
    $('.btn-wrap .btn-dtl2').on('click', function(){ popDtlCode('2'); });
    $('.btn-delt2').on('click', function(){ deleteData('2'); });

    //더블클릭시 상세화면 이동
    $('#listDataTableCode1 tbody').on('dblclick', 'tr', function (e) {
        popDtlCode('1');
    });
    $('#listDataTableCode2 tbody').on('dblclick', 'tr', function (e) {
        popDtlCode('2');
    });

    loadGrid1();
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS1 = {
    columns     : [
        { data: 'code',   className: "text-center" },
        { data: 'codeNm',   className: "text-center"   },
        //{ data: 'useYn',   className: "text-center"   },
        { data: 'regUserNm',   className: "text-center" },
        { data: 'regDt',   className: "text-center" },
        { data: 'modUserNm',   className: "text-center" },
        { data: 'modDt',   className: "text-center" },
    ],
    pageLength : 5,
    scrollCollapse      : true,
};

let GRID_OPTIONS2 = {
    columns     : [
        { data: 'code',   className: "text-center"   },
        { data: 'codeNm',   className: "text-center"   },
        { data: 'refVal1',   className: "text-center"   },
        { data: 'refVal2',   className: "text-center"   },
        { data: 'refVal3',   className: "text-center"   },
        { data: 'dispOrder',   className: "text-center"   },
        { data: 'useYn',   className: "text-center"   },
        { data: 'regUserNm',   className: "text-center" },
        { data: 'regDt',   className: "text-center" },
        { data: 'modUserNm',   className: "text-center" },
        { data: 'modDt',   className: "text-center" },
    ],
    //scrollY             : '300px',
    scrollCollapse      : true,
};

/**
 * Grid 구성
 */
let loadGrid1 = function(){
    let param = $('#searchForm').form('get.values');
    let gridOptions = $.extend(true, {}, GRID_OPTIONS1);
    let url = '/kmacvoc/v1/code/list';

    param.codeType = 'CODE_TYPE';

    $Grid1 = gridUtil.loadGrid("listDataTableCode1", gridOptions, url, param);
    let $DataTable = $('#listDataTableCode1').DataTable();
    $DataTable.on( 'select', function ( e, dt, type, indexes ) {
        if ( type === 'row' ) {
            $selectedRowData1 = $DataTable.rows( indexes ).data()[0];
            loadGrid2();
        }
    });

    //grid 생성 시, 첫번째 row click
    $DataTable.on('draw', function () {
        $DataTable.row(':eq(0)').select();
    })
};
let loadGrid2 = function(){
    let param = {};
    let gridOptions = $.extend(true, {}, GRID_OPTIONS2);
    let url = '/kmacvoc/v1/code/list';

    if($selectedRowData1 == undefined) {
        param.codeType = 'ZZZZZ';   //값이 조회되지 않게 하기위한 더미값
    } else {
        param.codeType = $selectedRowData1.code;
    }
    $Grid2 = gridUtil.loadGrid("listDataTableCode2", gridOptions, url, param);

    let $DataTable = $('#listDataTableCode2').DataTable();
    $DataTable.on( 'select', function ( e, dt, type, indexes ) {
        if ( type === 'row' ) {
            $selectedRowData2 = $DataTable.rows( indexes ).data()[0];
        }
    });
};

/**
 * 등록을 위한 코드상세팝업 오픈
 */
let popAddCode = function(flag){
    if(flag == '1') {
        $('#registCodeForm1').form('clear');
        $('#registCodeForm1').find('#codeSeq').val('0');
        $('.ui.modal-code1').modal('show');
    } else {
        if($selectedRowData1.code == '') {
            alert('코드유형을 선택해 주세요.');
            return;
        }
        $('#registCodeForm2').form('clear');
        // 코드유형 셋팅
        $('#registCodeForm2').form('set.values', {
            codeType: $selectedRowData1.code,
            codeSeq:0
        });
        $('.ui.modal-code2').modal('show');
    }
}

/**
 * 코드상세 팝업
 */
let popDtlCode =  function(flag){
    if(flag == '1') {
        if($selectedRowData.code == undefined) {
            alert('코드유형목록을 선택해 주세요');
            return;
        }
        AjaxUtil.get(
            '/kmacvoc/v1/code/'+$selectedRowData1.codeSeq,
            {},
            function(result){
                if(result && result.data){
                    $('#registCodeForm1').form('clear');
                    $('#registCodeForm1').form('set.values', result.data);
                    $('.btn-delt1').removeClass('blind');
                    $('.ui.modal-code1').modal('show');
                }
            }
        );
    } else {
        if($selectedRowData2.code == undefined) {
            alert('코드목록을 선택해 주세요');
            return;
        }
        AjaxUtil.get(
            '/kmacvoc/v1/code/'+$selectedRowData2.codeSeq,
            {},
            function(result){
                if(result && result.data){
                    $('#registCodeForm2').form('clear');
                    $('#registCodeForm2').form('set.values', result.data);
                    $('.btn-delt2').removeClass('blind');
                    $('.ui.modal-code2').modal('show');
                }
            }
        );
    }
}

/**
 * 데이터 저장
 */
let saveData = function(flag){
    if(flag == '1') $('#registCodeForm1').find('#codeType').val('CODE_TYPE');
    let formData = flag=='1' ? $('#registCodeForm1').serializeObject() : $('#registCodeForm2').serializeObject();
    let url = formData.codeSeq == '0' ? '/kmacvoc/v1/code/add' : '/kmacvoc/v1/code/modify';

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                if(flag == '1') {
                    $('.ui.modal-code1').modal('hide');
                    loadGrid1();
                } else if(flag == '2') {
                    $('.ui.modal-code2').modal('hide');
                    loadGrid2();
                }
            }
        }
    );
}

/**
 * 데이터 삭제
 */
let deleteData = function(flag){

    if(!confirm('코드정보를 삭제하시겠습니까?')) return;

    let codeSeq = flag == '1' ? $('#registCodeForm1').find('#codeSeq').val() : $('#registCodeForm2').find('#codeSeq').val();
    let url = '/kmacvoc/v1/code/remove/'+codeSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                if(flag == '1') {
                    $('.ui.modal-code1').modal('hide');
                    loadGrid1();
                } else if(flag == '2') {
                    $('.ui.modal-code2').modal('hide');
                    loadGrid2();
                }
            }
        }
    );
}
