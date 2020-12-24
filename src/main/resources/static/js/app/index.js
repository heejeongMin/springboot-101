var main = {
    init : function (){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
    },
    save : function (){
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    }
}

main.init();

/*
* 브라우저의 스코프는 공용공간으로 사용되기때문에 나중에 로딩된 js의 init, save가 먼저 로딩된 js function을 덮어쓰게 된다.
* 여러 사람이 참여하는 프로젝트에서 중복된 함수 이름은 자주 발생할 수 있으니, 이런 문제를 피하려고 유효범위를 만들어 사용한다.
* */