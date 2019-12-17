<jsp:useBean id="user" scope="request" class="it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean"/>
<jsp:useBean id="usersDoctor" scope="request" class="it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean"/>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/styleUserPage.css">
    <link rel="script" href="">
    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
            integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
            crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
            integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
            crossorigin="anonymous"></script>
</head>


<body>

<!-- NAVBAR -->
<nav id="navBar" class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">

    <button type="button" id="openSidebar" class="btn btn-info my-auto">
        <i class="fas fa-align-left" aria-hidden="true" style="margin-right: 0.5em"></i>
        <span id="openSidebarButtonText"><jsp:getProperty name="user" property="nome"/> <jsp:getProperty name="user"
                                                                                                         property="cognome"/></span>
    </button>

    <form class="form-inline my-2 my-lg-0 float-right justify-content-right my-auto" action="logoutServlet"
          method="post">
        <button class="btn btn-danger my-2 my-sm-0 navbar-btn my-auto" type="submit" value="logout">
            <i class="fas fa-lock" aria-hidden="true" style="margin-right: 0.5em"></i>logout
        </button>
    </form>
</nav>

<!--MAIN PAGE PC -->
<div id="mainPagePC">
    <!-- <h1 class="testoIncavato">Main page</h1> -->
    <div id="mainHeader" class="row">
        <div class="col col-4">
            <h2>In programma</h2>
        </div>
        <div class="col col-4">
            <h2>Visite fatte</h2>
        </div>
        <div class="col col-4">
            <h2>Ricette evase</h2>
        </div>
    </div>
    <div id="mainContent" class="row">
        <div class="col col-4 justify-content-center">
            <c:forEach items="${requestScope.userVisits}" var="visita">
                <div class="visita">
                    <a data-toggle="modal" data-target=".bd-example-modal-lg"></a>
                    <div class="visitaInfo">
                        codice visita: <c:out value="${visita.codice}"/><br/>
                        data: <c:out value="${visita.data}"/><br/>
                        medico: <c:out value="${visita.medicoDiBase.codiceMedico}"/> - <c:out
                            value="${visita.medicoDiBase.nome}"/> <c:out value="${visita.medicoDiBase.cognome}"/>
                    </div>
                    <div class="visitaResoconto">
                        <span><c:out value="${visita.resoconto}"/></span>
                        <div class="sfumatura"></div>
                    </div>
                    <span class="altro">Mostra tutto...</span>
                </div>
            </c:forEach>
        </div>
        <div class="col col-4 justify-content-center">
        </div>
        <div class="col col-4 justify-content-center">
        </div>
    </div>
</div>

<!--MAIN PAGE MOBILE -->


<!--MODAL POPUP -->
<div class="modal fade bd-example-modal-lg my-auto" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<!-- SIDEBAR -->
<nav id="sidebar">
    <button type="button" id="dismiss" class="btn btn-info sidebarButton">
        <i class="fas fa-arrow-left" aria-hidden="true"></i>
    </button>
    <div class="row rowImg">
        <div class="col colImg">
            <img id="profileImage" class="mx-auto d-block"
                 src="${pageContext.request.contextPath}/profileImage/<jsp:getProperty name="user" property="foto"/>"
                 alt="FOTO">
        </div>
    </div>

    <div class="row" id="rowChangeProfileImg">
        <div class="col col-6 my-auto">
            <button id="changeProfileImg" class="btn btn-info mx-auto d-block sidebarButton">CAMBIA IMMAGINE</button>
        </div>

        <div class="col col-6 my-auto">
            <div id="divChangeProfileImg" class="my-auto justify-content-center">
                <form id="formChangeProfileImg" class="my-auto" method="post" action="changeProfileImgServlet"
                      enctype='multipart/form-data'>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="newImg" name="newImg"
                               accept="image/x-png,image/gif,image/jpeg">
                        <label class="custom-file-label" for="newImg">Scegli file</label>
                    </div>
                    <button id="confirmChangeProfileImg" type="submit" class="btn btn-success my-auto">
                        <i class="fas fa-check" aria-hidden="true"></i>
                    </button>
                </form>
                <button id="dismissChangeProfileImg" type="submit" class="btn btn-danger my-auto">
                    <i class="fas fa-times" aria-hidden="true"></i>
                </button>
            </div>
        </div>
    </div>


    <div class="row text-center">
        <div class="col sidebarTitle">
            <h1>IL TUO PROFILO</h1>
        </div>
    </div>

    <div id="userInfoTable">
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                CodiceFiscale
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="codiceFiscale"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Nome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="nome"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Cognome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="cognome"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Data di Nascita
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="dataNascita"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Luogo di Nascita
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="luogoNascita"/>
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
                <jsp:getProperty name="user" property="email"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Provincia
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="user" property="provincia"/>
            </div>
        </div>
    </div>


    <div class="row text-center">
        <div class="col sidebarTitle">
            <h1>IL TUO MEDICO</h1>
        </div>
    </div>

    <div id="doctorInfoTable">
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Codice Medico
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="usersDoctor" property="codiceMedico"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Nome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="usersDoctor" property="nome"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Cognome
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="usersDoctor" property="cognome"/>
            </div>
        </div>
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                e-mail
            </div>
            <div class="col-7 InfoTableCol my-auto">
                <jsp:getProperty name="usersDoctor" property="email"/>
            </div>
        </div>
    </div>

    <div class="row" id="rowChangeMedicoDiBase">
        <div class="col col-6 my-auto">
            <button id="changeMedicoDiBase" class="btn btn-info mx-auto d-block sidebarButton">CAMBIA MEDICO DI BASE
            </button>
        </div>

        <div class="col col-6 my-auto">
            <div id="divChangeMedicoDiBase" class="my-auto text-center justify-content-center">
                <form id="formChangeMedicoDiBase" class="my-auto" method="post" action="changeDoctorServlet">
                    <label class="my-auto">
                        <select name="newDoctor" class="form-control">
                            <c:forEach items="${requestScope.DoctorForProvinceList}" var="medico">
                                <c:if test="${requestScope.user.medicoDiBase != medico.codiceMedico}">
                                    <option>
                                        <c:out value="${medico.codiceMedico}"/> - <c:out value="${medico.nome}"/> <c:out
                                            value="${medico.cognome}"/>
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </label>
                    <button id="confirmChangeMedicoDiBase" type="submit" class="btn btn-success my-auto">
                        <i class="fas fa-check" aria-hidden="true"></i>
                    </button>
                </form>
                <button id="dismissChangeMedicoDiBase" type="submit" class="btn btn-danger my-auto">
                    <i class="fas fa-times" aria-hidden="true"></i>
                </button>
            </div>
        </div>
    </div>
