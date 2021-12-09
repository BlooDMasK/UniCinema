<%@ taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<title>UniCinema</title>
<meta charset="utf-8">
<!-- bootstrap ------------------------------------------------------------------>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<!------------------------------------------------------------------------------->
<meta name = "description" content="Sito vendita biglietti cinema">
<link rel="icon" type="image/png" href="../../../images/logo.png">
<link href="${context}/css/reset.css" rel="stylesheet">
<link href="${context}/css/library.css" rel="stylesheet">
<c:if test="${not empty param.styles}">
    <c:forTokens items="${param.styles}" delims="," var="style">
        <link rel="stylesheet" href="${context}/css/${style}.css">
    </c:forTokens>
</c:if>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="${context}/js/library.js" defer></script>
<c:if test="${not empty param.scripts}">
    <c:forTokens items="${param.scripts}" delims="," var="script">
        <script src="${context}/js/${script}.js" defer></script>
    </c:forTokens>
</c:if>