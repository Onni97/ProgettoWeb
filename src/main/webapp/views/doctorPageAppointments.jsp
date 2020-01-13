<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
</head>
<body>
<div id="bodyAppointment" style="visibility: hidden">

    <!-- MAIN HEADER -->
    <div id="mainHeader" class="row">
        <div class="col col-12">
            <h2>APPUNTAMENTI</h2>
        </div>
    </div>


    <!-- MAIN CONTENT -->

    <div id="mainContentAppointments">

        <!-- FATTI -->
        <div class="row appointmentSection" id="doneAppointments">
            <c:forEach items="${requestScope.examList}" var="exam">
                <fmt:formatDate value="${exam.dataOraFissata}" pattern="yyyy/MM/d" var="examDate"/>
                <fmt:formatDate value="${now}" pattern="yyyy/MM/d" var="today"/>
                <c:if test="${exam.fatto}">
                    <div class="col-sm">
                        <div class="scheda">
                            <div class="schedaHeader">
                                <fmt:formatDate value='${exam.dataOraFissata}' type='date'
                                                pattern='dd-MM-yyyy H:m'/><br/>
                            </div>
                            <div class="schedaBody">
                                Paziente: ${exam.visita.utente.nome} ${exam.visita.utente.cognome}<br/>
                                Codice Fiscale Paziente: ${exam.visita.utente.codiceFiscale}<br/>
                                Tipo di Esame: ${exam.tipo}
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div class="row" id="fatti">
            <div class="col col-12">
                <h4>FATTI</h4>
            </div>
        </div>


        <!-- IN SOSPESO -->
        <div class="row appointmentSection" id="outstandingAppointments">
            <c:set value="false" var="isInSospeso"/>
            <c:forEach items="${requestScope.examList}" var="exam">
                <fmt:formatDate value="${exam.dataOraFissata}" pattern="yyyy/MM/d" var="examDate"/>
                <fmt:formatDate value="${now}" pattern="yyyy/MM/d" var="today"/>
                <c:if test="${exam.dataOraFissata < now and not (examDate eq today) and not exam.fatto}">
                    <div class="col-sm">
                        <div class="scheda">
                            <div class="schedaHeader">
                                <fmt:formatDate value='${exam.dataOraFissata}' type='date'
                                                pattern='dd-MM-yyyy H:m'/><br/>
                            </div>
                            <div class="schedaBody">
                                Paziente: ${exam.visita.utente.nome} ${exam.visita.utente.cognome}<br/>
                                Codice Fiscale Paziente: ${exam.visita.utente.codiceFiscale}<br/>
                                Tipo di Esame: ${exam.tipo}
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                            </div>
                        </div>
                    </div>
                    <c:set value="true" var="isInSospeso"/>
                </c:if>
            </c:forEach>
        </div>
        <c:if test="${isInSospeso}">
            <div class="row" id="inSospeso">
                <div class="col col-12">
                    <h4>IN SOSPESO</h4>
                </div>
            </div>
        </c:if>
        <c:if test="${not isInSospeso}">
            <div class="row" id="inSospeso">
                <div class="col col-12">
                    <h4>NIENTE IN SOSPESO</h4>
                </div>
            </div>
        </c:if>


        <!-- OGGI -->
        <div class="row" id="oggi">
            <div class="col col-12">
                <h4>OGGI</h4>
            </div>
        </div>
        <div class="row appointmentSection" id="todayAppointments">
            <c:forEach items="${requestScope.examList}" var="exam">
                <fmt:formatDate value="${exam.dataOraFissata}" pattern="yyyy/MM/d" var="examDate"/>
                <fmt:formatDate value="${now}" pattern="yyyy/MM/d" var="today"/>
                <c:if test="${examDate eq today}">
                    <div class="col-sm">
                        <div class="scheda">
                            <div class="schedaHeader">
                                <fmt:formatDate value='${exam.dataOraFissata}' type='date' pattern='H:m'/><br/>
                            </div>
                            <div class="schedaBody">
                                Paziente: ${exam.visita.utente.nome} ${exam.visita.utente.cognome}<br/>
                                Codice Fiscale Paziente: ${exam.visita.utente.codiceFiscale}<br/>
                                Tipo di Esame: ${exam.tipo}
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>


        <!-- PROSSIMAMENTE -->
        <div class="row" id="prossimamente">
            <div class="col col-12">
                <h4>PROSSIMAMENTE</h4>
            </div>
        </div>
        <div class="row appointmentSection" id="nextAppointments">
            <c:forEach items="${requestScope.examList}" var="exam">
                <fmt:formatDate value="${exam.dataOraFissata}" pattern="yyyy/MM/d" var="examDate"/>
                <fmt:formatDate value="${now}" pattern="yyyy/MM/d" var="today"/>
                <c:if test="${exam.dataOraFissata > now and not (examDate eq today)}">
                    <div class="col-sm">
                        <div class="scheda">
                            <div class="schedaHeader">
                                <fmt:formatDate value='${exam.dataOraFissata}' type='date'
                                                pattern='dd-MM-yyyy H:m'/><br/>
                            </div>
                            <div class="schedaBody">
                                Paziente: ${exam.visita.utente.nome} ${exam.visita.utente.cognome}<br/>
                                Codice Fiscale Paziente: ${exam.visita.utente.codiceFiscale}<br/>
                                Tipo di Esame: ${exam.tipo}
                            </div>
                            <span class="altro">Mostra tutto...</span>
                            <div class="schedaIndex">
                                #<span class="codiceEsame"><c:out value="${exam.codice}"/></span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
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
</div>
</body>

