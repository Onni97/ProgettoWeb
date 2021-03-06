<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sistema Sanitario</title>
</head>
<body>
<div id="bodyPatients">


    <!-- MAIN HEADER -->
    <div id="mainHeader" class="row">
        <div class="col col-12">
            <h2>PAZIENTI</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-3" id="doctorPagePatientsList"></div>
        <div class="col-9" id="doctorPagePatientInfo"></div>
    </div>


    <!--MODAL -->
    <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
         aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
            </div>
        </div>
    </div>


</div>
</body>

<script type="text/javascript">
    $(document).ready(function () {

        //FUNZIONI PER RIDIMENSIONARE LA MAINPAGE
        var correctMainPage = function () {
            var $mainHeader = $('#mainHeader');
            var $mainPage = $('#mainPage');
            var mainHeaderHeight = $mainHeader.height() + parseInt($mainHeader.css("padding-top").replace("px", "")) + parseInt($mainHeader.css("padding-bottom").replace("px", ""));
            document.getElementById("doctorPagePatientsList").style.height = String($mainPage.height() - mainHeaderHeight);
            document.getElementById("doctorPagePatientInfo").style.height = String($mainPage.height() - mainHeaderHeight);
        };
        window.addEventListener("resize", correctMainPage, false);
        correctMainPage();


        //CARICO LA LISTA PAZIENTI
        $.ajax({
            url: "${pageContext.request.contextPath}/doctorPagePatientsList",
            type: 'get',
            success: function (response) {
                // Add response in doctorPagePatientsList
                $('#doctorPagePatientsList').html(response);
            }
        });


        //RENDO L'INTERA PAGINA VISIBILE
        document.getElementById("bodyPatients").style.visibility = "visible";
    });
</script>
</html>
