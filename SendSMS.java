/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import FileParser.ParseFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import smsservice.SMS;

/**
 *
 * @author Divya
 */
public class SendSMS {
    
    String sendMessage = "TRUE";

    String SMSEnabled = "";
    String SMSTrigger = "";
    String MSGString = "";
    //String TxnAmnt = "";
   // String PAN = "";
   // String entry_exit = "";
    List<String> PANPhoneList = new ArrayList<String>();
    
    HashMap<String, String> panphone = new HashMap<String, String>();
    UtilityClass utils = new UtilityClass();
    //XMLGeneration xml;
    
    public SendSMS(String ConfigFilePath) {
        SMSEnabled = utils.GetvalS(ConfigFilePath, "SMSENABLE").get(0);
        SMSTrigger = utils.GetvalS(ConfigFilePath, "SMSTRIGGER").get(0);
        MSGString = utils.GetvalS(ConfigFilePath, "SMSString").get(0);
        //TxnAmnt =xml.TxnAmnt;
       // entry_exit = xml.SMSTrigger;
       // PAN = xml.PAN;
//        PANPhoneList = utils.GetvalS(ConfigFilePath, "SMSLIST.PAN:PHONE");
//        for (int _li = 0; _li < PANPhoneList.size(); _li++) {
//            String _d = PANPhoneList.get(_li);
//            panphone.put(_d.split(":")[0], _d.split(":")[1]);

        }

  //  }

    public boolean sendMessage(String PAN, String TxnType, String TxnAmnt,String PhoneNo ) {
        boolean status = false;
       // String PhoneNo = "";
        String Msg = (MSGString.replace("#", PAN)).replace("$", TxnAmnt) ;
        try {
            if (SMSEnabled.equals(sendMessage) && (TxnType.equals(SMSTrigger) || SMSTrigger.equals("ALL"))){

               // PhoneNo = panphone.get(PAN);
                if(PhoneNo != null && PAN !=null){
                String Response = SMS.sendSMS(PhoneNo, Msg, true);
                System.out.println(Response);
                if (Response.contains("\"MsgStatus\":\"Sent\"") == true){
                    System.out.println("SMS sent successfully !!");
                status = true;
                }
                else
                    System.out.println("SMS can not be sent !!");
                
            }
                
            else{
                System.out.println("Insufficient details to send SMS !!");
            
            }
            }
            else{
               System.out.println("SMS service unavailable !!");  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    
    
}
