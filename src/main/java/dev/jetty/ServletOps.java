/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jetty;

import java.io.IOException ;
import java.io.PrintWriter ;

import javax.servlet.http.HttpServletResponse ;

import org.apache.jena.atlas.io.IO ;

public class ServletOps {

    public static void responseSendError(HttpServletResponse response, int statusCode, String message) {
        try {
            response.sendError(statusCode, message) ;
        } catch (IOException ex) {
            errorOccurred(ex) ;
        } catch (IllegalStateException ex) {}
    }

    public static void responseSendError(HttpServletResponse response, int statusCode) {
        try {
            response.sendError(statusCode) ;
        } catch (IOException ex) {
            errorOccurred(ex) ;
        }
    }

    private static void errorOccurred(IOException ex) { IO.exception(ex); }

    public static void successNoContent(HttpServletResponse response) {
        success(response, HttpSC.NO_CONTENT_204) ;
    }

    public static void success(HttpServletResponse response) {
        success(response, HttpSC.OK_200) ;
    }

    public static void successCreated(HttpServletResponse response) {
        success(response, HttpSC.CREATED_201) ;
    }

    // When 404 is no big deal e.g. HEAD
    public static void successNotFound(HttpServletResponse response) {
        success(response, HttpSC.NOT_FOUND_404) ;
    }

    //
    public static void success(HttpServletResponse response, int httpStatusCode) {
        response.setStatus(httpStatusCode) ;
    }

    public static void successPage(HttpServletResponse response, String message) {
        try {
            response.setContentType("text/html") ;
            response.setStatus(HttpSC.OK_200) ;
            PrintWriter out = response.getWriter() ;
            out.println("<html>") ;
            out.println("<head>") ;
            out.println("</head>") ;
            out.println("<body>") ;
            out.println("<h1>Success</h1>") ;
            if ( message != null ) {
                out.println("<p>") ;
                out.println(message) ;
                out.println("</p>") ;
            }
            out.println("</body>") ;
            out.println("</html>") ;
            out.flush() ;
        } catch (IOException ex) {
            errorOccurred(ex) ;
        }
    }

//    public static void errorBadRequest(String string) {
//        error(HttpSC.BAD_REQUEST_400, string) ;
//    }
//
//    public static void errorNotFound(String string) {
//        error(HttpSC.NOT_FOUND_404, string) ;
//    }
//
//    public static void errorNotImplemented(String msg) {
//        error(HttpSC.NOT_IMPLEMENTED_501, msg) ;
//    }
//
//    public static void errorMethodNotAllowed(String method) {
//        errorMethodNotAllowed(method, "HTTP method not allowed: " + method) ;
//    }
//
//    public static void errorMethodNotAllowed(String method, String msg) {
//        error(HttpSC.METHOD_NOT_ALLOWED_405, msg) ;
//    }
//
//    public static void errorForbidden() {
//        error(HttpSC.FORBIDDEN_403, "Forbidden") ;
//    }
//    
//    public static void errorForbidden(String msg) {
//        if ( msg != null )
//            error(HttpSC.FORBIDDEN_403, msg) ;
//        else
//            errorForbidden() ;
//    }
//
//    public static void error(int statusCode) {
//        throw new ActionErrorException(null, null, statusCode) ;
//    }
//
//    public static void error(int statusCode, String string) {
//        throw new ActionErrorException(null, string, statusCode) ;
//    }
//
//    public static void errorOccurred(String message) {
//        errorOccurred(message, null) ;
//    }
//
//    public static void errorOccurred(Throwable ex) {
//        errorOccurred(null, ex) ;
//    }
//
//    public static void errorOccurred(String message, Throwable ex) {
//        throw new ActionErrorException(ex, message, HttpSC.INTERNAL_SERVER_ERROR_500) ;
//    }
//
    public static String formatForLog(String string) {
        if ( string == null )
            return "<null>" ;
        string = string.replace('\n', ' ') ;
        string = string.replace('\r', ' ') ;
        return string ;
    }

    public static void setNoCache(HttpServletResponse response) {
        response.setHeader(HttpNames.hCacheControl, "must-revalidate,no-cache,no-store");
        response.setHeader(HttpNames.hPragma, "no-cache");
    }
    
//    /** Send a JSON value as a 200 response.  Null object means no response body and no content-type headers. */
//    public static void sendJsonReponse(HttpServletResponse response, JsonValue v) {
//        if ( v == null ) {
//            ServletOps.success(action);
//            //ServletOps.successNoContent(action);
//            return ;
//        }
//        
//        ServletOps.success(action);
//        sendJson(action, v) ;
//    }
//
//    /** Send a JSON value as a 200 response.  Null object means no response body and no content-type headers. */
//    public static void sendJson(HttpServletResponse response, JsonValue v) {
//        if ( v == null )
//            return ;
//        
//        try {
//            HttpServletResponse response = action.response ;
//            ServletOutputStream out = response.getOutputStream() ;
//            response.setContentType(WebContent.contentTypeJSON);
//            response.setCharacterEncoding(WebContent.charsetUTF8) ;
//            JSON.write(out, v) ;
//            out.println() ; 
//            out.flush() ;
//        } catch (IOException ex) { ServletOps.errorOccurred(ex) ; }
//    }
//
//    /** response to a upload operation of some kind. */ 
//    public static void uploadResponse(HttpServletResponse response, UploadDetails details) {
//        if ( details.getExistedBefore().equals(PreState.ABSENT) )
//            ServletOps.successCreated(action) ; 
//        else
//            ServletOps.success(action) ; // successNoContent if empty body.
//        JsonValue v = details.detailsJson() ;
//        ServletOps.sendJson(action, v) ;
//    }
}
