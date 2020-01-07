<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Sistema Sanitario</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/styleDetails.css">
</head>

<body>


<div id="detailHeader">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <h5 class="title"><b>RICETTA</b></h5><br/>
    <c:if test="${requestScope.recipe.visita.codice != -1}">
        <h5 class="subtitle">Prescritta dopo la visita #<c:out value="${requestScope.recipe.visita.codice}"/></h5>
    </c:if>
    <c:if test="${requestScope.recipe.esame.codice != -1}">
        <h5 class="subtitle">Prescritta dopo l'esame #<c:out value="${requestScope.recipe.esame.codice}"/></h5>
    </c:if>
    <c:if test="${not empty requestScope.recipe.dataOraEvasa}">
        <h5 class="subtitle">Evasa in data: <fmt:formatDate value='${requestScope.recipe.dataOraEvasa}' type='date'
                                                            pattern='dd-MM-yyyy'/> alle <fmt:formatDate
                value='${requestScope.recipe.dataOraEvasa}' type='date' pattern='H:m'/></h5>
    </c:if>
</div>
<div id="DetailContent" style="text-align: center;">
    <p class="mainText"><c:out value="${requestScope.recipe.quantita}"/> x <c:out
            value="${requestScope.recipe.farmaco}"/></p>
    <p class="mainText">Descrizione farmaco: <c:out value="${requestScope.recipe.descrizioneFarmaco}"/></p>
    <button class="btn btn-info" style="margin-right: 2em"
            onclick="window.open('${pageContext.request.contextPath}/recipePDF?id=${requestScope.recipe.codice}','_blank')">
        Stampa
    </button>
    <button class="btn btn-info"
            onclick="window.open('${pageContext.request.contextPath}/recipeQR?id=${requestScope.recipe.codice}','_blank')">
        Vedi QR
    </button>

    <span class="index">#<c:out value="${requestScope.recipe.codice}"/></span>
</div>


</body>
</html>
