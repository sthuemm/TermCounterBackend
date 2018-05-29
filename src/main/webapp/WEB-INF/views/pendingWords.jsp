<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Pending Words</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script>
        var setWordAsTerm = function(word){
            console.log(JSON.stringify(word));
            $.ajax({
                url: "/term",
                type: "POST",
                contentType : "application/json",
                data: word,
                success: function (response) {
                    console.log(response)
                }

            })
        }

        var setWordAsNonTerm = function(word){
            $.ajax({
                url: "/nonTerm",
                type: "POST",
                contentType : "application/json",
                data: word,
                success: function (response) {
                    console.log(response)
                }

            })
        }
    </script>
</head>
<body>
<table>
    <th>Wort</th>
    <th>Begriff</th>
    <th>Kein Begriff</th>

    <c:forEach items="${pendingWords}" var="pendingWord">
    <tr>
        <td>${pendingWord.word}</td>
        <td>
            <button type="button" class="btn btn-default" aria-label="Links ausrichten" onclick="setWordAsTerm('${pendingWord.word}')">
                <span class="fas fa-check" aria-hidden="true"></span>
            </button>
        </td>
        <td>
            <button type="button" class="btn btn-default" aria-label="Links ausrichten" onclick="setWordAsNonTerm('${pendingWord.word}')">
                <span class="far fa-times-circle" aria-hidden="true"></span>
            </button>
        </td>
    </tr>
    </c:forEach>

</table>

</body>
</html>