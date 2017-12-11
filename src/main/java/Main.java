import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Main {

    private static String httpCall(String index) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(mediaType, "{\n  \"user_id\":\"850" + index + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.telegram.org/bot" + Token.getToken() + "/getUserProfilePhotos")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();

            String jsonData = response.body().string();
            JSONObject jObject = new JSONObject(jsonData);

            if (!jObject.getBoolean("ok")) {
                System.out.println("bad request " + index);
            } else
                System.out.println("850" + index);

            response.close();

        } catch (IOException e) {
            httpCall(index);
        }
        return "";
    }

    private static CompletableFuture[] completableFutures = new CompletableFuture[100000];

    public static void main(String[] args) throws IOException {

        for (int h = 0; h < 10; h++) {
            for (int k = 0; k < 10; k++) {
                for (int z = 0; z < 10; z++) {
                    for (int j = 0; j < 10; j++) {
                        for (int i = 0; i < 10; i++) {
                            String index = "" + h + k + z + j + i;

                            completableFutures[h * 10000 + k * 1000 + z * 100 + j * 10 + i] = CompletableFuture.runAsync(() -> Main.httpCall(index));
                        }
                    }
                }
            }
        }
        CompletableFuture.allOf(completableFutures).join();
    }
}