import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class KML {

    private static Document docu;
    private Element docM;

    /**
     * @pre-condition
     * Arraylist is constructed.
     * @post-condition
     * return a KML representation of the list
     * @see
     * <a href="https://stackoverflow.com/questions/19425059/how-to-make-dynamic-kml-from-java">...</a>
     * @param tsp the Arraylist that contain the nodes in the rectangle
     * @throws TransformerException
     */
    public static void toKML(ArrayList<String> tsp, ArrayList<String> optimal) throws TransformerException //create a KML object
    {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("kml");
            root.setAttribute("xmlns", "http://earth.google.com/kml/2.2");
            doc.appendChild(root);
            Element docM = doc.createElement("Document");
            root.appendChild(docM);
            Element titleName = doc.createElement("name");
            titleName.appendChild(doc.createTextNode("Pittsburgh TSP"));
            docM.appendChild(titleName);
            Element titilDescription = doc.createElement("description");
            titilDescription.appendChild(doc.createTextNode("TSP on Crime"));
            docM.appendChild(titilDescription);
            Element cstyle = doc.createElement("Style");
            cstyle.setAttribute("id", "style6");
            Element dstyle = doc.createElement("LineStyle");
            Element color = doc.createElement("color");
            color.appendChild((doc.createTextNode("73FF0000")));
            dstyle.appendChild(color);
            Element width = doc.createElement("width");
            width.appendChild(doc.createTextNode("5"));
            dstyle.appendChild(width);
            docM.appendChild(cstyle);
            cstyle.appendChild(dstyle);
            Element estyle = doc.createElement("Style");
            estyle.setAttribute("id", "style5");
            Element fstyle = doc.createElement("LineStyle");
            Element acolor = doc.createElement("color");
            acolor.appendChild((doc.createTextNode("507800F0")));
            fstyle.appendChild(acolor);
            Element awidth = doc.createElement("width");
            awidth.appendChild(doc.createTextNode("5"));
            fstyle.appendChild(awidth);
            docM.appendChild(estyle);
            estyle.appendChild(fstyle);
            //add element to placemark

            Element placemark = doc.createElement("Placemark");
            docM.appendChild(placemark);
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("TSP Path"));
            placemark.appendChild(name);
            Element decription = doc.createElement("description");
            decription.appendChild(doc.createTextNode("TSP Path"));
            placemark.appendChild(decription);
            Element styleUrl = doc.createElement("styleUrl");
            styleUrl.appendChild(doc.createTextNode("#style6"));
            placemark.appendChild(styleUrl);
            Element lineString = doc.createElement("LineString");
            placemark.appendChild(lineString);
            Element tessellate = doc.createElement("tessellate");
            tessellate.appendChild(doc.createTextNode("1"));
            lineString.appendChild(tessellate);
            Element coordinates = doc.createElement("coordinates");
            for(int i=0;i<tsp.size();i++) {
                String line = tsp.get(i);
                String[] data = line.split(",");
                coordinates.appendChild(doc.createTextNode("\n                 "+data[8] + "," + data[7]+ ",0.000000 "));
            }
            lineString.appendChild(coordinates);
            placemark.appendChild(lineString);
            //optimal path
            Element placemark_2 = doc.createElement("Placemark");
            docM.appendChild(placemark_2);
            Element name_2 = doc.createElement("name");
            name_2.appendChild(doc.createTextNode("Optimal Path"));
            placemark_2.appendChild(name_2);
            Element decription_2 = doc.createElement("description");
            decription_2.appendChild(doc.createTextNode("Optimal Path"));
            placemark_2.appendChild(decription_2);
            Element styleUrl_2 = doc.createElement("styleUrl");
            styleUrl_2.appendChild(doc.createTextNode("#style5"));
            placemark_2.appendChild(styleUrl_2);
            Element lineString_2 = doc.createElement("LineString");
            placemark_2.appendChild(lineString_2);
            Element tessellate_2 = doc.createElement("tessellate");
            tessellate_2.appendChild(doc.createTextNode("1"));
            lineString_2.appendChild(tessellate_2);
            Element coordinates_2 = doc.createElement("coordinates");
            for(int i=0;i<optimal.size();i++) {
                String line = optimal.get(i);
                String[] data = line.split(",");
                double longitude = Double.parseDouble(data[8])+0.001;
                double latitude = Double.parseDouble(data[7])+0.001;
                coordinates_2.appendChild(doc.createTextNode("\n                 "+longitude+ "," + latitude+ ",0.000000"));
            }
            lineString_2.appendChild(coordinates_2);
            placemark_2.appendChild(lineString_2);

            //write the content into KML file
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer transformer = tranFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            File file = new File("./PGHCrimes4.KML");
            Result oput = new StreamResult(new FileOutputStream(file));
            transformer.transform(new DOMSource(doc), oput);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
