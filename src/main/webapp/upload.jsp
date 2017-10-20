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
        <form action="up" method="post" enctype="multipart/form-data">

	
		Select a file : 
                <input type="file" name="file" accept="image/jpeg" /><br>
	  
    
                <!--<input type="text" name="typename"/><br>-->
          
	   <input type="submit" value="Upload It" /><br>
	</form>
    </body>
</html>
