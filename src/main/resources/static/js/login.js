function onClickLogin() {
    if (!checkInput($("#usernameId")[0])||!checkInput($("#passwordId")[0]))
        return;
    var username=$("#usernameId")[0].value;
    var password=$("#passwordId")[0].value;
    $.ajax({
        url : "/login",
        type : 'POST',
        async: false,
        dataType:"json",
        data : {"username":username,"password":CryptoJS.SHA256(password).toString()},
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : function(r) {
            if(r.code===0){
                window.location.href="load";
            }else{
                alert("登录失败，账号或密码错误");
            }
        },
        error : function(r) {
            alert("登录异常");
        }
    });
}
function gotoRegister() {
    window.location.href="registerPage";
}
function checkInput(obj) {
    var value=obj.value;
    if (value==""){
        obj.style.cssText="border-color: red";
        return false;
    }else {
        obj.style.borderColor="#b0bec5";
        return true;
    }

}