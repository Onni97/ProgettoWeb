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
    <h5 class="title"><b>ESAME</b></h5><br/>
    <c:if test="${requestScope.exam.fatto}">
        <h5 class="subtitle">Data: <fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date'
                                               pattern='dd/MM/yyyy H:m'/></h5><br/>
    </c:if>
    <c:if test="${not requestScope.exam.fatto}">
        <h5 class="subtitle">Data fissata: <fmt:formatDate value='${requestScope.exam.dataOraFissata}' type='date'
                                               pattern='dd/MM/yyyy H:m'/></h5><br/>
    </c:if>
    <h5 class="subtitle">Prescritto dopo la visita #<c:out value="${requestScope.exam.visita.codice}"/></h5>
    <c:if test="${not requestScope.isThePatient}">
        <h5 class="subtitle">Paziente: ${requestScope.exam.visita.utente.nome} ${requestScope.exam.visita.utente.cognome}</h5>
    </c:if>
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


</body>
</html>
