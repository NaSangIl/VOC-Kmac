let $bbsSeq;    //bbs key
let $backPage;  //이전 페이지
$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    $bbsSeq = localStorage.getItem("bbsSeq");
    $backPage = localStorage.getItem("backPage");

    if($backPage  == '/main') { //메인화면에서 호출된 경우, 목록=>메인으로 버튼명 변경
        $('.btn-wrap .btn-go-list').text('메인');
    }

    // event 연결 ----------------
    $('.btn-wrap .btn-go-list').on('click', function(){ goList(); });               //목록
    $('.btn-wrap .btn-add-comments').on('click', function(){ popAddComments(); });  //댓글저장용 팝업오픈
    $('.actions .btn-save-comments').on('click', function(){ saveComments(); });    //댓글저장

    setTimeout(function() {
        if($bbsSeq > 0) searchData($bbsSeq);
        localStorage.removeItem('bbsSeq');
        localStorage.removeItem('backPage');
        loadGrid();
    }, 200);
}

/**
 * 댓글 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'bbsCommentsSeq',   className: "text-center",
            'render': function (data, type, full, meta) {
                return  meta.row+1;
            }
        },
        { data: 'comments',         className: "text-center"   },
        { data: 'regUserNm',        className: "text-center"   },
        { data: 'regDt',            className: "text-center"   },
        { data: 'bbsCommentsSeq',
            'render': function (data, type, full, meta) {
                return  "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>" +
                    "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";
            }
        },
    ],
};

/**
 * 댓글 Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/bbs/comments/list';
    let param = {"bbsSeq":$bbsSeq};

    gridUtil.loadGrid("listDataTableComments", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function(){
    goPage($backPage);
}

/**
 * 데이터 조회
 */
let searchData =  function(key){
    AjaxUtil.get(
        '/kmacvoc/v1/bbs/'+key,
        {},
        function(result){
            if(result && result.data){
                let d = result.data;

                $('#companyNm').html(d.companyNm);
                $('#bbsSeq').html(d.bbsSeq);
                $('#bbsTypeCd').html(d.bbsTypeCd);
                $('#title').html(d.title);
                $('#contents').html(d.contents);
                $('#regDt').html(d.regDt);
                $('#regUserNm').html(d.regUserNm);
                $('#hit').html(d.hit);

                // 첨부파일표시
                d.fileList.forEach(file => {
                    $('.bbsFilesDiv').append(`
                    <li id="${file.fileSeq}">
                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
                        <button data-index='${file.fileSeq}' onclick='javascript:deleteFile(${file.fileSeq});' class='btn-file-delt'></button>
                    </li>`);
                });

                $('.btn-delt-bbs, .viewDiv').removeClass('blind');   //삭제버튼,이력 표시
            }
        }
    );
}

/**
 * 댓글창 오픈
 */
let popAddComments = function(){
    $('.modal-comments').find('#comments').html('');
    $('.ui.modal-comments').modal('show');
}

/**
 * 댓글상세 오픈
 */
let popDtlComments = function(bbsCommentsSeq){
    AjaxUtil.get(
        '/kmacvoc/v1/bbs/comments/'+bbsCommentsSeq,
        {},
        function(result){
            if(result && result.data){
                let d = result.data;
                $('.modal-comments').find('#bbsCommentsSeq').val(d.bbsCommentsSeq);
                $('.modal-comments').find('#comments').val(d.comments);
            }
        }
    );

    $('.ui.modal-comments').modal('show');
}

/**
 * 댓글 저장
 */
let saveComments = function(){
    let comments = $('.modal-comments').find('#comments').val();
    let bbsCommentsSeq = $('.modal-comments').find('#bbsCommentsSeq').val();
    let param = {"bbsSeq":$bbsSeq, "bbsCommentsSeq":bbsCommentsSeq, "comments":comments};
    let url = bbsCommentsSeq > 0 ? '/kmacvoc/v1/bbs/comments/modify' : '/kmacvoc/v1/bbs/comments/add';

    AjaxUtil.post(
        url,
        JSON.stringify(param),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                loadGrid();
                $('.ui.modal-comments').modal('hide');
            }
        }
    );
}

/**
 * 댓글 삭제
 */
let deleteComments = function(bbsCommentsSeq){

    if(!confirm('댓글을 삭제 하시겠습니까?')) return;

    let url = '/kmacvoc/v1/bbs/comments/remove/'+bbsCommentsSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                loadGrid();
            }
        }
    );
}
