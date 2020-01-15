<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sistema Sanitario</title>
</head>
<body>

<button type="button" id="closePatient" class="btn btn-dark">
    <i class="fas fa-arrow-left" aria-hidden="true"></i>
</button>


<div class="row"

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


    });
</script>
</html>
