<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleDetails.css">
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
        <label>
            Data: <input type="date" name="data" value="${nowDay}" disabled/> alle
            <input type="time" name="ora" value="${nowTime}" disabled/>
        </label>
    </h5><br/>

    <h5 class="subtitle"><label>
        Paziente: <input type="text" value="${requestScope.patient.nome} ${requestScope.patient.cognome}" disabled><br/>
        Medico: <input type="text" value="${requestScope.doctor.nome} ${requestScope.doctor.cognome}" disabled>
    </label></h5>

</div>

<div id="DetailContent" style="text-align: center;">
    <label>
        Descrizione: <input type="text" name="descrizione"/>
    </label>

    <button class="btn btn-info" style="margin-right: 2em">
        <i class="fas fa-plus" style="margin-right: 0.5em"></i>Prescrivi Esame
    </button>
    <button class="btn btn-info">
        <i class="fas fa-plus" style="margin-right: 0.5em"></i>Prescrivi Ricetta
    </button>
</div>


</body>
</html>
