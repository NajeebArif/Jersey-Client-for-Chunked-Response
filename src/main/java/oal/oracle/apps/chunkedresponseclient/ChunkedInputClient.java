/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oal.oracle.apps.chunkedresponseclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author narif
 */
public class ChunkedInputClient {

    private static final String RESTURL = "http://localhost:8080/sampleRestApp/api/hugeDataSets/inChunks/enhancedPagination";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ClientConfig config = new ClientConfig();
        config.register(GzipReaderInterceptors.class);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(RESTURL);

        Response response = target.request().get();

        ChunkedInput<String> input = response.readEntity(new GenericType<ChunkedInput<String>>() {
        });

        String chunk = "";
        input.setParser(ChunkedInput.createParser("\n"));
        int count = 0;
        File f = new File("response.json");
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(f))) {
            while ((chunk = input.read()) != null) {
                System.out.println("************************************************************************************");
                System.out.println("Iteration Number: " + count++);
                System.out.println("THE CHUNK SIZE IS: " + chunk.length());
                System.out.println("Chunk is: " + chunk);
                writer.println(chunk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        long time = (end - startTime) / 1000;
        System.out.println("TOTAL TIME TAKEN: " + time + " secs");
    }

}
