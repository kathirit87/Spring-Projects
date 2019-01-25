<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<title>Keep-Board</title>
<script type="text/javascript">

function validate() {
   
    var title = document.forms["noteForm"]["noteTitle"].value;
    if (title == "") {
        alert("noteTitle must be filled out");
        return false;
    }
    
    var content = document.forms["noteForm"]["noteContent"].value;
    if (content == "") {
        alert("noteContent must be filled out");
        return false;
    }
    
    var status = document.forms["noteForm"]["noteStatus"].value;
    if (status == "") {
        alert("noteStatus must be filled out");
        return false;
    }
}

</script>
</head>

<body>
	<!-- Create a form which will have text boxes for Note title, content and status along with a Add 
		 button. Handle errors like empty fields.  (Use dropdown-list for NoteStatus) -->
	<form name="noteForm" action="add" method="post" modelAttribute="note" onsubmit="validate()">
	 <table>
	 	<tr>
	 		<td>
	 			Note Title: <input type="text" name="noteTitle"></input>
	 		</td>
	 	</tr>
	 	<tr>
	 		<td>
	 			Note Text : <input type ="text" name="noteContent"></input>
	 		</td>
	 	</tr>
	 	<tr>
	 		<td>
	 			Note Status : <select name="noteStatus" >      
								<option>Active</option>      
								<option>Pending</option>      
								<option>Completed</option>  
								<option>Rejected </option>
								</select> 
	 		</td>
	 	</tr>	 	
	 </table>
		<input type="submit" value="Add">
	</form>
	<!-- display all existing notes in a tabular structure with Title,Content,Status, Created Date and Action -->
	<table>
    <!-- here should go some titles... -->
    <tr>
    	<th>NoteId</th>
        <th>NoteTitle</th>
        <th>NoteContent</th>
        <th>NoteStatus</th>
        <th>NoteCreatedAt</th>
    </tr>
    <c:forEach var="note" items="${notes}" >
    <tr>
    	<td>
            <c:out value="${note.noteId}" />
        </td>
        <td>
            <c:out value="${note.noteTitle}" />
        </td>
        <td>
            <c:out value="${note.noteContent}" />
        </td>
        <td>
            <c:out value="${note.noteStatus}" />
        </td>
        <td>
            <c:out value="${note.createdAt}" />
        </td>
         <td> <!-- Actions for the individual item -->
            <a href="delete?noteId=${note.noteId}">Delete</a>
        </td>
    </tr>
    </c:forEach>
</table>
</body>

</html>