</nav>


<!-- ombra scura per sidebar -->
<div id="overlay"></div>


<!-- Script for sidebar -->
<script type="text/javascript">
    $(document).ready(function () {
        var opened = false;

        var $navBar = $('#navBar');
        var navHeight = $navBar.height() + parseInt($navBar.css("padding-top").replace("px", "")) + parseInt($navBar.css("padding-bottom").replace("px", ""));
        document.getElementById("mainPagePC").style.marginTop = navHeight;
        document.getElementById("mainPagePC").style.height = String($(window).height() - navHeight);
        document.body.style.height = String($(window).height() - navHeight);
        var $mainHeader = $('#mainHeader');
        var mainHeaderHeight = $mainHeader.height() + parseInt($mainHeader.css("padding-top").replace("px", "")) + parseInt($mainHeader.css("padding-bottom").replace("px", ""));
        document.getElementById("mainContent").style.height = String($('#mainPagePC').height() - mainHeaderHeight);

        if ($(window).width() <= 575) {
            document.getElementById("sidebar").style.marginTop = navHeight;
            document.getElementById("sidebar").style.height = "" + ($(window).height() - navHeight);
        }

        window.addEventListener("resize", function () {
            var navHeight = $navBar.height() + parseInt($navBar.css("padding-top").replace("px", "")) + parseInt($navBar.css("padding-bottom").replace("px", ""));
            if ($(window).width() <= 575) {
                document.getElementById("sidebar").style.marginTop = navHeight;
                document.getElementById("sidebar").style.height = "" + ($(window).height() - navHeight);
            } else {
                document.getElementById("mainPagePC").style.marginTop = navHeight;
                document.getElementById("mainPagePC").style.height = String($(window).height() - navHeight);
                document.body.style.height = String($(window).height() - navHeight);
                var $mainHeader = $('#mainHeader');
                var mainHeaderHeight = $mainHeader.height() + parseInt($mainHeader.css("padding-top").replace("px", "")) + parseInt($mainHeader.css("padding-bottom").replace("px", ""));
                document.getElementById("mainContent").style.height = String($('#mainPagePC').height() - mainHeaderHeight);
                document.getElementById("sidebar").style.marginTop = "0";
                document.getElementById("sidebar").style.height = "100%";

            }
        }, false);

        $('#dismiss, #overlay').on('click', function () {
            $('#sidebar').removeClass('active');
            $('#overlay').removeClass('active');
            document.getElementById("openSidebarButtonText").innerHTML = "<c:out value="${requestScope.user.nome}"/>" + " "
                + "<c:out value="${requestScope.user.cognome}"/>";
            opened = false;
            setTimeout(function () {
                $('#rowChangeMedicoDiBase').removeClass('active');
            }, 300);
            setTimeout(function () {
                $('#rowChangeProfileImg').removeClass('active');
            }, 300);
        });
        $('#openSidebar').on('click', function () {
            if (opened) {
                $('#sidebar').removeClass('active');
                $('#overlay').removeClass('active');
                document.getElementById("openSidebarButtonText").innerHTML = "<c:out value="${requestScope.user.nome}"/>" + " "
                    + "<c:out value="${requestScope.user.cognome}"/>";
                opened = false;
                setTimeout(function () {
                    $('#rowChangeMedicoDiBase').removeClass('active');
                }, 300);
                setTimeout(function () {
                    $('#rowChangeProfileImg').removeClass('active');
                }, 300);
            } else {
                $('#sidebar').addClass('active');
                $('#overlay').addClass('active');
                document.getElementById("openSidebarButtonText").innerHTML = "Chiudi";
                opened = true;
            }
        });

        $('#changeMedicoDiBase').on('click', function () {
            $('#rowChangeMedicoDiBase').addClass('active');
            $('#rowChangeProfileImg').removeClass('active');
        });
        $('#dismissChangeMedicoDiBase').on('click', function () {
            $('#rowChangeMedicoDiBase').removeClass('active');
        });

        $('#changeProfileImg').on('click', function () {
            $('#rowChangeProfileImg').addClass('active');
            $('#rowChangeMedicoDiBase').removeClass('active');
        });
        $('#dismissChangeProfileImg').on('click', function () {
            $('#rowChangeProfileImg').removeClass('active');
        });
        $('#newImg').on('change', function () {
            //get the file name
            var filePath = $(this).val();
            filePath = filePath.split("\\");
            var fileName = filePath[filePath.length - 1];
            console.log(fileName);
            //replace the "Choose a file" label
            $(this).next('.custom-file-label').html(fileName);
        })
    });
</script>

</body>
</html>
