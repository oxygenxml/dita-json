package com.oxygenxml.json.dita;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.dita.dost.util.CatalogUtils;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class JSONReader implements XMLReader {

	/**
	 * Entity resolver
	 */
	private EntityResolver resolver;
	/**
	 * Content Handler
	 */
	private ContentHandler handler;
	/**
	 * Error Handler.
	 */
	private ErrorHandler errorHandler;

	@Override
	public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		return false;
	}

	@Override
	public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		return null;
	}

	@Override
	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
	}

	@Override
	public void setEntityResolver(EntityResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public EntityResolver getEntityResolver() {
		return resolver;
	}

	@Override
	public void setDTDHandler(DTDHandler handler) {
		//
	}

	@Override
	public DTDHandler getDTDHandler() {
		return null;
	}

	@Override
	public void setContentHandler(ContentHandler handler) {
		this.handler = handler;
	}

	@Override
	public ContentHandler getContentHandler() {
		return handler;
	}

	@Override
	public void setErrorHandler(ErrorHandler handler) {
		errorHandler = handler;
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}
	
	@Override
	public void parse(InputSource input) throws IOException, SAXException {
		InputStream is = input.getByteStream();
		URL url = new URL(input.getSystemId());
		if(is == null) {
			URL urlForConnect = url;
			if("file".equals(url.getProtocol())) {
				String urlStr = urlForConnect.toString();
				if(urlStr.contains("?")) {
					//Remove query part
					urlForConnect = new URL(urlStr.substring(0, urlStr.indexOf("?")));
				}
			}
			is = urlForConnect.openStream();
		}
		JSONObject obj = new JSONObject();
		//Read the content.
		StringBuilder contentBuilder = new StringBuilder();
		InputStreamReader reader = null;
		try {
			char[] chars = new char[1024];
			reader = new InputStreamReader(is, "UTF-8");
			int len = -1;
			while((len = reader.read(chars)) != -1) {
				contentBuilder.append(chars, 0, len);
			}
		} finally {
			if(reader != null) {
				//Close the input stream
				reader.close();
			}
		}
		

		String xmlString = XML.toString(obj, "JSON");
		System.err.println("XML STRING " + xmlString);
		//Delegate to content handler.
		SAXResult result = new SAXResult(handler);
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setURIResolver(CatalogUtils.getCatalogResolver());
			Source resolved = CatalogUtils.getCatalogResolver().resolve("plugin:com.oxygenxml.json.dita:" + "/resources/json-to-dita.xsl", new File(".").toURI().toURL().toString());
			
			System.err.println("RESOLVED " + (resolved != null ? resolved.getSystemId() : null));
			if(resolved != null) {
				Transformer newTransformer = factory.newTransformer(resolved);
				newTransformer.transform(new StreamSource(new StringReader(xmlString), url.toString()),
						result);
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	
	@Override
	public void parse(String systemId) throws IOException, SAXException {
		parse(new InputSource(systemId));
	}
	

}
