<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 24.08.18
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="jsp/resources/stylesheet/main.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/button.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/background.css">
    <link rel="stylesheet" href="jsp/resources/stylesheet/register.css">
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon">
</head>
<body class="background">
    <header>
        <div id="logo">
            VRedit
        </div>
    </header>
    <div id="centerLayer">
        <p><h1>Registration</h1></p>
        <div class="input_form">
            <form action="registration" method="post">
                <table>
                    <tr>
                        <td>
                            <p>Name:</p>
                            <p><input class="input_layer" type="text" name="name"></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p>Email address:</p>
                            <p><input class="input_layer" type="text" name="email"></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p>Password:</p>
                            <p><input class="input_layer" type="password" name="password"></p>
                            <p><input type="submit" value="Register"></p>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>
