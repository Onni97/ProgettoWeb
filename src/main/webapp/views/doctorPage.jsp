<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleUserPage.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/styles/styleDoctorPage.css">
    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
            integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
            crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
            integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
            crossorigin="anonymous"></script>
</head>


<body id="body" style="visibility: hidden; overflow-y: auto">


<!-- NAVBAR -->
<nav id="navBar" class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">

    <button type="button" id="openSidebar" class="btn btn-success my-auto">
        <i class="fas fa-align-left" aria-hidden="true" style="margin-right: 0.5em"></i>
        <span id="openSidebarButtonText">Menù</span>
    </button>

    <button class="btn btn-info" style="margin-right: 1em"
            onclick="window.open('${pageContext.request.contextPath}/userPage', '_self')">
        <i class="fas fa-user" aria-hidden="true" style="margin-right: 0.5em"></i>Passa a Utente
    </button>

    <form class="form-inline my-2 my-lg-0 float-right justify-content-right my-auto" action="logoutServlet"
          method="post">
        <button class="btn btn-danger my-2 my-sm-0 navbar-btn my-auto" type="submit" value="logout">
            <i class="fas fa-lock" aria-hidden="true" style="margin-right: 0.5em"></i>logout
        </button>
    </form>
</nav>


<!-- MAIN PAGE -->
<div id="mainPage" style="overflow-y: visible">
</div>


<!-- SIDEBAR -->
<nav id="sidebar">
    <button type="button" id="dismiss" class="btn btn-success sidebarButton">
        <i class="fas fa-arrow-left" aria-hidden="true"></i>
    </button>

    <div class="row rowImg">
        <div class="col colImg">
            <img id="profileImage" class="mx-auto d-block"
                 src="${pageContext.request.contextPath}/profileImage/${requestScope.user.foto}"
                 alt="FOTO">
        </div>
    </div>

    <div class="row text-center">
        <div class="col sidebarTitle">
            <h1>${requestScope.user.nome} ${requestScope.user.cognome}</h1>
        </div>
    </div>

    <div id="userInfoTable">
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Codice Fiscale
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.codiceFiscale}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Codice Medico
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.codiceMedico}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Nome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.nome}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Cognome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.cognome}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Data di Nascita
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.dataNascita}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Luogo di Nascita
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.luogoNascita}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Sesso
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <c:if test="${requestScope.user.sesso == '1'}">
                    <c:out value="M"/>
                </c:if>
                <c:if test="${requestScope.user.sesso == 2}">
                    <c:out value="F"/>
                </c:if>
                <c:if test="${requestScope.user.sesso == 3}">
                    <c:out value="Altro"/>
                </c:if>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                e-mail
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.email}
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Provincia
            </div>
            <div class="col-7 InfoTableCol my-auto">
                ${requestScope.user.provincia}
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col col-12 my-auto">
            <button id="buttonAppointments" class="btn btn-success mx-auto d-block sidebarButton">
                APPUNTAMENTI
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col col-12 my-auto">
            <button id="buttonPatients" class="btn btn-success mx-auto d-block sidebarButton">
                PAZIENTI
            </button>
        </div>
    </div>
</nav>
<!-- ombra scura per sidebar -->
<div id="overlay"></div>


</body>

<script type="text/javascript">
    $(document).ready(function () {

        //REGOLAZIONE SIDEBAR
        var correctSidebar = function () {
            var $navBar = $('#navBar');
            if ($(window).width() <= 575) {
                var newNavHeight = $navBar.height() + parseInt($navBar.css("padding-top").replace("px", "")) + parseInt($navBar.css("padding-bottom").replace("px", ""));
                document.getElementById("sidebar").style.marginTop = newNavHeight;
                document.getElementById("sidebar").style.height = "" + ($(window).height() - newNavHeight);
            } else {
                document.getElementById("sidebar").style.marginTop = "0";
                document.getElementById("sidebar").style.height = "100%";
            }
        };
        correctSidebar();
        window.addEventListener("resize", correctSidebar, false);

        //FUNZIONI PER IL CORRETTO FUNZIONAMENTO DELLA SIDEBAR
        var closeSidebar = function() {
            $('#sidebar').removeClass('active');
            $('#overlay').removeClass('active');
            document.getElementById("openSidebarButtonText").innerHTML = "Menù";
            opened = false;
        };
        var openSidebar = function() {
            $('#sidebar').addClass('active');
            $('#overlay').addClass('active');
            document.getElementById("openSidebarButtonText").innerHTML = "Chiudi";
            opened = true;
        };
        var opened = false;
        $('#dismiss, #overlay').on('click', function () {
            closeSidebar();
        });
        $('#openSidebar').on('click', function () {
            if (opened) {
                closeSidebar();
            } else {
                openSidebar();
            }
            correctSidebar();
        });


        //FUNZIONI PER REGOLARE LA MAINPAGE
        var correctMainPage = function () {
            var $navBar = $('#navBar');
            var navHeight = $navBar.height() + parseInt($navBar.css("padding-top").replace("px", "")) + parseInt($navBar.css("padding-bottom").replace("px", ""));
            document.getElementById("mainPage").style.marginTop = navHeight;
            document.getElementById("mainPage").style.height = window.innerHeight - navHeight + "px";
            document.getElementById("mainPage").style.maxHeight = window.innerHeight - navHeight + "px";
            document.getElementById("mainPage").style.minHeight = window.innerHeight - navHeight + "px";
        };
        correctMainPage();
        window.addEventListener("resize", correctMainPage, false);


        //FUNZIONI PER RICHIAMARE LE VARIE SOTTOPAGINE
        var showAppointments = function () {
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/doctorPageAppointments",
                type: 'get',
                success: function (response) {
                    // Add response in mainPage
                    $('#mainPage').html(response);
                }
            });
            closeSidebar();
        };
        var showPatients = function () {
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/doctorPagePatients",
                type: 'get',
                success: function (response) {
                    // Add response in mainPage
                    $('#mainPage').html(response);
                }
            });
            closeSidebar();
        };
        $('#buttonAppointments').on('click', showAppointments);
        $('#buttonPatients').on('click', showPatients);
        showAppointments();


        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("body").style.visibility = "visible";
    });
</script>
</html>
