
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KeepNote</title>
</head>
<body>
	<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
		 button. Handle errors like empty fields -->
	<form action="saveNote" method="post" modelAttribute="note">
	 <table>
	 	<tr>
	 		<td>
	 			Note Id : <input type ="text" name="noteId"></input>
	 		</td>
	 	</tr>	 		 
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
	 			Note Status : <input type ="text" name="noteStatus"></input>
	 		</td>
	 	</tr>	 	
	 </table>
		<input type="submit" value="Send">
	</form>
	
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
    </tr>
    </c:forEach>
</table>
	<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
</body>
</html>