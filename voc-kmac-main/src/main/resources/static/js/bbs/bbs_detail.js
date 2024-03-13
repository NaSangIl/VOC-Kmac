let $bbsSeq;    //bbs key
let editor;
$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
	$bbsSeq = localStorage.getItem("bbsSeq");
    let bbsSeq = localStorage.getItem("bbsSeq");

    editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        toolbarItems: [
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task'],
            ['table'],
            ['scrollSync'],
        ],
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        height: '100%'
    });

    // event 연결 ----------------
    $('.btn-wrap .btn-go-list').on('click', function(){ goList(); });              //목록
    $('.btn-wrap .btn-save-bbs').on('click', function(){ saveData(); });           //저장
    $('.btn-wrap .btn-delt-bbs').on('click', function(){ deleteData(); });         //삭제
	$('.btn-wrap .btn-add-comments').on('click', function(){ popAddComments(); });  //댓글저장용 팝업오픈
    $('.actions .btn-save-comments').on('click', function(){ saveComments(); });    //댓글저장
    
    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#registForm').find('.d-companyCd'));

	if($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
		//권한이 없는 경우 회사숨김 
		$('tr[id=companyRow]').attr('style', "display:none;");
	}
	
	
    setTimeout(function() {
        if(bbsSeq > 0) searchData(bbsSeq);
        localStorage.removeItem('bbsSeq');
    }, 200);

    loadGrid(bbsSeq);
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
        { data: 'comments',         className: "text-center pre-wrap"},
        { data: 'regUserNm',        className: "text-center"   },
        { data: 'regDt',            className: "text-center"   },
        { data: 'bbsCommentsSeq',
            'render': function (data, type, full, meta) {
				
					var btnStr = "";
					//시스템관리자, 시스템운영자인 경우 모든 댓글 수정,삭제 가능
     				if($SessionInfo.getUserAuth().indexOf('100') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1) {
						btnStr = "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>";
                    	btnStr += "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";
					}
					
					//관리자인 경우 자신의 소속회사의 모든 댓글 수정, 삭제 가능
					if($SessionInfo.getUserAuth().indexOf('200') > -1 && $SessionInfo.getCompanyCd() == $('#companyCd').val()){
						btnStr = "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>";
                    	btnStr += "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";
					}
					
					
					//시스템관리자, 시스템운영자, 관리자 모두 아닌 경우
			     	if($SessionInfo.getUserAuth().indexOf('000') < 0 && $SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('200') < 0) {
						//자신의 댓글만 수정/삭제 가능
						if(full.regUserNo == $SessionInfo.getUserSeq()){
							btnStr = "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>";
                    		btnStr += "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";
						}
					}
					
                return  btnStr;
            }
        }
    ],
};

/**
 * 댓글 Grid 구성
 */
let loadGrid = function(bbsSeq){
    if(ObjectUtil.isEmpty(bbsSeq)) bbsSeq=-1;

    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/bbs/comments/list';
    let param = {"bbsSeq":bbsSeq};

    gridUtil.loadGrid("listDataTableComments", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function(){
    goPage('/bbs/bbslist');
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
                $('#registForm').form('clear');
                $('#registForm').form('set.values', d);

                editor.setHTML(d.contents, false);

                // 첨부파일표시
                d.fileList.forEach(file => {
                    $('.bbsFilesDiv').append(`
                    <li id="${file.fileSeq}">
                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
                        <button data-index='${file.fileSeq}' onclick='javascript:deleteFile(${file.fileSeq});' class='btn-file-delt'></button>
                    </li>`);
                });

                $('.btn-file-delt').on('click', function(e){
                    e.preventDefault();
                });

                $('.btn-delt-bbs, .viewDiv').removeClass('blind');   //삭제버튼,이력 표시
            }
        }
    );
}

/**
 * 데이터 저장
 */
let saveData = function(){
    let $frm = $('#registForm');
    let contents = editor.getHTML();

    if($frm.find('#companyCd').val() == '') {
        alert('회사코드는 필수항목입니다.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#title').val() == '') {
        alert('제목은 필수항목입니다.');
        $frm.find('#title').focus();
        return;
    }
    if(contents == '') {
        alert('내용은 필수항목입니다.');
        $frm.find('#contents').focus();
        return;
    }

    $frm.find('#bbsTypeCd').val('NOTICE');
    $frm.find('#contents').val(contents);

    document.querySelector('#files').files = dataTranster.files;

    let form = $("#registForm")[0];
    let formData = new FormData(form);

    let bbsSeq = $frm.find('#bbsSeq').val();
    let url = bbsSeq == '0' ? '/kmacvoc/v1/bbs/add' : '/kmacvoc/v1/bbs/modify';

	Util.showLoading();
    $.ajax({
        method: "POST",
        url: url,
        data: formData,
        contentType: false,
        processData: false
    })
    .done(function(result) {
		Util.hideLoading();
        if(result && result.messageCode == '0000'){
            alert(result.data.rtnMessage);
            goList();
        }
    }).fail(function(xhr, status, error) {
		Util.hideLoading();
	});
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('공지사항 정보를 삭제하시겠습니까?')) return;

    let bbsSeq = $('#registForm').find('#bbsSeq').val();
    let url = '/kmacvoc/v1/bbs/remove/'+bbsSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                goList();
            }
        }
    );
}

/**
 * 파일삭제
 */
let deleteFile = function(fileSeq){
    if(!confirm('파일을 삭제하시겠습니까?')) return;

    let url = '/kmacvoc/v1/file/remove/'+fileSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                $('.file-list').find('#'+fileSeq).remove();
            }
        }
    );
}


/**
 * 댓글창 오픈
 */
let popAddComments = function(){
    $('.modal-comments').find('#comments').val('');
    $('.modal-comments').find('#bbsCommentsSeq').val(0);
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
                loadGrid($bbsSeq);
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
                loadGrid($bbsSeq);
            }
        }
    );
}