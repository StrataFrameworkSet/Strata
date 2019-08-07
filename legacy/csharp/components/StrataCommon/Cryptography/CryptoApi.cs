using System;
using System.IO;
using System.Text;
using System.Security.Cryptography;

namespace Strata.Common.Cryptography {
    /// <summary>
    /// This class exposes function to Encrypt and Decrypt the key information like database password.
    /// </summary>
    public class CryptoApi {
        const string ENCRYPTION_KEY = @"&%#@?,:*";

        /// <summary>
        /// This is the public shared function which takes the input text that needs to be Encrypted and returns the Encrypted Cipher Text.   
        /// </summary>
        /// <param name="pass">The input text that needs to be Encrypted</param>
        /// <returns>String</returns>
        public static String EncryptPass(String pass) {
            return Encrypt(pass, ENCRYPTION_KEY);
        }

        /// <summary>
        /// This is the public shared function which takes the cipher text that needs to be Decrypted and returns the original text.    
        /// </summary>
        /// <param name="ciphertext">The cipher text that needs to be decrypted</param>
        /// <returns>String</returns>
        public static String DecryptPass(String ciphertext) {
            return Decrypt(ciphertext, ENCRYPTION_KEY);
        }

        private static String Encrypt(String strText, String strEncrKey) {
            byte[] IV = { 0x12, 0x34, 0x56, 0x78, 0x90, 0xAB, 0xCD, 0xEF };

            try {
                byte[] bKey = Encoding.UTF8.GetBytes(strEncrKey.Substring(0, 8));
                DESCryptoServiceProvider des = new DESCryptoServiceProvider();
                Byte[] inputByteArray = Encoding.UTF8.GetBytes(strText);
                MemoryStream ms = new MemoryStream();
                CryptoStream cs = new CryptoStream(ms, des.CreateEncryptor(bKey, IV), CryptoStreamMode.Write);
                cs.Write(inputByteArray, 0, inputByteArray.Length);
                cs.FlushFinalBlock();
                return Convert.ToBase64String(ms.ToArray());
            } catch (Exception) {
                return string.Empty;
            }
        }

        private static String Decrypt(String strText, String strEncrKey) {
            byte[] IV = { 0x12, 0x34, 0x56, 0x78, 0x90, 0xAB, 0xCD, 0xEF };

            try {
                byte[] bKey = Encoding.UTF8.GetBytes(strEncrKey.Substring(0, 8));
                DESCryptoServiceProvider des = new DESCryptoServiceProvider();
                Byte[] inputByteArray = Convert.FromBase64String(strText);
                MemoryStream ms = new MemoryStream();
                CryptoStream cs = new CryptoStream(ms, des.CreateDecryptor(bKey, IV), CryptoStreamMode.Write);
                cs.Write(inputByteArray, 0, inputByteArray.Length);
                cs.FlushFinalBlock();
                Encoding encoding = Encoding.UTF8;
                return encoding.GetString(ms.ToArray());
            } catch (Exception) {
                return string.Empty;
            }
        }
    }
}