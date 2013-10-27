<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Spring</h1>
 
        <p>
            This was passed in from the controller thus showing that
            the controller was accessed before the page was rendered.
            This is MVC (Model View Controller) in action.
        </p>
        <p>
            Simple values can be rendered as so  i.e. here's the value from the controller: <blockquote>${hello}</blockquote>
        </p>
 
    </body>
</html>