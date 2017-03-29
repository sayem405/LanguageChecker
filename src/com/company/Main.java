package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String baseUrl = "";
        HashMap<String, Integer> baseFormatCountList = new HashMap<>();
        String[] resources = new String[]{"es", "fr", "tr", "ar"};

        File baseStringFile = new File("res/strings.xml");
        if (baseStringFile.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(baseStringFile);

                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("string");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nNode;
                        String key = element.getAttribute("name");
                        String value = element.getTextContent();
                        int count = Helper.getFormattingCount(value, false);
                        baseFormatCountList.put(key, count);
                    }
                }


                for (int i = 0; i < resources.length; i++) {
                    boolean isRtl = resources[i].contains("ar");
                    File stringFilePath = new File("res/values-" + resources[i] + "/strings.xml");
                    if (stringFilePath.exists()) {
                        FileWriter fw = new FileWriter("language_defects/" +resources[i] + ".txt");

                        dbFactory = DocumentBuilderFactory.newInstance();
                        dBuilder = dbFactory.newDocumentBuilder();
                        doc = dBuilder.parse(stringFilePath);
                        doc.getDocumentElement().normalize();

                        for (Map.Entry<String, Integer> entry : baseFormatCountList.entrySet()) {
                            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                            nList = doc.getElementsByTagName("string");
                            for (int j = 0; j < nList.getLength(); j++) {
                                Node nNode = nList.item(j);

                                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element = (Element) nNode;
                                    String key = element.getAttribute("name");
                                    if (key.equals(entry.getKey())) {

                                        String value = element.getTextContent();
                                        int count = Helper.getFormattingCount(value, isRtl);

                                        if (count != entry.getValue()) {
                                            //fw.write(entry.getKey() + ": base count@" + entry.getValue() + ", result count@" + count);
                                            fw.write(entry.getKey());
                                            fw.write("\n");
                                        }
                                    }
                                }
                            }
                        }

                        fw.close();
                        //fw.flush();
                    }
                }


            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.println("finished");
    }
}
