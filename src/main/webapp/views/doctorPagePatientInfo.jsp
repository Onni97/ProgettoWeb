<%--suppress ELValidationInJSP --%>
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
</head>
<body>

<button type="button" id="closePatient" class="btn btn-dark">
    <i class="fas fa-arrow-left" aria-hidden="true"></i>
</button>


<div class="row" style="height: 100%" id="rowPatientInfo">
    <!-- COLONNA INFO PAZIENTE -->
    <div class="col-md-5" id="patientGeneralInfo">
        <div class="row rowImg">
            <div class="col colImg">
                <img id="profileImage" class="mx-auto d-block"
                     src="${pageContext.request.contextPath}/profileImage/${requestScope.patient.foto}"
                     alt="FOTO">
            </div>
        </div>

        <div class="row text-center">
            <div class="col sidebarTitle">
                <h1>${requestScope.patient.nome} ${requestScope.patient.cognome}</h1>
            </div>
        </div>

        <div id="patientInfoTable">
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Codice Fiscale
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.codiceFiscale}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Nome
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.nome}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Cognome
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.cognome}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Data di Nascita
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.dataNascita}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Luogo di Nascita
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.luogoNascita}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Sesso
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    <c:if test="${requestScope.patient.sesso == '1'}">
                        <c:out value="M"/>
                    </c:if>
                    <c:if test="${requestScope.patient.sesso == 2}">
                        <c:out value="F"/>
                    </c:if>
                    <c:if test="${requestScope.patient.sesso == 3}">
                        <c:out value="Altro"/>
                    </c:if>
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    e-mail
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.email}
                </div>
            </div>
            <div class="row InfoTableRow">
                <div class="col-5 InfoTableCol my-auto">
                    Provincia
                </div>
                <div class="col-7 InfoTableCol my-auto">
                    ${requestScope.patient.provincia}
                </div>
            </div>
        </div>

        <button class="btn btn-success" id="buttonCompilaVisita">
            Compila Visita
            <span style="display: none" class="cfPatient">${requestScope.patient.codiceFiscale}</span>
        </button>
    </div>


    <!-- COLONNA STORICO -->
    <div class="col-md-7" id="patientHistory">
        <h2 style="margin-bottom: 2em">STORICO</h2>

        <c:set scope="page" var="counterVisits" value="0"/>
        <c:set scope="page" var="counterExams" value="0"/>
        <c:set scope="page" var="counterRecipes" value="0"/>

        <c:forEach items="${requestScope.recipesNotTakenList}" var="ricetta">
            <div class="scheda schedaRicetta" style="border: 1px solid #f09022">
                <div class="schedaHeader" style="border-bottom: 1px solid #f09022">
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
        <c:forEach items="${requestScope.examsNotDoneList}" var="exam">
            <div class="scheda schedaEsame" style="border: 1px solid #f09022">
                <div class="schedaHeader" style="border-bottom: 1px solid #f09022">
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
        <c:forEach items="${requestScope.stampList}" var="toStamp">
            <c:choose>
                <c:when test="${toStamp == 1}">
                    <!-- stampo la visita -->
                    <div class="scheda schedaVisita">
                        <div class="schedaHeader">
                            <b>VISITA</b><br/>
                            <span class="dataVisita"><fmt:formatDate
                                    value='${requestScope.visitsList[counterVisits].dataOra}' type='date'
                                    pattern='dd-MM-yyyy'/></span><br/>
                            <div>Medico:
                                <c:out value="${requestScope.visitsList[counterVisits].medicoDiBase.nome}"/> <c:out
                                        value="${requestScope.visitsList[counterVisits].medicoDiBase.cognome}"/><br/>
                            </div>
                        </div>
                        <div class="schedaBody">
                            <c:out value="${requestScope.visitsList[counterVisits].resoconto}"/>
                            <div class="sfumatura"></div>
                        </div>
                        <span class="altro">Mostra tutto...</span>
                        <div class="schedaIndex">
                            #<span class="codiceVisita"><c:out
                                value="${requestScope.visitsList[counterVisits].codice}"/></span>
                        </div>
                    </div>
                    <c:set scope="page" var="counterVisits" value="${counterVisits+1}"/>
                </c:when>
                <c:when test="${toStamp == 2}">
                    <!-- stampo l'esame -->
                    <div class="scheda schedaEsame">
                        <div class="schedaHeader">
                            <b>ESAME</b><br/>
                            <span class="dataEsame"><fmt:formatDate
                                    value='${requestScope.examsDoneList[counterExams].dataOraFissata}'
                                    type='date' pattern='dd-MM-yyyy'/><br/></span>
                        </div>
                        <div class="schedaBody">
                            <c:out value="${requestScope.examsDoneList[counterExams].tipo}"/><br/>
                            <c:out value="${requestScope.examsDoneList[counterExams].referto}"/>
                            <div class="sfumatura"></div>
                        </div>
                        <span class="altro">Mostra tutto...</span>
                        <div class="schedaIndex">
                            #<span class="codiceEsame">${requestScope.examsDoneList[counterExams].codice}</span>
                        </div>
                    </div>
                    <c:set scope="page" var="counterExam" value="${counterExams+1}"/>
                </c:when>
                <c:when test="${toStamp == 3}">
                    <!-- stampo la ricetta -->
                    <div class="scheda schedaRicetta">
                        <div class="schedaHeader">
                            <b>RICETTA</b><br/>
                            <fmt:formatDate value='${requestScope.recipesTakenList[counterRecipes].data}' type='date'
                                            pattern='dd-MM-yyyy'/><br/>
                        </div>
                        <div class="schedaBody">
                            <div class="bodyRicetta">
                                <span class="quantitaRicetta"><c:out
                                        value="${requestScope.recipesTakenList[counterRecipes].quantita}"/></span> x
                                <span
                                        class="farmacoRicetta"><c:out
                                        value="${requestScope.recipesTakenList[counterRecipes].farmaco}"/></span><br/>
                                <span class="descrizioneFarmacoRicetta"><c:out
                                        value="${requestScope.recipesTakenList[counterRecipes].descrizioneFarmaco}"/></span>
                            </div>
                        </div>
                        <span class="altro">Mostra tutto...</span>
                        <div class="schedaIndex">#<span class="codiceRicetta"><c:out
                                value="${requestScope.recipesTakenList[counterRecipes].codice}"/></span></div>
                    </div>
                    <c:set scope="page" var="counterRecipe" value="${counterRecipes+1}"/>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>


</div>


</body>


<script type="text/javascript">
    $(document).ready(function () {

        //FUNZIONI PER LA CORRETTA CHIUSURA DEL PAZIENTE
        var hidePatient = function () {
            $('#doctorPagePatientInfo').removeClass("active");
        };
        $('#closePatient').on("click", function () {
            hidePatient();
        });


        //FUNZIONI PER I MODAL DELLO STORICO
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


        $('#buttonCompilaVisita').on("click", function (event) {
            var cfPatient = $(event.currentTarget).find('.cfPatient').text();
            $.ajax({
                url: "${pageContext.request.contextPath}/enterVisitData?cfPatient=" + cfPatient,
                type: 'get',
                success: function (response) {
                    // Add response in Modal body
                    $('.modal-content').html(response);
                    // Display Modal
                    $('.modal').modal('show');
                }
            })
        })
    });
</script>
</html>
