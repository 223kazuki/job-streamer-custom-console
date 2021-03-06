package job_streamer.job_streamer_custom_console.util;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

public class HttpRequestUtil {
    final public static String UNAUTHORIZED = "UNAUTHORIZED";
    public static String executeGet(String url, String token) {
        Client client = null;
        try {
            client = ClientBuilder.newBuilder().build();
            Response response = client.target(url).request().header("Authorization", "Token " + token).get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(String.class);
            } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode() 
                    || response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                System.out.println(response.getStatus());
                System.out.println(response.getStatusInfo());
                return UNAUTHORIZED;
            } else {
                // TODO:OK以外のハンドリング
                System.out.println(response.getStatus());
                System.out.println(response.getStatusInfo());
            }

            response.close();
        } finally {
            Optional.ofNullable(client).ifPresent(Client::close);
        }
        // exampleなので200以外のステータスにはnullを返す。
        return null;
    }

    public static String executePostJSON(String url, Entity<?> entity, String token) {
        Client client = null;
        try {
            client = ClientBuilder.newBuilder().build();
            final Entity<?> json = entity == null ? Entity.text("") : Entity.json(entity);
            final Response response = client.target(url).request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Token " + token).post(json);

            if (response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return response.readEntity(String.class);
            } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode() 
                    || response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                return UNAUTHORIZED;
            } else {
                // TODO:OK,CREATED以外のハンドリング
                System.out.println(response.getStatus());
                System.out.println(response.getStatusInfo());
            }

            response.close();
        } finally {
            Optional.ofNullable(client).ifPresent(Client::close);
        }
        // exampleなので200以外のステータスにはnullを返す。
        return null;
    }

    public static String executePutJSON(String url, Entity<?> entity, String token) {
        Client client = null;
        try {
            client = ClientBuilder.newBuilder().build();
            final Entity<?> json = entity == null ? Entity.text("") : Entity.json(entity);
            final Response response = client.target(url).request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Token " + token).put(json);

            if (response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return response.readEntity(String.class);
            } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode() 
                    || response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                return UNAUTHORIZED;
            } else {
                // TODO:OK,CREATED以外のハンドリング
                System.out.println(response.getStatus());
                System.out.println(response.getStatusInfo());
            }

            response.close();
        } finally {
            Optional.ofNullable(client).ifPresent(Client::close);
        }
        // exampleなので200以外のステータスにはnullを返す。
        return null;
    }

    public static String executeLoginPost(String url) {
        Client client = null;
        try {
            client = ClientBuilder.newBuilder().build();
            final Response response = client.target(url).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE).request().post(null);
            final String location = response.getHeaderString("Location");
            if (response.getStatus() == Response.Status.FOUND.getStatusCode() && !(location == null)
                    && !(location.endsWith("?error=true"))) {
                final String token = response.readEntity(String.class).substring(9, 45);
                return token;
            } else {
                // TODO:REDIRECT以外のハンドリング
                System.out.println(response.getStatus());
                System.out.println(response.getStatusInfo());
            }

            response.close();
        } finally {
            Optional.ofNullable(client).ifPresent(Client::close);
        }
        return UNAUTHORIZED;
    }
}