<script type="text/javascript">
    $(document).ready(function () {


        //FUNZIONI PER RIDIMENSIONARE LA MAINPAGE
        var correctMainPage = function () {
            var $mainHeader = $('#mainHeader');
            var mainHeaderHeight = $mainHeader.height() + parseInt($mainHeader.css("padding-top").replace("px", "")) + parseInt($mainHeader.css("padding-bottom").replace("px", ""));
            document.getElementById("mainContentAppointments").style.height = String($('#mainPage').height() - mainHeaderHeight);
        };
        window.addEventListener("resize", correctMainPage, false);
        correctMainPage();


        //FUNZIONI PER VISUALIZZARE INIZIALMENTE LA SEZIONE OGGI
        var setSpaceBottom = function () {
            var $oggi = $('#oggi');
            var oggiHeight = $oggi.height() +
                parseInt($oggi.css("padding-top").replace("px", "")) + parseInt($oggi.css("padding-bottom").replace("px", "")) +
                parseInt($oggi.css("margin-top").replace("px", "")) + parseInt($oggi.css("margin-bottom").replace("px", ""));

            var $todayAppointments = $('#todayAppointments');
            var todayAppointmentsHeight = $todayAppointments.height() +
                parseInt($todayAppointments.css("padding-top").replace("px", "")) + parseInt($todayAppointments.css("padding-bottom").replace("px", "")) +
                parseInt($todayAppointments.css("margin-top").replace("px", "")) + parseInt($todayAppointments.css("margin-bottom").replace("px", ""));

            var $prossimamente = $('#prossimamente');
            var prossimamenteHeight = $prossimamente.height() +
                parseInt($prossimamente.css("padding-top").replace("px", "")) + parseInt($prossimamente.css("padding-bottom").replace("px", "")) +
                parseInt($prossimamente.css("margin-top").replace("px", "")) + parseInt($prossimamente.css("margin-bottom").replace("px", ""));

            var $nextAppointments = $('#nextAppointments');
            var nextAppointmentsHeight = $nextAppointments.height() +
                parseInt($nextAppointments.css("padding-top").replace("px", "")) + parseInt($nextAppointments.css("padding-bottom").replace("px", "")) +
                parseInt($nextAppointments.css("margin-top").replace("px", ""));

            document.getElementById("nextAppointments").style.marginBottom = (($('#mainContentAppointments').height() - (oggiHeight + todayAppointmentsHeight +
                prossimamenteHeight + nextAppointmentsHeight)) - 2) + "px";
        };
        setSpaceBottom();
        window.addEventListener("resize", setSpaceBottom, false);
        //VADO ALLA SEZIONE OGGI
        location.href = "#oggi";


        //FUNZIONI PER IL CORRETTO FUNZIONAMENTO DEI MODAL
        $('.scheda').on("click", function (event) {
            var scheda = $(event.currentTarget);
            var codice = scheda.find('.codiceEsame').text();
            // AJAX request
            $.ajax({
                url: "${pageContext.request.contextPath}/enterExamData?id=" + codice,
                type: 'get',
                success: function (response) {
                    // Add response in Modal body
                    $('.modal-content').html(response);
                    // Display Modal

                    $('.modal').modal('show');
                }
            });
        });


        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("bodyAppointment").style.visibility = "visible";
    });
</script>

</html>
