<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="rentals">

	<h2>Rental Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Start Date</th>
			<td><b><c:out value="${rental.startDate}" /></b></td>
		</tr>
		<tr>
			<th>End Date</th>
			<td><c:out value="${rental.endDate}" /></td>
		</tr>
		<tr>
			<th>Complete Address</th>
			<td><c:out value="${rental.room.roomNumber}" />, <c:out
					value="${rental.room.property.address}" /></td>
		</tr>
		<tr>
			<th>Owner</th>
			<td><c:out value="${rental.room.property.owner.lastName}" />, <c:out
					value="${rental.room.property.owner.firstName}" /></td>
		</tr>
		<tr>
			<th>Student</th>
			<td><c:out value="${rental.student.lastName}" />, <c:out
					value="${rental.student.firstName}" /></td>
		</tr>
	</table>


	<spring:url value="{roomId}/edit" var="editUrl">

	</spring:url>




	<c:if test="${rental.isARequest eq 'true'}">
		<spring:url value="/request" var="rentalUrlBack"></spring:url>
		<a href="${fn:escapeXml(rentalUrlBack)}" class="btn btn-default">Back
			to requests</a>
	</c:if>
	<c:if test="${rental.isARequest eq 'false'}">
		<spring:url value="/rentals" var="rentalUrlBack"></spring:url>
		<a href="${fn:escapeXml(rentalUrlBack)}" class="btn btn-default">Back
			to rentals</a>
	</c:if>




	<spring:url value="{studentId}/profile" var="profile">
		<spring:param name="studentId" value="${rental.student.id}" />
	</spring:url>
	<a href="${fn:escapeXml(profile)}" class="btn btn-default">Student
		Profile</a>

	<spring:url value="{rentalId}/accept" var="accept">
		<spring:param name="rentalId" value="${rental.id}" />
	</spring:url>
	<a href="${fn:escapeXml(accept)}" class="btn btn-default">Accept</a>

	<spring:url value="{rentalId}/reject" var="reject">
		<spring:param name="rentalId" value="${rental.id}" />
	</spring:url>
	<a href="${fn:escapeXml(reject)}" class="btn btn-default">Reject</a>


</petclinic:layout>
