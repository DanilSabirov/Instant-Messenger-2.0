<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 24.08.18
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="jsp/resources/stylesheet/main.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/button.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/background.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/login.css">
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon">
</head>
<body class="background">
    <div>
        <header>
            <div id="logo">
                VRedit
            </div>
        </header>
        <div id="centerLayer">
            <p><h1>Login</h1></p>
            <div class="input_form">
                <form action="login" method="post">
                    <table>
                        <tr>
                            <td><p>Email:</p>
                                <input class="input_layer" type="text" name="email"></td>
                        </tr>
                        <tr>
                            <td><p>Password:</p>
                                <input class="input_layer" type="password" name="password"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Login"></td>
                        </tr>
                    </table>
                </form>
                <form action="registration" method="get">
                    <input type="submit" value="Registration">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
