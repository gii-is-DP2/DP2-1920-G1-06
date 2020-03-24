<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="students">

	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#date").datepicker({
											dateFormat : 'yyyy-MM-dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
    
    <h2>
        <c:if test="${student['new']}">New </c:if> Student
    </h2>
    
   
    
    <form:form modelAttribute="student" class="form-horizontal"
			id="add-student-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName" />
            <petclinic:inputField label="Last Name" name="lastName" />
            <petclinic:inputField label="DNI" name="dni" />
            
            Birth Date:
            <form:input type="date" path="birthDate"
					pattern="yyyy-MM-dd" name="birthDate" />
            
            
            <div class="form-horizontal">
                <label class="col-sm-2 control-label">Gender:</label>
                <div class="col-sm-10">
                    <select name=propertyType class="form-control">
                        <option value=0>Male</option>
                        <option value=1>Female</option>
                    </select>
                </div>
            </div>
            <petclinic:inputField label="Email" name="email" />
            <petclinic:inputField label="Telephone" name="telephone" />
            <petclinic:inputField label="Username" name="user.username" />
            <petclinic:inputField label="Password" name="user.password" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                    <c:if test="${student['new']}">
                        <button class="btn btn-default" type="submit">Create student</button>
                    </c:if>
                   
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
