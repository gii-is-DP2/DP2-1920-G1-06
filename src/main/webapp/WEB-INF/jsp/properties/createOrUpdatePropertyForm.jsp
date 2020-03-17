<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="property">
    <%-- <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute> --%>
    <%-- <h2><c:if test="${properties['new']}">New </c:if>Visit</h2> --%>
    <%-- <jsp:body> --%>
        

        <b>Property</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Address</th>
                <th>City</th>
                <th>Description</th>
                <th>Property type</th>
                <th>Surface</th>
                <th>Total Rooms</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${property.address}"/></td>
                <td><c:out value="${property.city}"/></td>
                <td><c:out value="${property.description}"/></td>
                <td><c:out value="${property.propertyType}"/></td>
                <td><c:out value="${property.surface}"/></td>
                <td><c:out value="${property.totalRooms}"/></td>
            </tr>
        </table>

    <%-- </jsp:body> --%>

</petclinic:layout>