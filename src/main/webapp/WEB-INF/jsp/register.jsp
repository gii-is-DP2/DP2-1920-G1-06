<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="register">

	<h2>What type of user do you want to be?</h2>

	<form:form modelAttribute="userType" class="form-horizontal" id="user-type">

		<div class="form-group">
           	 <div class="col-sm-offset-2 col-sm-10">
                	
                   		<a href="/owners/new" class="btn btn-default">Owner</a> 
                   		
                   		<a href="/students/new" class="btn btn-default">Student</a> 
                   		
            </div>
        </div>
   	</form:form>
   
   </petclinic:layout>