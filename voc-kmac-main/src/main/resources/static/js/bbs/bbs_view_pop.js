$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){

    // VOC 정보 조회 ----------------
    let bbsSeq = (new URL(location.href)).searchParams.get('bbsSeq');
    searchData(bbsSeq);
	
    // 모달창 오픈 ----------------
    $('.modal-bbs-detail').modal('show');

    // event 연결 ----------------
    $('.close, .btn-close').on('click', function(){ self.close(); });   //윈도우창 닫기
    
    loadGrid(bbsSeq);

}

let GRID_OPTIONS = {
    columns     : [
        { data: 'bbsCommentsSeq',   className: "text-center",
            'render': function (data, type, full, meta) {
                return  meta.row+1;
            }
        },
        { data: 'comments',         className: "text-center ui-comment-list"   },
        { data: 'regUserNm',        className: "text-center"   },
        { data: 'regDt',            className: "text-center"   },
        { data: 'bbsCommentsSeq',
            'render': function (data, type, full, meta) {
				
				var sbtnHtml = "";
				
				if($SessionInfo.getUserSeq() == full.regUserNo){
					sbtnHtml += "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>";
                	sbtnHtml += "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";	
				}
                return sbtnHtml;
            }
        },
    ],
};

/**
 * 댓글 Grid 구성
 */
let loadGrid = function(key){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/bbs/comments/list';
    let param = {"bbsSeq":key};

    gridUtil.loadGrid("listDataTableComments", gridOptions, url, param);
    
    $('#listDataTableComments').attr('style','width:100%;');
};

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
                    </li>`);
                });
            }
        }
    );
}
