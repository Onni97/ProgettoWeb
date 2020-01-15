<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sistema Sanitario</title>
</head>
<body>
<div id="bodyPatientsList" style="visibility: hidden">

    <div class="row">


        <!-- SUGGESTION BOX -->
        <input type="text" class="form-control" id="search" placeholder="Cerca Paziente"/><label for="search">


        <!-- PATIENTS LIST -->
        <c:forEach items="${requestScope.patients}" var="patient" varStatus="index"></label>
        <div class="col-12">
            <div class="scheda schedaPaziente">
                <div class="row">
                    <div class="col-xs-4 schedaImg">
                        <img class="mx-auto d-block profileImage"
                             src="${pageContext.request.contextPath}/profileImage/${patient.foto}" alt="FOTO">
                    </div>
                    <div class="col-xs-8 schedaPazienteBody">
                        <div>
                            <h5><span class="nomeCognome">${patient.nome} ${patient.cognome}</span></h5>
                            CF: <span class="codiceFiscale">${patient.codiceFiscale}</span><br/><br/>
                            <span style="margin-bottom: 0.5em; display: block">
                                <c:if test="${requestScope.lastVisits[index.index] == null}">
                                    Visita mai effettuata<br/>
                                </c:if>
                                <c:if test="${requestScope.lastVisits[index.index] != null}">
                                    Ultima visita: <fmt:formatDate
                                        value="${requestScope.lastVisits[index.index].dataOra}"
                                        pattern='dd/MM/yyyy'/><br/>
                                </c:if>
                            </span>

                            <c:if test="${requestScope.lastRecipes[index.index] == null}">
                                Nessuna ricetta prescritta<br/>
                            </c:if>
                            <c:if test="${requestScope.lastRecipes[index.index] != null}">
                                Ultima ricetta: <fmt:formatDate value="${requestScope.lastRecipes[index.index].data}"
                                                                pattern='dd/MM/yyyy'/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>


</div>
</body>

<script type="text/javascript">
    $(document).ready(function () {


        //FUNZIONI PER LA VISUALIZZAZIONE DEL SINGOLO PAZIENTE
        var showPatient = function (codiceFiscale) {
            $.ajax({
                url: "${pageContext.request.contextPath}/doctorPagePatientInfo?codiceFiscale=" + codiceFiscale,
                type: 'get',
                success: function (response) {
                    // Add response in doctorPagePatientsList
                    var $doctorPagePatientInfo = $('#doctorPagePatientInfo');
                    $doctorPagePatientInfo.html(response);
                    $doctorPagePatientInfo.addClass("active");
                }
            });
        };
        $('.schedaPaziente').on("click", function (event) {
            var scheda = $(event.currentTarget);
            var codiceFiscale = scheda.find('.codiceFiscale').text();
            showPatient(codiceFiscale);
        });


        //FUNZIONI PER IL QUICK SEARCH
        $('#search').keyup(function () {
            var testoCercato = $('#search').val().toLowerCase();
            $('.schedaPaziente').each(function () {
                var nomeCognome = $(this).find('.nomeCognome').text().toLowerCase();
                var codiceFiscale = $(this).find('.codiceFiscale').text().toLowerCase();
                if (nomeCognome.indexOf(testoCercato) >= 0 || codiceFiscale.indexOf(testoCercato) >= 0) {
                    $(this).removeClass("hidden");
                } else {
                    $(this).addClass("hidden");
                }
            });
        });


        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("bodyPatientsList").style.visibility = "visible";
    });
</script>
</html>
