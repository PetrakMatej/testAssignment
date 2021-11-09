<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style><%@include file="/WEB-INF/css/style.css"%></style>
<!DOCTYPE html>
<html>
    <body>
        <p>Transakcia ${transactionId}: ${result}</p>
        <p>Suma: ${amount} ${currCode}</p>
        <p>Podpis: ${sign}</p>

    <body>
</html>