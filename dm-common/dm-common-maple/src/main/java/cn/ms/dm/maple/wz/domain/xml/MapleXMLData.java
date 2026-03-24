package cn.ms.dm.maple.wz.domain.xml;

import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.maple.wz.base.MapleDataEntity;
import cn.ms.dm.maple.wz.domain.MapleWZCanvasData;
import cn.ms.dm.maple.wz.enums.MapleDataType;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LouMT
 * @name MapleXMLData
 * @date 2026-03-18 10:35
 * @email lmtemail163@163.com
 * @description MapleXMLData解析器
 */
@Slf4j(topic = "【MapleXMLData解析器】")
public class MapleXMLData implements MapleData, Serializable {
    @Serial
    private static final long serialVersionUID = -6840880851295740355L;

    private final Node node;
    private File imageDataDir;

    private MapleXMLData(final Node node) {
        this.node = node;
    }

    public MapleXMLData(final FileInputStream fis, final File imageDataDir) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fis);
            this.node = document.getFirstChild();

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.imageDataDir = imageDataDir;
    }

    @Override
    public MapleData getChildByPath(final String path) {
        final String segments[] = path.split("/");
        if (segments[0].equals("..")) {
            return ((MapleData) getParent()).getChildByPath(path.substring(path.indexOf("/") + 1));
        }

        Node myNode = node;
        for (int x = 0; x < segments.length; x++) {
            NodeList childNodes = myNode.getChildNodes();
            boolean foundChild = false;
            if (childNodes != null) {
                for (int i = 0; i < childNodes.getLength(); i++) {
                    try {
                        final Node childNode = childNodes.item(i);
                        if (childNode != null && childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getAttributes().getNamedItem("name").getNodeValue().equals(segments[x])) {
                            myNode = childNode;
                            foundChild = true;
                            break;
                        }
                    } catch (NullPointerException e) {
                        log.error("getChildByPath()发生NullPointerException: {}", e.getMessage());
                    }
                }
            }
            if (!foundChild) {
                return null;
            }
        }
        final MapleXMLData ret = new MapleXMLData(myNode);
        ret.imageDataDir = new File(imageDataDir, getName() + "/" + path).getParentFile();
        return ret;
    }

    @Override
    public List<MapleData> getChildren() {
        final List<MapleData> ret = new ArrayList<>();
        final NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode != null && childNode.getNodeType() == Node.ELEMENT_NODE) {
                final MapleXMLData child = new MapleXMLData(childNode);
                child.imageDataDir = new File(imageDataDir, getName());
                ret.add(child);
            }
        }
        return ret;
    }

    @Override
    public Object getData() {
        final NamedNodeMap attributes = node.getAttributes();
        final MapleDataType type = getType();
        switch (type) {
            case DOUBLE: {
                return Double.parseDouble(attributes.getNamedItem("value").getNodeValue());
            }
            case FLOAT: {
                return Float.parseFloat(attributes.getNamedItem("value").getNodeValue());
            }
            case INT: {
                return Integer.parseInt(attributes.getNamedItem("value").getNodeValue());
            }
            case SHORT: {
                return Short.parseShort(attributes.getNamedItem("value").getNodeValue());
            }
            case STRING:
            case UOL: {
                return attributes.getNamedItem("value").getNodeValue();
            }
            case VECTOR: {
                return new Point(Integer.parseInt(attributes.getNamedItem("x").getNodeValue()), Integer.parseInt(attributes.getNamedItem("y").getNodeValue()));
            }
            case CANVAS: {
                return new MapleWZCanvasData(Integer.parseInt(attributes.getNamedItem("width").getNodeValue()), Integer.parseInt(attributes.getNamedItem("height").getNodeValue()), new File(imageDataDir, getName() + ".png"));
            }
        }
        return null;
    }

    public final MapleDataType getType() {
        final String nodeName = node.getNodeName();
        if (nodeName.equals("imgdir")) {
            return MapleDataType.PROPERTY;
        } else if (nodeName.equals("canvas")) {
            return MapleDataType.CANVAS;
        } else if (nodeName.equals("convex")) {
            return MapleDataType.CONVEX;
        } else if (nodeName.equals("sound")) {
            return MapleDataType.SOUND;
        } else if (nodeName.equals("uol")) {
            return MapleDataType.UOL;
        } else if (nodeName.equals("double")) {
            return MapleDataType.DOUBLE;
        } else if (nodeName.equals("float")) {
            return MapleDataType.FLOAT;
        } else if (nodeName.equals("int")) {
            return MapleDataType.INT;
        } else if (nodeName.equals("long")) {
            return MapleDataType.LONG;
        } else if (nodeName.equals("short")) {
            return MapleDataType.SHORT;
        } else if (nodeName.equals("string")) {
            return MapleDataType.STRING;
        } else if (nodeName.equals("vector")) {
            return MapleDataType.VECTOR;
        } else if (nodeName.equals("null")) {
            return MapleDataType.IMG_0x00;
        }
        return null;
    }

    @Override
    public MapleDataEntity getParent() {
        final Node parentNode = node.getParentNode();
        if (parentNode.getNodeType() == Node.DOCUMENT_NODE) {
            return null; // can't traverse outside the img file - TODO is this a problem?
        }
        final MapleXMLData parentData = new MapleXMLData(parentNode);
        parentData.imageDataDir = imageDataDir.getParentFile();
        return parentData;
    }

    @Override
    public String getName() {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    @Override
    public Iterator<MapleData> iterator() {
        return getChildren().iterator();
    }
}
