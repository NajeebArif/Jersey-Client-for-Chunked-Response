/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oal.oracle.apps.chunkedresponseclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

/**
 *
 * @author narif
 */
public class GzipReaderInterceptors implements ReaderInterceptor{

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ric) throws IOException, WebApplicationException {
        InputStream is = ric.getInputStream();
        ric.setInputStream(new GZIPInputStream(is));
        return ric.proceed();
    }
    
}
