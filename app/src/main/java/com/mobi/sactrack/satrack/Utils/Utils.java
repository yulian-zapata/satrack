package com.mobi.sactrack.satrack.Utils;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * utilerias
 */
public class Utils {

    /**
     * Método que mockea una respuesta de la Rest para los test, se usa para
     * response code que estén en el límite de [200, 300)
     *
     * @param code:    Response code
     * @param message: Mensaje de la Rest
     * @param body:    Cuerpo de la respuesta
     * @return
     */
    public static okhttp3.Response buildResponse(
            int code, String message, ResponseBody body) {

        okhttp3.Response response = new okhttp3.Response.Builder()
                .code(code)
                .message(message)
                .body(body)
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build();

        return response;
    }
}
