/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import FileParser.ParseFile;
import java.nio.ByteBuffer;
import java.util.List;


/**
 *
 * @author Divya
 */
public class UtilityClass {
    
    public List<String> GetvalS(String FilePath, String Key) {
        List<String> Result = ParseFile.GetVal_old(FilePath, Key);
        if (Result.isEmpty()) {
            return null;
        }
        while (Result.get(0).startsWith("PATH:")) {
            while (Result.get(0).startsWith("PATH:")) {
                Result.set(0, Result.get(0).substring(5));
            }
            
            Result = ParseFile.GetVal_old(Result.get(0), Key);
        }
        return Result;
    }

      public String HEXArraytoString(byte[] _bcd) {

            String sx = "";
            for (int i = 0; i < _bcd.length; i++) {
                String _s = Integer.toHexString(Byte.toUnsignedInt(_bcd[i]));

                if (_s.length() == 1) {
                    _s = "0" + _s;
                }
                sx = sx + _s;//.toUpperCase();
            }
            //byte[] x=Util.DecStringToByteArray(sx);
            return sx.toUpperCase();//Util.DecStringToByteArray(sx);

            //return  _bcd;
        }

        //Example 0x12, 0x34, 0x45 will be converted to 12,34,45
        public byte[] BCDtoDECAsItIs(byte[] _bcd) {

            //  _bcd[0]=(byte)254;
            String sx = "";
            for (int i = 0; i < _bcd.length; i++) {
                String _s = Integer.toHexString(Byte.toUnsignedInt(_bcd[i]));

                if (_s.length() == 1) {
                    _s = "0" + _s;
                }
                sx = sx + _s.toUpperCase();
            }
            return DecStringToByteArray(sx.toUpperCase());

            //return  _bcd;
        }

        public byte[] BCDToDEC(byte[] _bcd) {

            String sx = "";
            for (int i = 0; i < _bcd.length; i++) {
                String _s = Integer.toHexString(Byte.toUnsignedInt(_bcd[i]));

                if (_s.length() == 1) {
                    _s = "0" + _s;
                }
                sx = sx + _s;
            }
            return DecStringToByteArray(sx);
        }

        public byte[] DecStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 10) * 10)
                        + Character.digit(s.charAt(i + 1), 10));
                if (data[i / 2] == -1) {
                    data[i / 2] = (byte) s.charAt(i);
                }
            }
            return data;
        }

        public byte[] DecToBCD(byte[] _dec) {

            String sx = "";
            for (int i = 0; i < _dec.length; i++) {
                String _s = Integer.toString(Byte.toUnsignedInt(_dec[i]));
                if (_s.length() == 1 || _s.length() == 3) {
                    _s = "0" + _s;
                }
                sx = sx + _s;
            }

            return hexStringToByteArray(sx);
        }

        public byte[] longToBytes(long x) {
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.putLong(x);
            return buffer.array();
        }

        public long bytesToLong(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.put(bytes);
            buffer.flip();//need flip 
            return buffer.getLong();
        }

        public byte[] hexStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i + 1), 16));
            }
            return data;
        }

        /*
         * long number to bcd byte array e.g. 123 --> (0000) 0001 0010 0011
         * e.g. 12 ---> 0001 0010
         */
        public byte[] DecToBCDArray(long num) {
            int digits = 0;

            long temp = num;
            while (temp != 0) {
                digits++;
                temp /= 10;
            }

            int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;
            boolean isOdd = digits % 2 != 0;

            byte bcd[] = new byte[byteLen];

            for (int i = 0; i < digits; i++) {
                byte tmp = (byte) (num % 10);

                if (i == digits - 1 && isOdd) {
                    bcd[i / 2] = tmp;
                } else if (i % 2 == 0) {
                    bcd[i / 2] = tmp;
                } else {
                    byte foo = (byte) (tmp << 4);
                    bcd[i / 2] |= foo;
                }

                num /= 10;
            }

            for (int i = 0; i < byteLen / 2; i++) {
                byte tmp = bcd[i];
                bcd[i] = bcd[byteLen - i - 1];
                bcd[byteLen - i - 1] = tmp;
            }

            return bcd;
        }

        public String BCDtoString(byte bcd) {
            StringBuffer sb = new StringBuffer();

            byte high = (byte) (bcd & 0xf0);
            high >>>= (byte) 4;
            high = (byte) (high & 0x0f);
            byte low = (byte) (bcd & 0x0f);

            sb.append(high);
            sb.append(low);

            return sb.toString();
        }

        public String BCDtoString(byte[] bcd) {

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < bcd.length; i++) {
                sb.append(BCDtoString(bcd[i]));
            }

            return sb.toString();
        }

}
