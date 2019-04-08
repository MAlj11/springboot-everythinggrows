<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>枝丫后台系统登陆</title>
    <link href="mystyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="main">
    <form action="/admin/user/logup" method="post">
        <hr>
        请输入邮箱：<input id="email" name="email" type="email"/>
        <br/>
        <hr>
        请输入密码：<input id="password" name="password" type="password"/>
        <br/>
        <hr>
        <input type="submit" value="登陆" />
    </form>
</div>
</body>
</html>