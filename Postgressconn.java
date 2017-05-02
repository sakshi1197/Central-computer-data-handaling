/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;
import FileParser.ParseFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.file.*;
import xml_library.*;
import java.sql.*;
import java.text.*;
//import java.util.List;
//import java.util.Scanner;
//import java.util.HashMap;
import java.util.*;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import smsservice.SMS;


/**
 *
 * @author cdacnoida
 */
public class Postgressconn 
{
    Connection con=null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stmt =null,stmt1=null,stmt2=null,stmt3=null,stmt4=null,stmt5=null;
    
    List<String> dttmlctxn_s = new ArrayList<String>();
    List<String> pan_s= new ArrayList<String>();
    List<String> psn_s= new ArrayList<String>();
    List<String> TxnPlace_s= new ArrayList<String>();
    List<String> txntypq_s= new ArrayList<String>();
    List<String> amtadd_s= new ArrayList<String>();
    List<String> crdacpttrmid_s= new ArrayList<String>();
    List<String> amttxn_s= new ArrayList<String>();
    List<String> mti_s= new ArrayList<String>();
    List<String> funcd_s= new ArrayList<String>();
    List<String> recnum_s= new ArrayList<String>();
    List<String> ccycdtxn_s= new ArrayList<String>();
    List<String> proccd_s= new ArrayList<String>();
    List<String> crdacpbusscd_s= new ArrayList<String>();
    List<String> posdatacd_s= new ArrayList<String>();
    List<String> posentmode_s= new ArrayList<String>();
    List<String> poscondcd_s= new ArrayList<String>();
    List<String> actncd_s= new ArrayList<String>();
    List<String> tc_s= new ArrayList<String>();
    List<String> acqinstcd_s= new ArrayList<String>();
    List<String> ard_s= new ArrayList<String>();
    List<String> txnorginstcd_s= new ArrayList<String>();
    List<String> crdacpidcd_s= new ArrayList<String>();
    List<String> crdacpnm_s= new ArrayList<String>();
    List<String> crdacploc_s= new ArrayList<String>();
    List<String> crdacpcity_s= new ArrayList<String>();
    List<String> crdacpstnm_s= new ArrayList<String>();
    List<String> crdacpctrycd_s= new ArrayList<String>();
    String ps,d,u,i,p;
    int port;
    
