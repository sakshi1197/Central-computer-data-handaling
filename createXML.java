package creation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;
public class createXML {

public  void createxml()
        {
      try {
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = 
            dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         // root element
         Element rootElement = doc.createElement("txnblock");
         doc.appendChild(rootElement);

         //  supercars element
         Element transaction = doc.createElement("txn");
         rootElement.appendChild(transaction);

         // setting attribute to element
        

         // carname element
         

         // write the content into xml file
         
         
         
         
         Scanner s=new Scanner(System.in);
         while(true)
         {
             System.out.println("MENU");
             System.out.println("0: Exit");
             System.out.println("1: Date and time of local transaction");
             System.out.println("2: PAN (Primary Account Nmuber)");
             System.out.println("3: Card Acceptor Terminal ID");
             System.out.println("4: Amount of transaction");
             System.out.println("5: Card acceptor id code");
             System.out.println("6: Card acceptor name");
             System.out.println("7: Card acceptor location/address");
             System.out.println("8: Card acceptor city");
             System.out.println("9: Card acceptor state name");
             System.out.println("10:Card acceptor country code");
             System.out.println("enter your choice");
             int ch=s.nextInt();
             if(ch==0)
                 break;
             switch(ch)
             {
                 case 1:
                     System.out.println("enter the value");
                     String DtTmLcTxn=s.next();
                     Element dataelement1 = doc.createElement("DtTmLcTxn");
                     dataelement1.appendChild(
                     doc.createTextNode(DtTmLcTxn));
                     transaction.appendChild(dataelement1);        

                     break;
                 case 2:
                     System.out.println("enter the value");
                     String Pan=s.next();
                     Element dataelement2 = doc.createElement("Pan");
                     dataelement2.appendChild(
                     doc.createTextNode(Pan));
                     transaction.appendChild(dataelement2);
                     break;
                 case 3:
                     System.out.println("enter the value");
                     String CrdAcptTrmId=s.next();
                     Element dataelement3 = doc.createElement("CrdAcptTrmId");
                     dataelement3.appendChild(
                     doc.createTextNode(CrdAcptTrmId));
                     transaction.appendChild(dataelement3);
                     break;
                 case 4:
                     System.out.println("enter the value");
                     String AmtTxn=s.next();
                     Element dataelement4 = doc.createElement("AmtTxn");
                     dataelement4.appendChild(
                     doc.createTextNode(AmtTxn));
                     transaction.appendChild(dataelement4);
                     break;
                 case 5:
                     System.out.println("enter the value");
                     String CrdAcpIDCd=s.next();
                     Element dataelement5 = doc.createElement("CrdAcpIDCd");
                     dataelement5.appendChild(
                     doc.createTextNode(CrdAcpIDCd));
                     transaction.appendChild(dataelement5);
                     break;
                 case 6:
                     System.out.println("enter the value");
                     String CrdAcpNm=s.next();
                     Element dataelement6 = doc.createElement("CrdAcpNm");
                     dataelement6.appendChild(
                     doc.createTextNode(CrdAcpNm));
                     transaction.appendChild(dataelement6);
                     break;
                 case 7:
                     System.out.println("enter the value");
                     String CrdAcpLoc=s.next();
                     Element dataelement7 = doc.createElement("CrdAcpLoc");
                     dataelement7.appendChild(
                     doc.createTextNode(CrdAcpLoc));
                     transaction.appendChild(dataelement7);
                     break;
                 case 8:
                     System.out.println("enter the value");
                     String CrdAcpCity=s.next();
                     Element dataelement8 = doc.createElement("CrdAcpCity");
                     dataelement8.appendChild(
                     doc.createTextNode(CrdAcpCity));
                     transaction.appendChild(dataelement8);
                     break;
                 case 9:
                     System.out.println("enter the value");
                     String CrdAcpStNm=s.next();
                     Element dataelement9 = doc.createElement("CrdAcpStNm");
                     dataelement9.appendChild(
                     doc.createTextNode(CrdAcpStNm));
                     transaction.appendChild(dataelement9);
                     break;
                 case 10:
                     System.out.println("enter the value");
                     String CrdAcpCtryCd=s.next();
                     Element dataelement10 = doc.createElement("CrdAcpCtryCd");
                     dataelement10.appendChild(
                     doc.createTextNode(CrdAcpCtryCd));
                     transaction.appendChild(dataelement10);
                     break;
                 default:
                     if(ch>10)
                     System.out.println("reenter choice! wrong input");
             }
             
         }
         TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result =
         new StreamResult(new File("E:\\data.xml"));
         transformer.transform(source, result);
         // Output to console for testing
         StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);
                     
                   
         
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String argv[])
   {
        createXML cx=new createXML();
     cx.createxml();  
   }

}