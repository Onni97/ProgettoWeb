<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleDetails.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/select2/select2.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/select2/select2.min.js"></script>
</head>
<body>


<div id="detailHeader">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <h5 class="title"><b>COMPILA VISITA</b></h5><br/>
    <fmt:formatDate value="${now}" pattern="yyyy-MM-d" var="nowDay"/>
    <fmt:formatDate value="${now}" pattern="H:m" var="nowTime"/>
    <h5 class="subtitle">
        Data: <fmt:formatDate value="${now}" pattern="d/MM/yyyy"/> alle ${nowTime}
    </h5><br/>

    <h5 class="subtitle">
        Paziente: ${requestScope.patient.nome} ${requestScope.patient.cognome}
    </h5>
    <h5 class="subtitle">
        Medico: ${requestScope.doctor.nome} ${requestScope.doctor.cognome}
    </h5>

</div>

<div id="DetailContent" style="text-align: center;">
    <form id="formCompileVisit" method="post" action="enterVisitDataServlet">
        <input type="hidden" name="cfPatient" value="${requestScope.patient.codiceFiscale}"/>
        <input type="hidden" name="cfDoctor" value="${requestScope.doctor.codiceFiscale}"/>
        <input type="hidden" name="dateTime" value="${nowDay} ${nowTime}"/>
        <label>
            Descrizione: <br/>
            <textarea name="description" form="formCompileVisit"></textarea>
        </label><br/>


        <div class="row">
            <div class="col-sm">
                <button type="button" id="buttonAddExam" class="btn btn-info" style="margin-bottom: 1em">
                    <i class="fas fa-plus" style="margin-right: 0.5em"></i>Prescrivi Esame
                </button>
            </div>
            <div class="col-sm">
                <button type="button" id="buttonAddRecipe" class="btn btn-info" style="margin-bottom: 1em">
                    <i class="fas fa-plus" style="margin-right: 0.5em"></i>Prescrivi Ricetta
                </button>
            </div>
        </div>


        <br/>
        <input class="btn btn-success" type="submit" value="Compila Visita"/>


    </form>
</div>


</body>


<script type="text/javascript">
    $(document).ready(function () {

        var counterExam = 0;
        var counterRecipe = 0;
        var inputExam = function (exam) {
            var toRtn = "<div class='divAddOther'>\n" +
                "            Data e ora:<br/>\n" +
                "            <label>\n" +
                "                <input class='form-control-sm' type=\"date\" name=\"esameData" + exam + "\"/> <input class='form-control-sm' type=\"time\" name=\"esameOra" + exam + "\"/>\n" +
                "            </label><br/>\n" +
                "            Medico:<br/>\n" +
                "            <label>\n" +
                "                <select class='form-control-sm' name='esameMedico'>";

            <c:forEach items="${requestScope.doctorsOfProvince}" var="doctor">
            toRtn += "<option>" +
                "${doctor.codiceMedico} - ${doctor.nome} ${doctor.cognome}" +
                "</option>";

            </c:forEach>

            toRtn += "                </select>" +
                "            </label><br/>\n" +
                "            Tipo Esame:<br/>\n" +
                "            <label>\n" +
                "                <select class='form-control-sm select2' name=\"esameTipo" + exam + "\">\n";

            <c:set var="prevCategory" scope="page" value=""/>
            <c:forEach items="${requestScope.examTypes}" var="examType">
                <c:if test="${prevCategory == ''}">
                    <c:set var="prevCategory" value="${examType.categoria}"/>
                    toRtn += " <optgroup label='${examType.categoria}'>";
                </c:if>
                <c:if test="${examType.categoria == prevCategory}">
                    toRtn += "<option>${examType.tipo}</option>";
                </c:if>

                <c:if test="${examType.categoria != prevCategory}">
                    <c:set var="prevCategory" value="${examType.categoria}"/>
                    toRtn += "</optgroup>" +
                        "<optgroup label='${examType.categoria}'>'" +
                        "<option>${examType.tipo}</option>";
                </c:if>

            </c:forEach>

            toRtn +=
                "            </optgroup></select></label><br/>\n" +
                "            <button type=\"button\" class=\"btn btn-danger my-auto dismiss\">\n" +
                "                <i class=\"fas fa-minus\" aria-hidden=\"true\"></i>\n" +
                "            </button>\n" +
                "        </div>";

            return toRtn;
        };
        var inputRecipe = function (recipe) {
            return "<div class='divAddOther'>\n" +
                "            Farmaco:<br/>\n" +
                "            <label>\n" +
                "                <input class='form-control-sm' type=\"text\" name=\"ricettaFarmaco" + recipe + "\"/>\n" +
                "            </label><br/>\n" +
                "            Quantità:<br/>\n" +
                "            <label>\n" +
                "                <input class='form-control-sm' type=\"number\" name=\"ricettaQuantita" + recipe + "\"/>\n" +
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

        //FUNZIONI PER AGGIUNGERE ESAMI/RICETTE
        $('#buttonAddExam').on("click", function (event) {
            var button = $(event.currentTarget);
            button.after(inputExam(counterExam));
            counterExam++;
        });
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
