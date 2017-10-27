<%-- 
    Document   : index
    Created on : Sep 10, 2017, 10:20:02 PM
    Author     : AnNguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="newchild" method="post" enctype="multipart/form-data">

	
		Select a file : 
                <input type="file" name="file" accept="image/jpeg" /><br>
                <input type="text" name="token"/><br>
                 <input type="text" name="action"/><br>
                <input type="text" name="fullname"/><br>
                <input type="text" name="date_of_birth"/><br>
                <input type="text" name="father"/><br>
                <input type="text" name="mother"/><br>
                <input type="text" name="father_career"/><br>
                <input type="text" name="mother_career"/><br>
                <input type="text" name="monthly_income"/><br>
                <input type="text" name="child_sex"/><br>         
	   <input type="submit" value="Upload It" /><br>
	</form>
    </body>
</html>
