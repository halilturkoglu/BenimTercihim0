package com.madlion.benimtercihim;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Created by halilturkoglu on 31.12.2016.
 */

public class XMLParse {

    String uri;
    Document doc;

    enum XMLType {
        File,
        URL,
        StringVar
    }

    public XMLParse(String uri, XMLType type) {/*Type File*/
        if(type==XMLType.File) {
            try {
                String filepath = uri;
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                this.doc = docBuilder.parse(filepath);
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SAXException sae) {
                sae.printStackTrace();
            }
        }
        else if(type==XMLType.StringVar)
        {
            try {
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(uri));
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                this.doc = docBuilder.parse(is);
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SAXException sae) {
                Log.i("veri",uri);
                sae.printStackTrace();
            }

        }
        else if(type==XMLType.URL)
        {
            try {
                URL url = new URL(uri);
                URLConnection conn = url.openConnection();
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                this.doc = docBuilder.parse(conn.getInputStream());
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SAXException sae) {
                sae.printStackTrace();
            }
        }
        else
        {
            IOException e=null;
            PrintStream s=null;
            s.append("Geçersiz veri türü");
            e.printStackTrace(s);
        }
    }

    public int getElementCount(String elementName)
    {
        NodeList n = doc.getElementsByTagName(elementName);
        return n.getLength();
    }

    public String getElementValue(String elementPath)
    {
        String[] el=elementPath.split("/");
        if(el.length==1)
        {
            return doc.getElementsByTagName(el[0]).item(0).getNodeValue();
        }
        else
        {
            NodeList n=doc.getElementsByTagName(el[0]);
            Element e= (Element) n.item(Integer.parseInt(el[1]));
            for(int i=2;i<el.length;i+=2)
            {
                n=e.getElementsByTagName(el[i]);
                if(i+1<el.length)
                    e= (Element) n.item(Integer.parseInt(el[i+1]));
                else
                    e= (Element) n.item(0);
            }
            return e.getTextContent();
        }

    }
}
