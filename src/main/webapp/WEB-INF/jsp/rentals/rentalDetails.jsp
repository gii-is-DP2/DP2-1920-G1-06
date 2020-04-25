<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="rentals">

    <h2>Room Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Start Date</th>
            <td><b><c:out value="${rental.startDate}"/></b></td>
        </tr>
        <tr>
            <th>End Date</th>
            <td><c:out value="${rental.endDate}"/></td>
        </tr>
        <tr>
            <th>Complete Address</th>
            <td><c:out value="${rental.room.roomNumber}"/>, <c:out value="${rental.room.property.address}" /></td>
        </tr>
        <tr>
            <th>Owner</th>
            <td><c:out value="${rental.room.property.owner.lastName}" />, <c:out value="${rental.room.property.owner.firstName}" /></td>
        </tr>
         <tr>
            <th>Student</th>
            <td><c:out value="${rental.student.lastName}" />, <c:out value="${rental.student.firstName}" /></td>
        </tr>
    </table>

	<spring:url value = "/rentals" var="rentalUrlBack"></spring:url>
	<a href="${fn:escapeXml(rentalUrlBack)}" class="btn btn-default">Back to rentals</a>
</petclinic:layout>
