<jsp:useBean id="user" scope="request" class="it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean"/>
<jsp:useBean id="usersDoctor" scope="request" class="it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleUserPage.css">
    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
            integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
            crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
            integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
            crossorigin="anonymous"></script>
</head>


<!-- INIZIALMENTE NASCONDO L'INTERA PAGINA, LA MOSTRERO' ALLA FINE PER EVITARE PROBLEMI GRAFICI DURANTE IL CARICAMENTO -->
<body id="body" style="visibility: hidden">


<!-- NAVBAR -->
<nav id="navBar" class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">

    <button type="button" id="openSidebar" class="btn btn-info my-auto">
        <i class="fas fa-align-left" aria-hidden="true" style="margin-right: 0.5em"></i>
        <span id="openSidebarButtonText">Profilo</span>
    </button>

    <c:if test="${requestScope.user.isDoctor}">
        <button class="btn btn-success" style="margin-right: 1em"
                onclick="window.open('${pageContext.request.contextPath}/doctorPage', '_self')">
            <i class="fas fa-user-md" aria-hidden="true" style="margin-right: 0.5em"></i>Passa a Medico
        </button>
    </c:if>

    <form class="form-inline my-2 my-lg-0 float-right justify-content-right my-auto" action="logoutServlet"
          method="post">
        <button class="btn btn-danger my-2 my-sm-0 navbar-btn my-auto" type="submit" value="logout">
            <i class="fas fa-lock" aria-hidden="true" style="margin-right: 0.5em"></i>logout
        </button>
    </form>
</nav>