 //   SendSMS send = new SendSMS(System.getProperty("user.dir")+ "\\CONFIG\\FileConfig.conf");
    
    
    public int Read_from_db()
    {
         int i=0;
         try
         {
             stmt = con.createStatement();
             stmt1 = con.createStatement();
             stmt2 = con.createStatement();
             stmt3=con.createStatement();
             stmt4=con.createStatement();
             stmt5=con.createStatement();
             String sql = "SELECT * FROM dynamic_tb where ((modified=1)AND((txntypq='03')OR(txntypq='08')))" ;
             String sql1="SELECT * FROM static_tb ORDER BY ID DESC LIMIT 1";
             String sql2="SELECT * FROM configurable_tb ORDER BY ID DESC LIMIT 1";
             System.out.println("query : " + sql);
             
             ResultSet rs = stmt.executeQuery(sql);
             ResultSet rs1 = stmt1.executeQuery(sql1);
             ResultSet rs2 = stmt2.executeQuery(sql2);
             String table=null;
             int j=1;
            // 
             while(rs.next())
             {
                 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 String time_stmp = null;
                 time_stmp = rs.getString(1);
               //time_stmp="2008-04-27 15:51:20";
                 java.util.Date d = df.parse(time_stmp);
                 SimpleDateFormat df2 = new SimpleDateFormat("yyMMddHHmmss");
                 String ts = new String( df2.format(d) );
                 dttmlctxn_s.add(ts);
                 pan_s.add( rs.getString(2));
                 
                      psn_s.add(rs.getString(3));
                 recnum_s.add(Integer.toString(j));
                 TxnPlace_s.add( rs.getString(4));
                 txntypq_s.add( rs.getString(5));
                 amtadd_s.add(rs.getString(6));
                 crdacpttrmid_s.add(rs.getString(7));
                 amttxn_s.add(rs.getString(8));
                 tc_s.add(rs.getString(9));
                 j++;
                 i++;
              
              //ResultSet rs8=stmt.executeUpdate(s6);
              
                 
                 }
              while(rs1.next())
              {
                  mti_s.add(rs1.getString(2));
                  funcd_s.add(rs1.getString(3));
                  ccycdtxn_s.add(rs1.getString(4));
                  proccd_s.add(rs1.getString(5));
                  crdacpbusscd_s.add(rs1.getString(6));
                  posdatacd_s.add(rs1.getString(7));
                  posentmode_s.add(rs1.getString(8));
                  poscondcd_s.add(rs1.getString(9));
                  actncd_s.add(rs1.getString(10));
               }
              while(rs2.next())
              {
                acqinstcd_s.add(rs2.getString(2));
                ard_s.add(rs2.getString(3));
                txnorginstcd_s.add(rs2.getString(4));
         //    crdacpidcd_s.add(rs2.getString(6));
          //   crdacpnm_s.add(rs2.getString(7));
          //   crdacploc_s.add(rs2.getString(8));
          //   crdacpcity_s.add(rs2.getString(9));
          //   crdacpstnm_s.add(rs2.getString(10));
          //   crdacpctrycd_s.add(rs2.getString(11));
            
              }
              String sql3 = "UPDATE dynamic_tb SET modified=0 WHERE modified=1" ; 
              stmt.execute(sql3);
          //   String sql3 = "UPDATE static_tb SET modified=0 WHERE modified=1" ; 
           //  stmt.execute(sql3);
              //System.out.println("table is"+table);
              stmt.close();
              stmt1.close();
              stmt2.close();
              con.close();
          }
        catch (Exception e)
        {
         e.printStackTrace();
        }  
        return i;
         
         
      //   String sql = "SELECT * FROM DYNAMIC_TB;";
       //  ResultSet rs = stmt.executeQuery(sql);
       //  stmt.close();
         
         
        // do something appropriate with the exception, *at least*:
       
        }
   //      stmt.executeUpdate(sql);
        public int getDbConnection() throws FileNotFoundException, IOException 
        {
            int status = 1;
            String uname,pswd,port,db;
        //Connection con;
            try
            {
                HashMap<String, String> HM=new HashMap<String,String>();
                HM=ParseFile.GetVal("conf_d.conf", "Con");
                db=HM.get("Con.database");
                uname=HM.get("Con.username");
                pswd=HM.get("Con.password");
                port=HM.get("Con.port");
                String Driver=HM.get("Con.driver");
                String str=HM.get("Con.con_string");
                String Host=HM.get("Con.host");
                /*String io_file=HM.get("path.input");
                String backup_file=HM.get("path.backup");
                String bank=HM.get("path.bank_file");*/
                Class.forName(Driver);
                String st=str+"//"+Host+port+"/"+db;
                con = DriverManager.getConnection(st,
                uname, pswd);
                con.setAutoCommit(true);
                stmt5=con.createStatement();
                System.out.println("Database Connected.");
            
            }   
            catch (ClassNotFoundException | SQLException e) 
            {
                status = 0;
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);

            }

