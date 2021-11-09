package sk.pay.testAssignment.controller;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PaymentController {

    public static final String CLIENT_ID = "12345";
    public static final String CURRENCY = "EUR";
    public static final String MS_TX_ID = "1234567890";
    public static final String FAMILY_NAME = "Mrkvička";
    public static final String FIRST_NAME = "Jožko";
    public static final String EMAIL = "jozo@gmail.com";
    public static final String COUNTRY = "SVK";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String OK = "OK";
    public static final String SIGN_FAILED = "Nespravny podpis";
    public static final String RESULT = "result";
    public static final String AMOUNT = "amount";
    public static final String CURR_CODE = "currCode";
    public static final String SIGN = "sign";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String CONF_PARAMETER_ESHOP_ID = "conf.parameter.EshopId";
    public static final String CONF_PARAMETER_MID = "conf.parameter.Mid";
    public static final String CONF_PARAMETER_PAYMENT_GATEWAY_URL = "conf.parameter.PaymentGatewayUrl";
    public static final String REDIRECT_SIGN_TRUE = "?RedirectSign=true";
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    public static final String RURL = "http://127.0.0.1:8024/rurl";

    @Autowired
    private Environment env;

    @RequestMapping("/request")
    public String viewTest(Model model){
        return "request";
    }


    @RequestMapping("/redirectToPayment")
    public String redirectToPayment(@RequestParam("sum") double sum, Model model){

        String uri = env.getProperty(CONF_PARAMETER_PAYMENT_GATEWAY_URL) + REDIRECT_SIGN_TRUE;
        String mid = env.getProperty(CONF_PARAMETER_MID);
        String eshopId = env.getProperty(CONF_PARAMETER_ESHOP_ID);

        String timestamp = DATE_FORMAT.format(new Date());
        String amount = String.format("%.2f", sum).replace(',', '.');
        String message = mid + amount + CURRENCY + MS_TX_ID + FIRST_NAME + FAMILY_NAME + timestamp;

        String generatedSign = generateSign(message);

        model.addAttribute("Mid", mid);
        model.addAttribute("EshopId", eshopId);
        model.addAttribute("MsTxnId", MS_TX_ID);
        model.addAttribute("Amount", amount);
        model.addAttribute("CurrAlphaCode", CURRENCY);
        model.addAttribute("ClientId", CLIENT_ID);
        model.addAttribute("FirstName", FIRST_NAME);
        model.addAttribute("FamilyName", FAMILY_NAME);
        model.addAttribute("Email", EMAIL);
        model.addAttribute("Country", COUNTRY);
        model.addAttribute("Timestamp", timestamp);
        model.addAttribute("Sign", generatedSign);
        model.addAttribute("RURL", RURL);
        model.addAttribute("Url", uri);
        return "redirect";
    }

    @GetMapping("/rurl")
    public String viewRurl(@RequestParam String MsTxnId, @RequestParam String Amount, @RequestParam String CurrCode,
                           @RequestParam String Result, @RequestParam String Sign, Model model){
        String message = MsTxnId + Amount + CurrCode + Result;
        String generatedSign = generateSign(message);
        String signResult = generatedSign.equalsIgnoreCase(Sign) ? OK : SIGN_FAILED;
        model.addAttribute(RESULT, Result);
        model.addAttribute(AMOUNT, Amount);
        model.addAttribute(CURR_CODE, CurrCode);
        model.addAttribute(SIGN, signResult);
        model.addAttribute(TRANSACTION_ID, MsTxnId);
        return "rurl";
    }

    public String generateSign(String message)	{
        try {

            String key = env.getProperty("conf.parameter.Key");
            String iv = env.getProperty("conf.parameter.IV");

            Security.addProvider(new BouncyCastleProvider());
            byte[]	keyBytes	=	Hex.decode(key);
            byte[]	ivBytes	=	iv.getBytes();
            SecretKeySpec secretKeySpec	=	new SecretKeySpec(keyBytes,	"AES");
            IvParameterSpec ivSpec	=	new IvParameterSpec(ivBytes);
            Cipher encryptCipher	=	Cipher.getInstance("AES/CBC/PKCS7Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE,	secretKeySpec,	ivSpec);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[]	sha1Hash	=	md.digest(message.getBytes(StandardCharsets.UTF_8));
            byte[]	encryptedData	=	encryptCipher.doFinal(sha1Hash);
            return Hex.toHexString(encryptedData).substring(0,32);
        }	catch (Exception	e)	{
            logger.error("ERROR!",	e);
            return null;
        }
    }

}
