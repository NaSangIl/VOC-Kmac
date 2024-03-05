let $GridAuth        = null;
let $GridMenu        = null;
let $selectedRowData1 = {};

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    // dropdown 생성 ----------------

    // event 연결 ----------------
    $('.btn-save').on('click', function(){ saveData(); });

    setTimeout(function() {
        loadGridAuth();
    }, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS_AUTH = {
    columns     : [
        { data: 'authSeq',    className: "select-checkbox",
            'render': function (data, type, full, meta) {
                return '<input type="radio" value="'+data+'" />';
            }
        },
        { data: 'authCd',   className: "text-center"   },
        { data: 'authNm',   className: "text-center"   },
    ],
    paging: false,
    info: false,
};
let GRID_OPTIONS_MENU = {
    columns     : [
        { data: 'menuSeq',    className: "select-checkbox",
            render: function (data,type,row) {
                return '<input type="checkbox" name="menuSeq" value="'+data+'">';
            }
        },
        { data: 'menuId',   className: "text-center"   },
        { data: 'menuNm',   className: "text-center"   },
        { data: 'menuLevl',   className: "text-center"   },
        { data: 'menuUrl',   className: "text-center"   },
        { data: 'menuYn',   className: "text-center"   },
    ],
    rowCallback: function( row, data ) {
        if(!ObjectUtil.isEmpty(data.authCd))  {
            $(row).addClass('selected');
        }
    },
    paging: false,
    info: false,
    select: {style: 'multi'},
    scrollY: '500px',
};

/**
 * Grid 구성
 */
let loadGridAuth = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS_AUTH);
    let url = '/kmacvoc/v1/auth/list';
    let param = {'start':-1};

    $GridAuth = gridUtil.loadGrid("listDataTableAuth", gridOptions, url, param);

    let $DataTable = $('#listDataTableAuth').DataTable();
    $DataTable.on( 'select', function ( e, dt, type, indexes ) {
        if ( type === 'row' ) {
            $selectedRowData1 = $DataTable.rows( indexes ).data()[0];
            loadGridMenu();
        }
    });

    //grid 생성 시, 첫번째 row click
    $DataTable.on('draw', function () {
        $DataTable.row(':eq(0)').select();
    })
};
let loadGridMenu = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS_MENU);
    let url = '/kmacvoc/v1/authmenu/list';
    let param = {'authCd':$selectedRowData1.authCd};

    $GridMenu = gridUtil.loadGrid("listDataTableMenu", gridOptions, url, param);
};

/**
 * 데이터 저장
 */
let saveData = function(){
    let param = {'authCd':$selectedRowData1.authCd};
    let menuSeqs = [];
    $('#listDataTableMenu tr').map(function(){
        if($(this).hasClass('selected')) {
            menuSeqs.push($(this).find('input[name=menuSeq]').val());
        }
    });
    param.menuSeqs = menuSeqs;

    let url = '/kmacvoc/v1/authmenu/add';
    AjaxUtil.post(
        url,
        JSON.stringify(param),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                loadGridMenu(); // grid 조회
            }
        }
    );
}