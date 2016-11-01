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

<form action="service/loops/generateForm" method="POST">
    <table>
    <h1>Loop Generator</h1>
    <!--xSize-->
    <tr>
        <td><label for="xSizeInput">xSize:</label></td>
        <td><input type="text" id="xSizeInput" name="xSize"/></td>
    </tr>
    <!--ySize-->
    <tr>
        <td><label for="ySizeInput">ySize:</label></td>
        <td><input type="text" id="ySizeInput"  name="ySize"/></td>
    </tr>
    <!--routeDistance-->
    <tr>
        <td><label for="routeDistanceInput">Route Distance:</label></td>
        <td><input type="text" id="routeDistanceInput"  name="routeDistance"/></td>
    </tr>
    <!--legSize-->
    <tr>
        <td><label for="legSizeInput">Leg Size:</label></td>
        <td><input type="text" id="legSizeInput"  name="legSize"/></td>
    </tr>
    <!--numLoops-->
    <tr>
        <td><label for="numLoopsInput">Number of Loops:</label></td>
        <td><input type="text" id="numLoopsInput"  name="numLoops"/></td>
    </tr>
    <!--sameFailCount-->
    <tr>
        <td><label for="sameFailCountInput">Same Fail Count:</label></td>
        <td><input type="text" id="sameFailCountInput"  name="sameFailCount"/></td>
    </tr>
    <!--failCount-->
    <tr>
        <td><label for="failCountInput">Fail Count:</label></td>
        <td><input type="text" id="failCountInput"  name="failCount"/></td>
    </tr>

    <!--allowDoubleBack-->
    <tr>
        <td><label>Allow Double Back?</label></td>
        <td>
            <label for="allowDoubleBackInputYes">Yes</label>
            <input type="radio" name="allowDoubleBack" id="allowDoubleBackInputYes" value="true"/>
            <label for="allowDoubleBackInputNo">No</label>
            <input type="radio" name="allowDoubleBack" id="allowDoubleBackInputNo" value="false"/>
        </td>
    </tr>
    <!--allowSameCoordinates-->
    <tr>
        <td><label>Allow Same Coordinates?</label></td>
        <td>
            <label for="allowSameCoordinatesYes">Yes</label>
            <input type="radio" name="allowSameCoordinates" id="allowSameCoordinatesYes" value="true"/>
            <label for="allowSameCoordinatesNo">No</label>
            <input type="radio" name="allowSameCoordinates" id="allowSameCoordinatesNo" value="false"/>
        </td>
    </tr>
    <!--allowSameCoordinates-->
    <tr>
        <td><label>Allow Through Start?</label></td>
        <td>
            <label for="allowTroughStartYes">Yes</label>
            <input type="radio" name="allowTroughStart" id="allowTroughStartYes" value="true"/>
            <label for="allowTroughStartNo">No</label>
            <input type="radio" name="allowTroughStart" id="allowTroughStartNo" value="false"/>
        </td>
    </tr>
    <!--variableLegLength-->
    <tr>
        <td><label>Variable Leg Length?</label></td>
        <td>
            <label for="variableLegLengthYes">Yes</label>
            <input type="radio" name="variableLegLength" id="variableLegLengthYes" value="true"/>
            <label for="variableLegLengthNo">No</label>
            <input type="radio" name="variableLegLength" id="variableLegLengthNo" value="false"/>
        </td>
    </tr>
    <!--returnType-->
    <tr>
        <td><label>Return Type:</label></td>
        <td>
            <label for="returnTypeHTML">HTML</label>
            <input type="radio" name="returnType" id="returnTypeHTML" value="HTML"/>
            <label for="returnTypeJSON">JSON</label>
            <input type="radio" name="returnType" id="returnTypeJSON" value="JSON"/>
        </td>
    </tr>
    </table>

    <input type="submit" value="Generate Loops" />

</form>
</body>
</html>
