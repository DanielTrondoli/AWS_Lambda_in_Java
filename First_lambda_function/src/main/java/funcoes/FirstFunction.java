package funcoes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FirstFunction implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        var responseJson = new JsonObject();
        //var responseHeader = new JsonObject();
        var responseBody = new JsonObject();

        //responseHeader.addProperty("x-custom-header", "Its something that goes before the message.");
        responseBody.addProperty("message", "Its Working, its some kind of magic !!!");

        //responseJson.add("headers", responseHeader);
        //responseJson.addProperty("status_code", 200);
        responseJson.addProperty("body", responseBody.toString());

        var writer = new OutputStreamWriter(outputStream,"UTF-8");
        writer.write(responseJson.toString());
        writer.close();

    }
}
