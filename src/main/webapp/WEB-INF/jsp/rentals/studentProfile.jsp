<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="profile">

<b>Student Profile</b>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Telephone</th>
			</tr>
		</thead>
		<tr>
			<td><c:out value="${student.firstName}" /></td>
			<td><c:out value="${student.lastName}" /></td>
			<td><c:out value="${student.telephone}" /></td>
		</tr>
	</table>
	



</petclinic:layout>