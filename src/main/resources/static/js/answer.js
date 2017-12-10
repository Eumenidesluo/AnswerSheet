/**
 * Created by Eumenides on 2017/10/23.
 */
var answer = new Vue({
    el: "#vue",
    data: {
        id: "",
        answers: [],
        trueAnswers:[]
    }
});
function submitAnswer() {
    var formData = new FormData();
    var name = $("#textName").val();
    formData.append("file", $("#file")[0].files[0]);
    formData.append("name", name);
    $.ajax({
        url: "/uploadAnswer",
        type: 'POST',
        async: false,
        dataType: "json",
        data: formData,
// 告诉jQuery不要去处理发送的数据
        processData: false,
// 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (r) {
            if (r.code === 0) {
                console.log("成功" + r);
                answer.id = r.id;
                // for(var i=0;i<80;i++) {
                //     answer.answers[i] = r.answers[i];
                // }
                answer.trueAnswers = r.answers.slice(0);
                answer.answers=convertAnswers2Str(r.answers.slice(0));
            } else {
                console.log("失败");
            }
        },
        error: function (r) {
            console.log("error");
        }
    });
}

function convertAnswers2Str(answers) {
    var answersStr=new Array(answers.length);
    for (var i=0;i<answers.length;i++){
        if(answers[i]>0&&answers[i]<5){
            answersStr[i]=String.fromCharCode(answers[i]+64);
        }else {
            answersStr[i]='N';
        }
    }
    return answersStr;
}
function convertStr2Answers(answers) {
    var trueAnswers=new Array(answers.length);
    for (var i=0;i<answers.length;i++){
        var code=answers[i].charCodeAt(0);
        if(code>=65&&code<=68){
            trueAnswers[i]=code-64;
        }else {
            trueAnswers[i]=0;
        }
    }
    return trueAnswers;
}
function ensure() {
    $.ajax({
        url: "/ensure",
        type: 'POST',
        async: false,
        dataType: "json",
        data: {"id": answer.id, "answers": convertStr2Answers(answer.answers)},
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (r) {
            if (r.code === 0) {
                alert("修改成功 id:" + answer.id);
            } else {
                alert("修改失败 id:" + answer.id);
            }
        },
        error: function (r) {
            alert("修改异常 id:" + answer.id);
        }
    });
}
function onValueChange(input_view) {
    var val=input_view.value;
    var code=val.charCodeAt(0);
    if (code<65||code>68){
        input_view.style.borderColor="#ff0000";
    }else {
        input_view.style.borderColor="#000000";

    }

}


