<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<form action="login" method="post">

	UserName : <input type="text" name="username"/>
	Password : <input type="password" name="password"/>
	
	<input type="submit" value="Submit"/>
	<input type="reset" value="Reset"/>
</form>
</body>
</html>
