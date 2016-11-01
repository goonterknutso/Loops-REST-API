<%--
  Created by IntelliJ IDEA.
  User: gunther
  Date: 10/31/16
  Time: 6:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
    <h1>Loop Generator</h1>
    <!--xSize-->
    <tr>
        <td><label for="xSizeInput">xSize:</label></td>
        <td><input type="text" id="xSizeInput"/></td>
    </tr>
    <!--ySize-->
    <tr>
        <td><label for="ySizeInput">ySize:</label></td>
        <td><input type="text" id="ySizeInput"/></td>
    </tr>
    <!--routeDistance-->
    <tr>
        <td><label for="routeDistanceInput">Route Distance:</label></td>
        <td><input type="text" id="routeDistanceInput"/></td>
    </tr>
    <!--legSize-->
    <tr>
        <td><label for="legSizeInput">Leg Size:</label></td>
        <td><input type="text" id="legSizeInput"/></td>
    </tr>
    <!--numLoops-->
    <tr>
        <td><label for="numLoopsInput">Number of Loops:</label></td>
        <td><input type="text" id="numLoopsInput"/></td>
    </tr>
    <!--sameFailCount-->
    <tr>
        <td><label for="sameFailCountInput">Same Fail Count:</label></td>
        <td><input type="text" id="sameFailCountInput"/></td>
    </tr>
    <!--failCount-->
    <tr>
        <td><label for="failCountInput">Fail Count:</label></td>
        <td><input type="text" id="failCountInput"/></td>
    </tr>

    <!--allowDoubleBack-->
    <tr>
        <td><label>Allow Double Back?</label></td>
        <td>
            <label for="allowDoubleBackInputYes">Yes</label>
            <input type="radio" name="allowDoubleBack" id="allowDoubleBackInputYes"/>
            <label for="allowDoubleBackInputNo">No</label>
            <input type="radio" name="allowDoubleBack" id="allowDoubleBackInputNo"/>
        </td>
    </tr>
    <!--allowSameCoordinates-->
    <tr>
        <td><label>Allow Same Coordinates?</label></td>
        <td>
            <label for="allowSameCoordinatesYes">Yes</label>
            <input type="radio" name="allowSameCoordinates" id="allowSameCoordinatesYes"/>
            <label for="allowSameCoordinatesNo">No</label>
            <input type="radio" name="allowSameCoordinates" id="allowSameCoordinatesNo"/>
        </td>
    </tr>
    <!--variableLegLength-->
    <tr>
        <td><label>Variable Leg Length?</label></td>
        <td>
            <label for="variableLegLengthYes">Yes</label>
            <input type="radio" name="variableLegLength" id="variableLegLengthYes"/>
            <label for="variableLegLengthNo">No</label>
            <input type="radio" name="variableLegLength" id="variableLegLengthNo"/>
        </td>
    </tr>
    <!--returnType-->
    <tr>
        <td><label>Return Type:</label></td>
        <td>
            <label for="returnTypeHTML">HTML</label>
            <input type="radio" name="returnType" id="returnTypeHTML"/>
            <label for="returnTypeJSON">JSON</label>
            <input type="radio" name="returnType" id="returnTypeJSON"/>
        </td>
    </tr>

</table>
</body>
</html>
