package ru.chernyshovms;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class readXmlToPostgres {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ArrayList<String> allcols = new ArrayList<String>();
        allcols = getcolnames();
        boolean colbool[] = new boolean[allcols.size()];
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("D:\\Stackoverflow.com-Posts/test1.xml"));
            String columnName = "";
            String columnValue = "";
            String insertStatement_A = "";
            String insertStatement_B = "";
            String insertStatement_C = "";
            String insertStatement_full = "";
            ArrayList<String> insertColnames = new ArrayList<String>();
            ArrayList<String> insertValues = new ArrayList<String>();
            Connection ds = null;
            ds = createConnection();
            int count = 0;

            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("row")) {
                            insertColnames.clear();
                            insertValues.clear();
                            insertStatement_A="insert into user_request(";
                            insertStatement_B=") values(";
                            insertStatement_C=" )";
                        }
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        if (qName.equalsIgnoreCase("row")) {
                            for(int i=0; i<allcols.size(); i++){
                                if (attributes.hasNext()) {
                                    Attribute idAttr = startElement.getAttributeByName(new QName(String.valueOf(attributes.next().getName())));
                                    colbool[i] = true;
                                    columnName = String.valueOf(idAttr.getName());
                                    columnValue = idAttr.getValue();
                                    insertColnames.add(columnName);
                                    insertValues.add(columnValue);

                                }
                            }
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("row")){
                            count = count + 1;
                            for(int i=0; i<insertColnames.size(); i++){
                                insertStatement_A += ""+insertColnames.get(i)+", ";
                                if(insertValues.get(i)==null){
                                    //   System.out.println("value is null");
                                    insertStatement_B += ""+insertValues.get(i)+", ";
                                }
                                else{
                                    insertStatement_B += "'"+insertValues.get(i).replace("'", "")+"', ";
                                }
                            }//for end
                            insertStatement_A=insertStatement_A.replaceAll(", $", "").toLowerCase();

                            insertStatement_B=insertStatement_B.replaceAll(", $", "");

                            insertStatement_full = insertStatement_A + insertStatement_B + insertStatement_C;

                            insertData(insertStatement_full, ds);
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    public static void insertData(String insertQuery, Connection ds) throws SQLException {
        java.sql.Statement  stmt  = null;
        stmt = ds.createStatement();
        stmt.executeUpdate(insertQuery);
    }


    public static ArrayList<String> getcolnames() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ArrayList<String> allcols = new ArrayList<String>();
        allcols.clear();
        Connection ds = null;
        java.sql.Statement stmt = null;
        ds = createConnection();
        stmt = ds.createStatement();
        String colQuery = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public'  AND table_name   = 'user_request'";
        ResultSet rs = stmt.executeQuery(colQuery);
        while (rs.next()) {
            String colname = rs.getString("column_name");
            allcols.add(colname);
            System.out.println("column size : " + allcols.size());
        }
        return allcols;
    }



    public static Connection createConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection connection;
        String DbName = "XmlToPostgres";
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DbName , "postgres", "qwerty12233");
        System.out.println("connection established.");
        return connection;
    }


}