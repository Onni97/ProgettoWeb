<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleDetails.css">
</head>

<body>


<div id="detailHeader">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <h5 class="title"><b>VISITA</b></h5><br/>
    <h5 class="subtitle">
        Data: <fmt:formatDate value='${requestScope.visit.dataOra}' type='date' pattern='dd/MM/yyyy H:m'/>
    </h5><br/>

    <c:if test="${not requestScope.isThePatient}">
        <h5 class="subtitle">Paziente: ${requestScope.visit.utente.nome} ${requestScope.visit.utente.cognome}</h5>
    </c:if>
    <h5 class="subtitle">Medico: <c:out value="${requestScope.visit.medicoDiBase.nome}"/> <c:out
            value="${requestScope.visit.medicoDiBase.cognome}"/></h5>
</div>
<div id="DetailContent">
    <p class="mainText"><c:out value="${requestScope.visit.resoconto}"/></p>

    <span class="index">#<c:out value="${requestScope.visit.codice}"/></span>
</div>


</body>
</html>
