
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Overview</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</head>
<body>
    <c:forEach items="${unionOfStates}" var="unionOfState">
        <p>
            <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#unionOfState${unionOfState.id}" aria-expanded="false" aria-controls="${unionOfState.id}">
                    ${unionOfState.name}
            </button>
        </p>
        <div class="collapse" id="unionOfState${unionOfState.id}">
            <div class="card card-body">
                <c:forEach items="${unionOfState.countries}" var="country">
                    <p>
                        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#country${country.id}" aria-expanded="false" aria-controls="${country.id}">
                                ${country.name}
                        </button>
                    </p>
                    <div class="collapse" id="country${country.id}">
                        <div class="card card-body">
                            <c:forEach items="${country.federalStates}" var="federalState">
                                <p>
                                    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#federalState${federalState.id}" aria-expanded="false" aria-controls="${federalState.id}">
                                            ${federalState.name}
                                    </button>
                                </p>
                                <div class="collapse" id="federalState${federalState.id}">
                                    <div class="card card-body">
                                        <c:forEach items="${federalState.districts}" var="district">
                                            <p>
                                                <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#district${district.id}" aria-expanded="false" aria-controls="${district.id}">
                                                        ${district.name}
                                                </button>
                                            </p>
                                            <div class="collapse" id="district${district.id}">
                                                <div class="card card-body">
                                                    <c:forEach items="${district.cities}" var="city">
                                                        <p>
                                                            <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#city${city.id}" aria-expanded="false" aria-controls="${city.id}">
                                                                    ${city.name}
                                                            </button>
                                                        </p>
                                                        <div class="collapse" id="city${city.id}">
                                                            <div class="card card-body">
                                                                <c:forEach items="${city.schools}" var="school">
                                                                    <p>
                                                                        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#school${school.id}" aria-expanded="false" aria-controls="${school.id}">
                                                                                ${school.name}
                                                                        </button>
                                                                    </p>
                                                                    <div class="collapse" id="school${school.id}">
                                                                        <div class="card card-body">
                                                                            <c:forEach items="${school.graduations}" var="graduation">
                                                                                <p>
                                                                                    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#graduation${graduation.id}" aria-expanded="false" aria-controls="${graduation.id}">
                                                                                            ${graduation.name}
                                                                                    </button>
                                                                                </p>
                                                                                <div class="collapse" id="graduation${graduation.id}">
                                                                                    <div class="card card-body">
                                                                                        <c:forEach items="${graduation.persons}" var="person">
                                                                                            <p>
                                                                                                    ${person.firstname} ${person.lastname}: ${person.terms.size()}
                                                                                            </p>
                                                                                            <%--<div class="collapse" id="person${person.id}">--%>
                                                                                                <%--<div class="card card-body">--%>
                                                                                                        <%----%>
                                                                                                <%--</div>--%>
                                                                                            <%--</div>--%>
                                                                                        </c:forEach>
                                                                                    </div>
                                                                                </div>
                                                                            </c:forEach>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>

</body>
</html>