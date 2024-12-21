<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%> --%>
   <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
    <%
    
    String errorMessage=(String)request.getAttribute("errorMessage");
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            text-align: center;
        }
        .error-container {
            margin-top: 10%;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            display: inline-block;
        }
        h1 {
            color: #dc3545;
            font-size: 3em;
            margin: 0;
        }
        p {
            font-size: 1.2em;
            color: #333;
        }
        .error-emoji {
            font-size: 5em;
            margin-bottom: 10px;
        }
        .retry-btn {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 1em;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }
        .retry-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="error-container">
        <div class="error-emoji">⚠️</div>
        <h1><b>Oh no! Something's wrong.</b></h1>
        <p>${errorMessage}</p>
        <p>If the problem persists, please contact our support team.</p>
        <a href="/views/home" class="retry-btn">Go Back to Home</a>
    </div>
</body>
</html>