<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <body>
        <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous">
        </script>
        <script type="text/javascript">
                       $(document).ready(function() {
                           document.redirectForm.submit();
                       });
        </script>
        <form name="redirectForm" action="${Url}" method="POST" enctype="application/x-www-form-urlencoded">
           <input name="Mid" type="hidden" value="${Mid}"/>
           <input name="EshopId" type="hidden" value="${EshopId}"/>
           <input name="MsTxnId" type="hidden" value="${MsTxnId}" />
           <input name="Amount" type="hidden" value="${Amount}" />
           <input name="CurrAlphaCode" type="hidden" value="${CurrAlphaCode}" />
           <input name="ClientId" type="hidden" value="${ClientId}" />
           <input name="FirstName" type="hidden" value="${FirstName}" />
           <input name="FamilyName" type="hidden" value="${FamilyName}" />
           <input name="Email" type="hidden" value="${Email}" />
           <input name="Country" type="hidden" value="${Country}" />
           <input name="Timestamp" type="hidden" value="${Timestamp}" />
           <input name="Sign" type="hidden" value="${Sign}" />
           <input name="RURL" type="hidden" value="${RURL}" />
           <noscript>
               <input type="submit" value="Click here to continue" />
           </noscript>
        </form>
    </body>
</html>