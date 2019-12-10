<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema sanitario</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/styleLogin.css">
    <meta
            name='viewport'
            content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
    />
</head>



<body>

<div class="row justify-content-center sopra">
    <div class="colonna my-auto">
        <h1>Sistema Sanitario</h1>
    </div>
</div>

<div class="row justify-content-center sotto">
    <div class="colonna">
        <h2>Login</h2>
        <form action="loginServlet" method="post">
            <table>
                <tr>
                    <td>
                        Codice Fiscale
                    </td>
                    <td>
                        <label><input type="text" name="codiceFiscale" /></label>
                    </td>
                </tr>
                <tr>
                    <td>
                        Password
                    </td>
                    <td>
                        <label><input type="password" name="password"/></label>
                    </td>
                </tr>
            </table>
            <p>Accedi come:</p>
            <button class="btn btn-outline-info my-2 my-sm-0" type="submit" name="user" value="utente" style="margin-right: 1em">UTENTE</button>
            <button class="btn btn-outline-info my-2 my-sm-0" type="submit" name="doctor" value="medico">MEDICO</button>
        </form>
    </div>
</div>

<div class="row justify-content-center">
    <div class="colonna">
        <c:if test="${not empty param.error}" >
            <c:if test="${param.error == -2}">
                <div class="alert alert-danger alert-dismissible text-left">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Errore!</strong> Non sei un medico
                </div>
            </c:if>
            <c:if test="${param.error == -1}">
                <div class="alert alert-danger alert-dismissible text-left">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Errore!</strong> Login errato
                </div>
            </c:if>
        </c:if>
    </div>
</div>

</body>
</html>
