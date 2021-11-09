<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<style><%@include file="/WEB-INF/css/style.css"%></style>
<!DOCTYPE html>
<html>
    <body>
        <form action="redirectToPayment" method="POST">
            Suma: <input type="number" placeholder="0.00" required name="sum" min="0" value="0.00" step="0.01" />
            </br>
            </br>
            <input type="submit" value="Zaplat" />
        </form>
    <body>
</html>