<!--MAIN PAGE -->
<div id="mainPage">

    <div id="mainDropdown">
        <label>
            <select class="form-control" id="mainDropdownSelect">
                <option selected>In programma</option>
                <option>Visite/Esami Fatti</option>
                <option>Ricette Evase</option>
                <option>Ticket Pagati</option>
            </select>
        </label>
    </div>
    <div id="mainHeader" class="row">
        <div class="col col-3">
            <h2>In programma</h2>
        </div>
        <div class="col col-3">
            <h2>Visite/Esami fatti</h2>
        </div>
        <div class="col col-3">
            <h2>Ricette evase</h2>
        </div>
        <div class="col col-3">
            <h2>Ticket pagati</h2>
        </div>
    </div>
    <div id="mainContent" class="row">
        <!-- COLONNA DA FARE -->
        <c:if test="${fn:length(requestScope.recipesNotTaken) + fn:length(requestScope.examListNotDone) == 0}">
            <div class="col col-3 justify-content-center" id="colonnaPromemoria"
                 style="align-items: center;">
                <h1 class="testoIncavato">Niente in programma</h1>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.recipesNotTaken) + fn:length(requestScope.examListNotDone) > 0}">
            <div class="col col-3 justify-content-center" id="colonnaPromemoria" style="background-color: #f09022">
                <c:forEach items="${requestScope.recipesNotTaken}" var="ricetta">
                    <div class="scheda schedaRicetta">
                        <div class="schedaHeader">
                            <b>RICETTA</b><br/>
                        </div>
                        <div class="schedaBody">
                            <div class="bodyRicetta">
                                <c:out value="${ricetta.quantita}"/> x <c:out value="${ricetta.farmaco}"/>
                            </div>
                        </div>
                        <span class="altro">Mostra tutto...</span>
                        <div class="schedaIndex">
                            #<span class="codiceRicetta"><c:out value="${ricetta.codice}"/></span>
                        </div>
                    </div>
                </c:forEach>
                <c:forEach items="${requestScope.examListNotDone}" var="exam">
                    <div class="scheda schedaEsame">
                        <div class="schedaHeader">
                            <b>ESAME</b><br/>
                            <fmt:formatDate value='${exam.dataOraFissata}' type='date' pattern='dd-MM-yyyy'/><br/>
                        </div>
                        <div class="schedaBody">
                            <div class="bodyEsame">
                                Tipo: ${exam.tipo}
                            </div>
                        </div>
                        <span class="altro">Mostra tutto...</span>
                        <div class="schedaIndex">
                            #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                        </div>
                    </div>
                </c:forEach>


            </div>
        </c:if>

        <!-- COLONNA ESAMI/VISITE FATTE -->
        <div class="col col-3 justify-content-center" id="colonnaFatti">
            <c:set scope="page" value="0" var="counterVisits"/>
            <c:set scope="page" value="0" var="counterExams"/>
            <c:forEach begin="0" end="${fn:length(requestScope.userVisits) + fn:length(requestScope.examListDone)}">
                <c:choose>
                    <%--suppress ELValidationInJSP --%>
                    <c:when test="${counterVisits < fn:length(requestScope.userVisits) and
                                    counterExams < fn:length(requestScope.examListDone) and
                                    requestScope.userVisits[counterVisits].dataOra < requestScope.examListDone[counterExams].dataOraFissata}">
                        <!-- STAMPO L'ESAME -->
                        <div class="scheda schedaEsame">
                            <div class="schedaHeader">
                                <b>ESAME</b><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <span class="dataEsame"><fmt:formatDate
                                        value='${requestScope.examListDone[counterExams].dataOraFissata}'
                                        type='date' pattern='dd-MM-yyyy'/><br/></span>
                            </div>
                            <div class="schedaBody">
                                    <%--suppress ELValidationInJSP --%>
                                Tipo: <c:out value="${requestScope.examListDone[counterExams].tipo}"/><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.examListDone[counterExams].referto}"/>
                                <div class="sfumatura"></div>
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                    <%--suppress ELValidationInJSP --%>
                                #<span class="codiceEsame"><c:out
                                    value="${requestScope.examListDone[counterExams].codice}"/></span>
                            </div>
                        </div>
                        <c:set var="counterExams" value="${counterExams + 1}" scope="page"/>
                    </c:when>
                    <%--suppress ELValidationInJSP --%>
                    <c:when test="${counterVisits < fn:length(requestScope.userVisits) and
                                    counterExams < fn:length(requestScope.examListDone) and
                                    requestScope.userVisits[counterVisits].dataOra > requestScope.examListDone[counterExams].dataOraFissata}">
                        <!-- STAMPO LA VISITA -->
                        <div class="scheda schedaVisita">
                            <div class="schedaHeader">
                                <b>VISITA</b><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <span class="dataVisita"><fmt:formatDate
                                        value='${requestScope.userVisits[counterVisits].dataOra}' type='date'
                                        pattern='dd-MM-yyyy'/></span><br/>
                                <div>Medico:
                                        <%--suppress ELValidationInJSP --%>
                                    <c:out value="${requestScope.userVisits[counterVisits].medicoDiBase.nome}"/> <c:out
                                            value="${requestScope.userVisits[counterVisits].medicoDiBase.cognome}"/><br/>
                                </div>
                            </div>
                            <div class="schedaBody">
                                    <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.userVisits[counterVisits].resoconto}"/>
                                <div class="sfumatura"></div>
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                    <%--suppress ELValidationInJSP --%>
                                #<span class="codiceVisita"><c:out
                                    value="${requestScope.userVisits[counterVisits].codice}"/></span>
                            </div>
                        </div>
                        <c:set var="counterVisits" value="${counterVisits + 1}" scope="page"/>
                    </c:when>
                    <c:when test="${counterVisits == fn:length(requestScope.userVisits) and
                                    counterExams < fn:length(requestScope.examListDone)}">
                        <!-- STAMPO L'ESAME -->
                        <div class="scheda schedaEsame">
                            <div class="schedaHeader">
                                <b>ESAME</b><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <span class="dataEsame"><fmt:formatDate
                                        value='${requestScope.examListDone[counterExams].dataOraFissata}'
                                        type='date' pattern='dd-MM-yyyy'/><br/></span>
                            </div>
                            <div class="schedaBody">
                                    <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.examListDone[counterExams].tipo}"/><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.examListDone[counterExams].referto}"/>
                                <div class="sfumatura"></div>
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                #<span class="codiceEsame">
                                <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.examListDone[counterExams].codice}"/></span>
                            </div>
                        </div>
                        <c:set var="counterExams" value="${counterExams + 1}" scope="page"/>
                    </c:when>
                    <c:when test="${counterVisits < fn:length(requestScope.userVisits) and
                                    counterExams == fn:length(requestScope.examListDone)}">
                        <!-- STAMPO LA VISITA -->
                        <div class="scheda schedaVisita">
                            <div class="schedaHeader">
                                <b>VISITA</b><br/>
                                    <%--suppress ELValidationInJSP --%>
                                <span class="dataVisita"><fmt:formatDate
                                        value='${requestScope.userVisits[counterVisits].dataOra}' type='date'
                                        pattern='dd-MM-yyyy'/></span><br/>
                                <div>Medico:
                                        <%--suppress ELValidationInJSP --%>
                                    <c:out value="${requestScope.userVisits[counterVisits].medicoDiBase.nome}"/> <c:out
                                            value="${requestScope.userVisits[counterVisits].medicoDiBase.cognome}"/><br/>
                                </div>
                            </div>
                            <div class="schedaBody">
                                    <%--suppress ELValidationInJSP --%>
                                <c:out value="${requestScope.userVisits[counterVisits].resoconto}"/>
                                <div class="sfumatura"></div>
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                    <%--suppress ELValidationInJSP --%>
                                #<span class="codiceVisita"><c:out
                                    value="${requestScope.userVisits[counterVisits].codice}"/></span>
                            </div>
                        </div>
                        <c:set var="counterVisits" value="${counterVisits + 1}" scope="page"/>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>


        <!-- COLONNA RICETTE -->
        <div class="col col-3 justify-content-center" id="colonnaRicette">
            <c:forEach items="${requestScope.recipesTaken}" var="ricetta">
                <div class="scheda schedaRicetta">
                    <div class="schedaHeader">
                        <b>RICETTA</b><br/>
                        <fmt:formatDate value='${ricetta.data}' type='date' pattern='dd-MM-yyyy'/><br/>
                    </div>
                    <div class="schedaBody">
                        <div class="bodyRicetta">
                            <span class="quantitaRicetta"><c:out value="${ricetta.quantita}"/></span> x <span
                                class="farmacoRicetta"><c:out value="${ricetta.farmaco}"/></span><br/>
                            <span class="descrizioneFarmacoRicetta"><c:out
                                    value="${ricetta.descrizioneFarmaco}"/></span>
                        </div>
                    </div>
                    <span class="altro">Mostra tutto...</span>
                    <div class="schedaIndex">#<span class="codiceRicetta"><c:out
                            value="${ricetta.codice}"/></span></div>
                </div>
            </c:forEach>
        </div>

        <!-- COLONNA TICKET PAGATI -->
        <div class="col col-3 justify-content-center" id="colonnaTicket">
            <button style="margin-bottom: 2em;" class="btn btn-info"
                    onclick="window.open('${pageContext.request.contextPath}/ticketsPDF','_blank')">
                Stampa Ricevute
            </button>
            <c:forEach items="${requestScope.examListDone}" var="exam">
                <div class="scheda schedaEsame">
                    <div class="schedaHeader">
                        <b>TICKET</b><br/>
                        <fmt:formatDate value='${exam.dataOraFissata}' type='date' pattern='dd-MM-yyyy'/><br/>
                    </div>
                    <div class="schedaBody">
                        <div class="bodyEsame" style="text-align: center">
                            Prezzo: ${exam.ticket}€
                        </div>
                    </div>
                    <span class="altro">Mostra tutto...</span>
                    <div class="schedaIndex" style="visibility: hidden">
                        #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<!--MODAL -->
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
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
            <h1>${requestScope.user.nome} ${requestScope.user.cognome}</h1>
        </div>
    </div>

    <div id="userInfoTable">
        <div class="row InfoTableRow">
            <div class="col-5 InfoTableCol my-auto">
                Codice Fiscale
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
                                <c:if test="${requestScope.user.medicoDiBase != medico.codiceMedico
                                                and requestScope.user.codiceMedico != medico.codiceMedico}">
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

    <!-- TODO: tasto per cambiare la password -->
