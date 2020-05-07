<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="students">

<!--<spring:url value=" aqui iria la url de la que viene" var="">
		<spring:param name="studentId" value="${student.id}" />
	</spring:url> -->

	<b>Student Profile</b>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Address</th>
				<th>City</th>
				<th>Telephone</th>
			</tr>
		</thead>
		<tr>
			<td><c:out value="${student.firstName}" /></td>
			<td><c:out value="${student.lastName}" /></td>
			<td><c:out value="${student.address}" /></td>
			<td><c:out value="${student.city}" /></td>
			<td><c:out value="${student.telephone}" /></td>
		</tr>
	</table>
		
</petclinic:layout>