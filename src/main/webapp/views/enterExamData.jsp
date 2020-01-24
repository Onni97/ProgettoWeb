<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleDetails.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
</head>
<body>


<!-- L'ESAME E' GIA' CHIUSO -->
<c:if test="${requestScope.exam.fatto}">
    <div id="detailHeader">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="title"><b>ESAME GIA' CHIUSO</b></h5><br/>
        <h5 class="subtitle">Data: <fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date'
                                                   pattern='dd/MM/yyyy'/> alle
            <fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date'
                            pattern='H:m'/></h5><br/>
        <h5 class="subtitle">Prescritto dopo la visita #<c:out value="${requestScope.exam.visita.codice}"/></h5>
        <h5 class="subtitle">Paziente: <c:out value="${requestScope.exam.visita.utente.nome}"/> <c:out
                value="${requestScope.exam.visita.utente.cognome}"/> -
            (<c:out value="${requestScope.exam.visita.utente.codiceFiscale}"/>)</h5>
        <h5 class="subtitle">Medico: <c:out value="${requestScope.exam.medico.nome}"/> <c:out
                value="${requestScope.exam.medico.cognome}"/></h5>
        <h5 class="subtitle">Tipo: <c:out value="${requestScope.exam.tipo}"/></h5>
    </div>
    <div id="DetailContent">
        <c:if test="${requestScope.exam.fatto}">
            <p class="mainText">Referto: <c:out value="${requestScope.exam.referto}"/></p>
            <p class="mainText">Ticket: <c:out value="${requestScope.exam.ticket}"/>€</p>
        </c:if>
        <span class="index">#<c:out value="${requestScope.exam.codice}"/></span>
    </div>
</c:if>


<!-- L'ESAME E' ANCORA DA FARE -->
<c:if test="${not requestScope.exam.fatto}">
    <form method="post" action="updateExamServlet">
            <%--suppress XmlDuplicatedId --%>
        <div id="detailHeader">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h5 class="title"><b>ESAME APERTO</b></h5><br/>
            <h5 class="subtitle">Data fissata:
                <label><input type="date" name="data"
                              value="<fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date'
                                                   pattern='yyyy-MM-dd'/>"/> alle
                    <input type="time" name="ora"
                           value="<fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date' pattern='HH:mm'/>">
                </label></h5><br/>

            <h5 class=" subtitle">Prescritto dopo la visita #<c:out value="${requestScope.exam.visita.codice}"/></h5>

            <h5 class="subtitle">Paziente: <c:out value="${requestScope.exam.visita.utente.nome}"/> <c:out
                    value="${requestScope.exam.visita.utente.cognome}"/> -
                (<c:out value="${requestScope.exam.visita.utente.codiceFiscale}"/>)</h5>
            <h5 class="subtitle">Medico: <c:out value="${requestScope.exam.medico.nome}"/> <c:out
                    value="${requestScope.exam.medico.cognome}"/></h5>
            <h5 class="subtitle">Tipo: <c:out value="${requestScope.exam.tipo}"/></h5>
        </div>
        <div id="DetailContent">
            <p class="mainText">Referto: <label>
                <input required type="text" name="referto" value="${requestScope.exam.referto}" placeholder="referto"/></label>
            </p>
            <p class="mainText">Ticket: <label>
                <input required type="number" step='0.01' name="ticket" value="${requestScope.exam.ticket}"
                       placeholder="ticket"/></label>€</p>
            <input type="hidden" name="codice" value="<c:out value="${requestScope.exam.codice}"/>"/>
            <div style="width: 100%">

                <div class="row">
                    <div class="col">
                        <button type="button" id="buttonAddRecipe" class="btn btn-info" style="margin-bottom: 1em">
                            <i class="fas fa-plus" style="margin-right: 0.5em"></i>Prescrivi Ricetta
                        </button>
                    </div>
                </div>

                <input style="margin-bottom: 0.3em;" class="btn btn-success" type="submit" name="chiudi" value="Chiudi Esame">
                <input style="margin-bottom: 0.3em;" class="btn btn-success" type="submit" name="lasciaInSospeso" value="Lascia in Sospeso">
            </div>
            <span class="index">#<c:out value="${requestScope.exam.codice}"/></span>
        </div>
    </form>
</c:if>


</body>

<script type="text/javascript">
    $(document).ready(function () {


        //FUNZIONI PER AGGIUNGERE ESAMI/RICETTE
        var counterRecipe = 0;
        var inputRecipe = function (recipe) {
            return "<div class='divAddOther'>\n" +
                "            Farmaco:<br/>\n" +
                "            <label>\n" +
                "                <input required class='form-control-sm' type=\"text\" name=\"ricettaFarmaco" + recipe + "\"/>\n" +
                "            </label><br/>\n" +
                "            Quantità:<br/>\n" +
                "            <label>\n" +
                "                <input required class='form-control-sm' type=\"number\" name=\"ricettaQuantita" + recipe + "\"/>\n" +
                "            </label><br/>\n" +
                "            Descrizione Farmaco:<br/>\n" +
                "            <label>\n" +
                "                <input class='form-control-sm' type=\"text\" name=\"ricettaDescrizione" + recipe + "\"/>\n" +
                "            </label><br/>\n" +
                "            <button type=\"button\" class=\"btn btn-danger my-auto dismiss\">\n" +
                "                <i class=\"fas fa-minus\" aria-hidden=\"true\"></i>\n" +
                "            </button>\n" +
                "        </div>";
        };
        $('#buttonAddRecipe').on("click", function (event) {
            var button = $(event.currentTarget);
            button.after(inputRecipe(counterRecipe));
            counterRecipe++;
        });

        $(document).on("click", ".dismiss", function (event) {
            $(event.currentTarget).parent().remove();
        });


        $('.select2').select2();


    });
</script>
</html>
