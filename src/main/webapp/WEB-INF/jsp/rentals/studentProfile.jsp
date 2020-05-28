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
				<th>Birth Date</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>DNI</th>
				<th>Username</th>
				
				
			</tr>
		</thead>
		<tr>
			<td><c:out value="${student.firstName}" /></td>
			<td><c:out value="${student.lastName}" /></td>
			<td><c:out value="${student.birthDate}" /></td>
			<td><c:out value="${student.email}" /></td>
			<td><c:out value="${student.telephone}" /></td>
			<td><c:out value="${student.dni}" /></td>
			<td><c:out value="${student.user.username}" /></td>
			
		</tr>
	</table>
	



</petclinic:layout>