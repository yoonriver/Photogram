// (1) 회원정보 수정
function update(userId, event) {

    event.preventDefault(); // 폼태그 액션을 막기!!

    // profileUpdate 폼 태그 안에 있는 정보들을 data가 참조
    // .serilalize() : form 태그에 있는 key=value를 넘길 때 씀, 사진 x
    let data=$("#profileUpdate").serialize();

    console.log(data);

    $.ajax({
        type: "put",
        url : `/api/user/${userId}`, // 매개변수로 넘어온 userId
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"

        // HttpStatus 상태코드 200번이면 -> 성공
    }).done(res=>{ // res : json 데이터를 parsing해서 res에 저장
        console.log("성공", res);
        location.href = `/user/${userId}`;

        // HttpStatus 상태코드가 200번대가 아닐 때 -> 실패
    }).fail(error=>{ // error : json 데이터를 parsing해서 error에 저장
        if(error.data == null) {
            alert(error.responseJSON.message);
        }else {
            alert(JSON.stringify(error.responseJSON.data));
        }


    });
}