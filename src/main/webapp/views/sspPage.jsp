<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleUserPage.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleSsp.css">
    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
            integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
            crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
            integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
            crossorigin="anonymous"></script>
</head>
<body>
<nav id="navBar" class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">
    <div class="container-fluid" style="justify-content: right">
        <form class="form-inline my-2 my-lg-0 float-right justify-content-right my-auto" action="logoutServlet"
              method="post">
            <button class="btn btn-danger my-2 my-sm-0 navbar-btn my-auto" type="submit" value="logout">
                <i class="fas fa-lock" aria-hidden="true" style="margin-right: 0.5em"></i>logout
            </button>
        </form>
    </div>
</nav>

<div class="row" id="mainPage">
    <div class="col my-auto">
        <h1 style="margin-bottom: 1em">Servizio Sanitario Provinciale</h1>
        <button class="btn btn-success">Stampa</button>
    </div>
</div>

</body>
</html>
