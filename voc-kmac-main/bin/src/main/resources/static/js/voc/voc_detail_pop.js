$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){

    // VOC 정보 조회 ----------------
    let vocSeq = (new URL(location.href)).searchParams.get('vocSeq');
    searchData(vocSeq);

    // 모달창 오픈 ----------------
    $('.modal-voc-detail').modal('show');

    // event 연결 ----------------
    $('.close, .btn-close').on('click', function(){ self.close(); });   //윈도우창 닫기

}

/**
 * 데이터 조회
 */
let searchData =  function(key){
    let $frm = $('#viewForm');

    AjaxUtil.get(
        '/kmacvoc/v1/voc/'+key,
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
                if(d.sourceDt != '') {
                    d.sourceDt1 = d.sourceDt.substring(0,10);
                    d.sourceDt2 = d.sourceDt.substring(11,13);
                    d.sourceDt3 = d.sourceDt.substring(14,16);
                }
                $frm.form('clear');
                $frm.form('set.values', d);

                //checkbox
                if(d.anonymCustYn == 'Y') $frm.find('#anonymCustYn').prop( "checked", true );
                if(d.custReplyYn == 'Y') $frm.find('#custReplyYn').prop( "checked", true );
                if(d.sensSpecYn == 'Y') $frm.find('#sensSpecYn').prop( "checked", true );
                if(d.immeActYn == 'Y') $frm.find('#immeActYn').prop( "checked", true );

                // 고객 VOC 건수 표시
                $frm.find('.voc-cnt').html(d.vocCustInfo.vocCnt);
                $frm.find('.sens-cnt').html(d.vocCustInfo.sensVocCnt);

                // VOC등록 첨부파일표시
                d.fileList1.forEach(file => {
                    $('.vocFilesDiv').append(`
	                    <li id="${file.fileSeq}">
	                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
	                    </li>`);
                });

                // VOC처리 첨부파일표시
                d.fileList2.forEach(file => {
                    $('.vocActFilesDiv').append(`
	                    <li id="${file.fileSeq}">
	                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
	                    </li>`);
                });
            }
        }
    );
}
