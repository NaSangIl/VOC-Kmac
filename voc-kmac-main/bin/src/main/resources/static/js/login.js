$(function () {
    // event 연결 ----------------
    $('.btn-login').on('click', function(){ login(); });  //로그인
});

let login = function() {
    let $frm = $('#loginForm');

    if($frm.find('#companyCd').val() == '') {
        alert('회사코드는 필수항목입니다.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#userId').val() == '') {
        alert('아이디는 필수항목입니다.');
        $frm.find('#userId').focus();
        return;
    }
    if($frm.find('#pw').val() == '') {
        alert('비밀번호는 필수항목입니다.');
        $frm.find('#pw').focus();
        return;
    }
    let param = $('#loginForm').form('get.values');
    let url = '/login';

    AjaxUtil.post(
        url,
        JSON.stringify(param),
        function(result){
            console.log('result',result);
            if(result && result.messageCode == '0000'){
                goMain();
            } else {
                alert(result.message);
            }
        }
    );
}