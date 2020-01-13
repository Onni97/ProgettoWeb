<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sistema Sanitario</title>
</head>
<body>
<div id="bodyPatientsList" style="visibility: hidden">

    <div class="row">
        <c:forEach items="${requestScope.patients}" var="patient">
            <div class="col-12">
                <div class="scheda schedaPatient">
                    <div class="row">
                        <div class="col-xs-4 schedaImg">
                            <img class="mx-auto d-block profileImage"
                                 src="${pageContext.request.contextPath}/profileImage/${patient.foto}" alt="FOTO">
                        </div>
                        <div class="col-xs-8 schedaPatientBody">
                            <div>
                                <h5>${patient.nome} ${patient.cognome}</h5>
                                    CF: ${patient.codiceFiscale}
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


        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("bodyPatientsList").style.visibility = "visible";
    });
</script>
</html>