</nav>
<!-- ombra scura per sidebar -->
<div id="overlay"></div>


<!-- Script -->
<script type="text/javascript">
    $(document).ready(function () {

        //IMPOSTO LE ALTEZZE DELLA PAGINA, CHE RISULTANO SBALLATE PER COLPA DELLA NAVBAR
        var $navBar = $('#navBar');


        //REGOLAZIONE SIDEBAR
        var correctSidebar = function () {
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
        var opened = false;
        $('#dismiss, #overlay').on('click', function () {
            $('#sidebar').removeClass('active');
            $('#overlay').removeClass('active');
            document.getElementById("openSidebarButtonText").innerHTML = "Profilo";
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
                document.getElementById("openSidebarButtonText").innerHTML = "Profilo";
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
            correctSidebar();
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
            //replace the "Choose a file" label
            $(this).next('.custom-file-label').html(fileName);
        });

        <%--suppress JSUnresolvedFunction, JSJQueryEfficiency --%>
        //FUNZIONI PER IL CORRETTO FUNZIONAMENTO DEI MODAL
        $('.schedaVisita').on('click', function (event) {
            var schedaVisita = $(event.currentTarget);
            var codiceVisita = schedaVisita.find('.codiceVisita').text();
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/visitData?id=" + codiceVisita,
                type: 'get',
                success: function (response) {
                    // Add response in Modal body
                    $('.modal-content').html(response);
                    // Display Modal
                    $('.modal').modal('show');
                }
            });
        });

        $('.schedaRicetta').on('click', function (event) {
            var schedaRicetta = $(event.currentTarget);
            var codiceRicetta = schedaRicetta.find('.codiceRicetta').text();
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/recipeData?id=" + codiceRicetta,
                type: 'get',
                success: function (response) {
                    // Add response in Modal body
                    $('.modal-content').html(response);
                    // Display Modal
                    $('.modal').modal('show');
                }
            });
        });

        $('.schedaEsame').on('click', function (event) {
            var schedaEsame = $(event.currentTarget);
            var codiceEsame = schedaEsame.find('.codiceEsame').text();
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/examData?id=" + codiceEsame,
                type: 'get',
                success: function (response) {
                    // Add response in Modal body
                    $('.modal-content').html(response);
                    // Display Modal
                    $('.modal').modal('show');
                }
            });
        });


        //FUNZIONE PER LO SWITCH TRA LA VISUALIZZAZIONE MOBILE E PC E REGOLAZIONI VARIE
        var showDropdown = false;
        var correctMainPage = function () {
            document.getElementById("mainPage").style.paddingTop = $navBar.height() + parseInt($navBar.css("padding-top").replace("px", "")) + parseInt($navBar.css("padding-bottom").replace("px", ""));
            var $mainHeader = $('#mainHeader');
            var $mainDropdown = $('#mainDropdown');
            if ($(window).width() <= 1400) {
                //regolo le altezze del dropdown
                var mainDropdownHeight = $mainDropdown.height() + parseInt($mainDropdown.css("padding-top").replace("px", "")) + parseInt($mainDropdown.css("padding-bottom").replace("px", ""));
                document.getElementById("mainContent").style.height = String($('#mainPage').height() - mainDropdownHeight);
                //regolo la larghezza delle colonne
                document.getElementById("colonnaPromemoria").style.flex = "0 0 100%";
                document.getElementById("colonnaFatti").style.flex = "0 0 100%";
                document.getElementById("colonnaRicette").style.flex = "0 0 100%";
                document.getElementById("colonnaTicket").style.flex = "0 0 100%";
                document.getElementById("colonnaPromemoria").style.maxWidth = "100%";
                document.getElementById("colonnaFatti").style.maxWidth = "100%";
                document.getElementById("colonnaRicette").style.maxWidth = "100%";
                document.getElementById("colonnaTicket").style.maxWidth = "100%";
                document.getElementById("mainContent").style.padding = "0";
                if (!showDropdown) {
                    //passo dalla visualizzazione grande a quella piccola
                    //nascondo la titlebar
                    document.getElementById("mainHeader").style.display = "none";
                    //imposto la dropdown su in programma e nascondo tutte le colonne apparte quella in programma
                    var $mainDropdownSelect = $('#mainDropdownSelect');
                    $mainDropdownSelect.val("In programma").change();
                    //mostro la dropdown
                    document.getElementById("mainDropdown").style.display = "block";
                    showDropdown = true
                } else {
                    //sono già nella visualizzazione piccola
                    document.getElementById("mainHeader").style.display = "none";
                }
            } else {
                //sono nella visualizzazione grande
                //nascondo il dropdown
                document.getElementById("mainDropdown").style.display = "none";
                //mostro la titlebar
                document.getElementById("mainHeader").style.display = "flex";
                //regolo le altezze dell' header
                var mainHeaderHeight = $mainHeader.height() + parseInt($mainHeader.css("padding-top").replace("px", "")) + parseInt($mainHeader.css("padding-bottom").replace("px", ""));
                document.getElementById("mainContent").style.height = String($('#mainPage').height() - mainHeaderHeight);
                //regolo la larghezza delle colonne
                document.getElementById("colonnaPromemoria").style.flex = "0 0 25%";
                document.getElementById("colonnaFatti").style.flex = "0 0 25%";
                document.getElementById("colonnaRicette").style.flex = "0 0 25%";
                document.getElementById("colonnaTicket").style.flex = "0 0 25%";
                document.getElementById("colonnaPromemoria").style.maxWidth = "25%";
                document.getElementById("colonnaFatti").style.maxWidth = "25%";
                document.getElementById("colonnaRicette").style.maxWidth = "25%";
                document.getElementById("colonnaTicket").style.maxWidth = "25%";
                document.getElementById("mainContent").style.paddingRight = "2em";
                document.getElementById("mainContent").style.paddingLeft = "2em";
                //imposto tutte le colonne su visibile
                document.getElementById("colonnaPromemoria").style.display = "block";
                document.getElementById("colonnaFatti").style.display = "block";
                document.getElementById("colonnaRicette").style.display = "block";
                document.getElementById("colonnaTicket").style.display = "block";
                showDropdown = false;
            }
        };
        correctMainPage();
        window.addEventListener("resize", correctMainPage, false);
        if ($(window).width() <= 1400) {
            $('#mainDropdownSelect').val("In programma").change();
        }

        //aggiungo il listener del dropdown
        $('#mainDropdownSelect').change(function () {
            var colonnaScelta = this.value;
            switch (colonnaScelta) {
                case "In programma":
                    document.getElementById("colonnaPromemoria").style.display = "block";
                    document.getElementById("colonnaFatti").style.display = "none";
                    document.getElementById("colonnaRicette").style.display = "none";
                    document.getElementById("colonnaTicket").style.display = "none";
                    break;
                case "Visite/Esami Fatti":
                    document.getElementById("colonnaPromemoria").style.display = "none";
                    document.getElementById("colonnaFatti").style.display = "block";
                    document.getElementById("colonnaRicette").style.display = "none";
                    document.getElementById("colonnaTicket").style.display = "none";
                    break;
                case "Ricette Evase":
                    document.getElementById("colonnaPromemoria").style.display = "none";
                    document.getElementById("colonnaFatti").style.display = "none";
                    document.getElementById("colonnaRicette").style.display = "block";
                    document.getElementById("colonnaTicket").style.display = "none";
                    break;
                case "Ticket Pagati":
                    document.getElementById("colonnaPromemoria").style.display = "none";
                    document.getElementById("colonnaFatti").style.display = "none";
                    document.getElementById("colonnaRicette").style.display = "none";
                    document.getElementById("colonnaTicket").style.display = "block";
                    break;
            }
        });

        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("body").style.visibility = "visible";
        correctMainPage();
    });
</script>

</body>
</html>