            return status;
        }
    @SuppressWarnings("empty-statement")
        public void createTb() throws SQLException
        {
            // Scanner s=new Scanner(System.in);
            stmt = con.createStatement();
       
       // String tbname=s.next();
            String sql ="CREATE TABLE IF NOT EXISTS Static_tb"+" ( ID  SERIAL PRIMARY KEY ,\n" +
                    "MTI INT    ,\n"+
                    "     FunCd   INT   ,\n" +
                    
                    "CcyCdTxn        INT, \n" +
                    "      ProcCd       INT,\n" +
                    "  CrdAcpBussCd  INT, \n" +
                    "   PosDataCd  TEXT, \n" +
                    "   PosEntMode INT, \n" +
                    "   PosCondCd  INT, \n" +
                    "      ActnCd   INT );";
        
            stmt.executeUpdate(sql);
         // stmt.executeUpdate(sql1);
                  // System.out.println("error");
       // else
            System.out.println("Static_tb created");
            stmt.close();
            stmt1= con.createStatement(); 
       //   System.out.println("enter the name of the table");
       // tbname=s.next();
             String sql2 ="CREATE TABLE IF NOT EXISTS Dynamic_tb "+"(DtTmLcTxn TIMESTAMP ,"+
                "   PAN BIGINT , " +
                "   PSN BIGINT ," +
                "   TxnPlace TEXT ,"+
                "   TxnTypq TEXT ,"+
                "   AmtAdd INT ,"+
                "   crdacpttrmid TEXT ," +
                "   AmtTxn TEXT ,\n"+
                "   TC     TEXT,\n"+
                "   modified INT);";
            stmt1.executeUpdate(sql2);
            System.out.println("Dynamic table created");
            stmt.close();
            stmt2 = con.createStatement(); 
       
        //tbname=s.next();
            String sql1 ="CREATE TABLE IF NOT EXISTS Configurable_tb" + "(ID  SERIAL PRIMARY KEY ,\n"+
                 "AcqInstCd TEXT ,\n"+
                "   ARD TEXT ,\n"+
                "   TxnOrgInstCd TEXT ,\n" +
                "   crdacpttrmid TEXT ,\n"+
                "   CrdAcpIDCd TEXT ,\n" +
                "   CrdAcpNm TEXT ,\n" +
                "   CrdAcpLoc TEXT ,\n" +
                "   CrdAcpCity TEXT ,\n" +
                "   CrdAcpStNm TEXT ,\n"+
                "   CrdAcpCtryCd TEXT);";
        stmt2.executeUpdate(sql1);
        System.out.println("Configurable_tb created");
         stmt2.close();
         stmt3=con.createStatement();
    
         stmt = con.createStatement();
       
       // String tbname=s.next();
            String sql5 ="CREATE TABLE IF NOT EXISTS sms1"+" ( PAN TEXT ,\n" + " phnum TEXT);";
        
            stmt.executeUpdate(sql5);
            String arr[],num[];
             HashMap<String, String> HM=new HashMap<String,String>();
                HM=ParseFile.GetVal("conf_d.conf", "msg");
                String p1=HM.get("msg.pan1");
                String p2=HM.get("msg.pan2");
                String p3=HM.get("msg.pan3");
                String p4=HM.get("msg.pan4");
                String p5=HM.get("msg.pan5");
                String pn1=HM.get("msg.ph1");
                String pn2=HM.get("msg.ph2");
                String pn3=HM.get("msg.ph3");
                String pn4=HM.get("msg.ph4");
                String pn5=HM.get("msg.ph5");
            arr=new String[]{p1,p2,p3,p4,p5};
            num=new String[]{pn1,pn2,pn3,pn4,pn5};
                 System.out.println("sms1 created");
                 for(int i=0;i<arr.length;i++)
                 {
                     String sql6="insert into sms1 values('"+arr[i]+"','"+num[i]+"')";
                     stmt.executeUpdate(sql6);
            
                 }
            System.out.println("inserted");
            
         // stmt.executeUpdate(sql1);
                  // System.out.println("error");
       // else
       
            stmt.close();
        }
    public void parseXML()
    {

        try 
        {	
            String workingDir = System.getProperty("user.dir");
            String query1=null;
            HashMap<String, String> HM1=new HashMap<String,String>();
            
              
               HM1=ParseFile.GetVal("conf_d.conf", "path");
               
               
                String io_file=HM1.get("path.input");
                String backup_file=HM1.get("path.backup");
                String bank=HM1.get("path.bank_file");
         File inputFile = new File( io_file);
         File[] allfiles=inputFile.listFiles();
         int i;
        for(i=0;i<allfiles.length;i++)
        {
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         dbFactory.setIgnoringElementContentWhitespace(true);
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         
         Document doc = dBuilder.parse(allfiles[i]);
         removeWhitespaces(doc);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         
         NodeList nl = doc.getElementsByTagName("txn");
         for(int temp1=0;temp1 < nl.getLength();temp1++)
         {
             String table_old=null;
            String value=null,value1=null,value2=null,value3=null,value4=null;
            int static_i=0,configurable_i=0,header_i=0,trailer_i=0;
            java.sql.Timestamp ts=null;
         Node node = nl.item(temp1);
         Element el = (Element)node;
        
         NodeList nList = el.getChildNodes();     
         System.out.println("length "+nList.getLength());
         for (int temp = 0; temp < nList.getLength(); temp++) 
         {
            Node nNode = nList.item(temp);
             if (nNode.getNodeType() == Node.ELEMENT_NODE) 
             {
                System.out.println("node value : " + nNode.getTextContent());
                System.out.println("node value : " + nNode.getNodeName());
               
           stmt = con.createStatement();
           stmt1=con.createStatement();
      
          String column=nNode.getNodeName().substring(1);
          column=column.toLowerCase();
           String query="SELECT TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME LIKE '"+ column+"'";
            System.out.println("query : " + query);
            ResultSet rs = stmt.executeQuery(query);
            String table=null;
      while(rs.next())
      {
            table=  rs.getString(1);
            if(table.equalsIgnoreCase("dynamic_tb"))
            {
                 if (temp==0)
                {
                    value1=nNode.getTextContent();
             
            DateFormat dfm = new SimpleDateFormat("yyMMddHHmmss");
            java.util.Date date = dfm.parse(value1);
             ts = new Timestamp(date.getTime());
              query1 = "INSERT INTO "+ table+ "("+column+", modified)"+"VALUES " + "('"+ ts+"',1)" ; }
            // query1 = "INSERT INTO "+ table+ "("+column+", modified)"+"VALUES " + "('"+ nNode.getTextContent()+"',1)" ; }
                else
                 query1="UPDATE " + table+ " SET "+ column+"= '"+nNode.getTextContent()+"' WHERE dttmlctxn= '" +ts+"'";
               //  query1="UPDATE " + table+ " SET "+ column+"= '"+nNode.getTextContent()+"' WHERE dttmlctxn= '" +value1+"'";
            }
           
            else if(table.equalsIgnoreCase("static_tb"))
            {
                if(static_i==0)
                {
                    value2=nNode.getTextContent();
                query1 = "INSERT INTO "+ table+ "("+column+")"+"VALUES " + "('"+ nNode.getTextContent()+"')" ; 
                static_i++;
                 }
              //  if(table.equalsIgnoreCase("dynamic_tb"))
               else  query1= "UPDATE " + table+ " SET "+ column+"= '"+nNode.getTextContent()+"' WHERE mti=' "+value2+"'";
            }
            else if(table.equalsIgnoreCase("configurable_tb"))
            {
                if(configurable_i==0)
                {
                    
                value3=nNode.getTextContent();
                query1="INSERT INTO "+ table+ "("+column+")"+"VALUES " + "('"+ nNode.getTextContent()+"')" ; 
                configurable_i++;
                }
                else query1= "UPDATE " + table+ " SET "+ column+"= '"+nNode.getTextContent()+"' WHERE crdacpttrmid='" +value3+"'";
            }
       
          
          System.out.println("query : " + query1);
          stmt1.executeUpdate(query1);
         System.out.println("table name is " + table);
       
                 
       
      }
      
            
             } 
         
         }
          
         
         }
         
          System.out.println("file name is"+allfiles[i].getName());
       allfiles[i].renameTo(new File(backup_file + allfiles[i].getName()));
       allfiles[i].delete();
          
   //  stmt1.executeQuery("Delete from configurable_tb WHERE CRDACPIDCD IS NULL");
         
}    
        
         stmt5=con.createStatement();
         String sql10 = "SELECT PAN,AmtTxn,TxnTypq FROM dynamic_tb WHERE TxnTypq='03'";
                 ResultSet rs10=stmt5.executeQuery(sql10);
                 String pa="",a="",t="",s="";
                 while(rs10.next())
                 {
                    pa=rs10.getString(1);
                  a=rs10.getString(2);
                  t=rs10.getString(3);
                 }
        String sql9="SELECT phnum FROM sms1 where pan='"+pa+"'";
                 ResultSet rs9=stmt5.executeQuery(sql9);
               while(rs9.next())
                 {
                     s=rs9.getString(1);
                     
                 }
               
       
                 stmt5.close();
   //            send.sendMessage(pa, t, a, s);
      
     
}   
    catch (Exception e)
       {
           System.out.println(e);
      }
    
      
    }
    
    public void removeWhitespaces(Node pRootNode)
    {
    if (pRootNode != null) {
        NodeList tList = pRootNode.getChildNodes();
        if (tList != null && tList.getLength() > 0) {
            ArrayList<Node> tRemoveNodeList = new ArrayList<Node>();
            for (int i = 0; i < tList.getLength(); i++) {
                Node tChildNode = tList.item(i);
                if (tChildNode.getNodeType() == Node.TEXT_NODE) {
                    if (tChildNode.getTextContent() == null
                            || "".equals(tChildNode.getTextContent().trim()))
                        tRemoveNodeList.add(tChildNode);
                } else
                    removeWhitespaces(tChildNode);
            }
            for (Node tRemoveNode : tRemoveNodeList) {
                pRootNode.removeChild(tRemoveNode);
            }
        }
    }
}
 
     public static void main(String args[]) throws SQLException, FileNotFoundException, IOException
     {
        Postgressconn pgc=new Postgressconn();
         pgc.getDbConnection();
         pgc.createTb();
        Timer timer=new Timer();
        TimerTask timerTask1 = new TimerTask() {
       
           public void run()
    {
        
    Postgressconn pgc=new Postgressconn();
               try {
                   pgc.getDbConnection();
               } catch (FileNotFoundException ex) {
                   Logger.getLogger(Postgressconn.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                   Logger.getLogger(Postgressconn.class.getName()).log(Level.SEVERE, null, ex);
               }
              
    //pgc.createTb();
      pgc.parseXML();
    }
       };
    
          
    timer.schedule(timerTask1, 0, 5*1000);
   TimerTask timerTask = new TimerTask() {
          public void run()
    {
      
        Postgressconn pgc=new Postgressconn();
              try {             
                  pgc.getDbConnection();
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(Postgressconn.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(Postgressconn.class.getName()).log(Level.SEVERE, null, ex);
              }
            
        int count=0;
        count=pgc.Read_from_db();
         
        if(count!=0)
        {
        HashMap<String,List<String>> hm=new HashMap<String, List<String>>();
         hm.put("dttmlctxn",pgc.dttmlctxn_s);
         hm.put("pan",pgc.pan_s);
         hm.put("psn",pgc.psn_s);
         hm.put("TxnPlace",pgc.TxnPlace_s);
          hm.put("txntypq",pgc.txntypq_s);
           hm.put("amtadd",pgc.amtadd_s);
         hm.put("crdacpttrmid",pgc.crdacpttrmid_s);
         hm.put("amttxn",pgc.amttxn_s);
         hm.put("tc",pgc.tc_s);
         hm.put("mti",pgc.mti_s);
          hm.put("funcd",pgc.funcd_s);       
        hm.put("recnum",pgc.recnum_s);       
        hm.put("ccycdtxn",pgc.ccycdtxn_s);                
        hm.put("proccd",pgc.proccd_s);                
        hm.put("crdacpbusscd",pgc.crdacpbusscd_s);                
        hm.put("posdatacd",pgc.posdatacd_s);                
        hm.put("posentmode",pgc.posentmode_s);       
        hm.put("poscondcd",pgc.poscondcd_s);       
        hm.put("actncd",pgc.actncd_s);     
         hm.put("acqinstcd",pgc.acqinstcd_s);
        hm.put("ard",pgc.ard_s);
        hm.put("txnorginstcd",pgc.txnorginstcd_s);
     //   hm.put("crdacpidcd",pgc.crdacpidcd_s);
      //  hm.put("crdacpnm",pgc.crdacpnm_s);
      //  hm.put("crdacploc",pgc.crdacploc_s);
      //  hm.put("crdacpcity",pgc.crdacpcity_s);
      //  hm.put("crdacpstnm",pgc.crdacpstnm_s);
      //  hm.put("crdacpctrycd",pgc.crdacpctrycd_s);
        
        
        
        
         xmllib cx=new xmllib();
        cx.createxml_bank(hm,count);
        }
        // do something appropriate with the exception, *at least*:
    
    }  
      };
      
      timer.schedule(timerTask, 0, 60*1000);

       
    // Thread thread1 = new Thread(new Postgressconn(),"thread1"); //Thread created mythread.start(); //Thread started now but not running myrunnable.start();
     // thread1.start();
        
        }
    
// run method of timer task

}
