package com.budiluhur.kkp.common;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
 
public class Encryption {
 
	private String securityKey = "a2lzbWFuIGltYW4g";
 
    public void tes() throws Exception {
        String kunciEnkripsi = "a2lzbWFuIGltYW4g";
        String pesanPlainText = "Ini Budi. \nIni Ibu Budi. \nIni Bapak Budi. \nIni Iwan, adik Budi. \nIni kakak Budi, Wati.";
        String pesanTerenkripsi = encryptAES(pesanPlainText, kunciEnkripsi);
        String hasilDekripsi = decryptAES(pesanTerenkripsi, kunciEnkripsi);
        //a2lzbWFuIGltYW4g
 
        System.out.println("PESAN ASLI : \n" + pesanPlainText);
        System.out.println("\nHASIL ENKRIPSI : \n" + pesanTerenkripsi);
        System.out.println("\nHASIL DEKRIPSI : \n" + hasilDekripsi);
    }
 
    public String encryptAES(String Data, String k)
            throws Exception {
        Cipher c = Cipher.getInstance("AES");
        Key key = generateKey(this.securityKey);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        // String encryptedValue = new BASE64Encoder().encode(encVal); //
        String encryptedValue = DatatypeConverter.printBase64Binary(encVal);
        return encryptedValue;
    }
 
    // Method Dekripsi AES
    public String decryptAES(String encryptedData, String k)
            throws Exception {
	System.out.println("key sec : " + this.securityKey);
        Cipher c = Cipher.getInstance("AES");
        Key key = generateKey(this.securityKey);
        c.init(Cipher.DECRYPT_MODE, key);
 
        // byte[] decordedValue = new
        byte[] decordedValue = DatatypeConverter
                .parseBase64Binary(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
 
    private Key generateKey(String k) throws Exception {
        Key key = new SecretKeySpec(convertToByte(k), "AES");
        return key;
    }
 
    private byte[] convertToByte(String k) {
        byte[] array_byte = new byte[16];
        int i = 0;
        while (i < k.length()) {
            array_byte[i] = (byte) k.charAt(i);
            i++;
        }
        if (i < 16) {
            while (i < 16) {
                array_byte[i] = (byte) i;
                i++;
            }
        }
        return array_byte;
    }
}
