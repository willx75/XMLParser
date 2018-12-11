package com.will2.xmlparser;

import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLManager {

    private static String Tag = "XmlManager";

    public static List<DocumentModel> ParseXML(String path) throws Exception {

        List<DocumentModel> list = new ArrayList<>();

        /**
         * creation d'un file rss avec documentBuilderFactory
         */
        File rssFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(rssFile);

        document.getDocumentElement().normalize();

        Log.d(Tag, "ROOT ELEMENT : " + document.getDocumentElement().getNodeName());

        //on prend le 1e element channel dans mon docu rss/ il y a qu'un "channel" dans chaque document rss
        Element nodeChannel = (Element) document.getElementsByTagName("channel").item(0);


        Log.d(Tag, "---------------------");
        Log.d(Tag, "Current Mother element : " + nodeChannel.getNodeName());

        //creation d'une nodeListe d'item qui englobe chaque item de mon doc rss
        NodeList itemsNode = nodeChannel.getElementsByTagName("item");
        /**
         *on boucle pour recuperer tout les items, si tout ls element items sont present on les
         mets dans la Node puis on les ajoutent
         */

        for (int i = 0; i < itemsNode.getLength(); i++) {
            Node node = itemsNode.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;

                Log.d(Tag, "ELEMENT TITLE : " + element.getElementsByTagName("title").item(0).getTextContent());
                list.add(new DocumentModel() {
                    {
                        //Chaque doc rss commencent pas title,description,link

                        title = element.getElementsByTagName("title").item(0).getTextContent();
                        //description = element.getElementsByTagName("description").item(0).getTextContent(); //les rss n'ont plus de champs descriptions
                        link = element.getElementsByTagName("link").item(0).getTextContent();
                    }
                });
            }
        }


        return list;
    }
}