package com.example.demo.Controller;

import com.example.demo.Config.PaymentConfig;
import com.example.demo.Entities.Bill;
import com.example.demo.Entities.User;
import com.example.demo.Services.Bill.IBillService;
import com.example.demo.Utils.GetUserLoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    @Autowired
    private IBillService iBillService;

    @GetMapping("/user/payment") //API gửi đường link thanh toán hoá đơn
    public ResponseEntity<?> geyPayment(@RequestParam Integer billId, HttpServletRequest request) throws UnsupportedEncodingException {
        Bill billPayment = iBillService.getBillByBillId(billId);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) billPayment.getTotalPrice() * 100;  //Để triệt tiêu phần thập phân trc khi gửi sang VNPay
        String bankCode = "NCB";
        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_IpAddr = "192.0.0.1";

        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl + "?id=" + billId);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

        return new ResponseEntity<>(paymentUrl, HttpStatus.OK);
    }

    @GetMapping("/user/check-payment") //API để xác nhận là thanh toán thành công hay thất bại
    public ResponseEntity<?> checkPayment(@RequestParam("vnp_ResponseCode") String responseCode, @RequestParam("billId") Integer billId) {

        User userLogin = GetUserLoginUtil.getUserLogin();

        if(responseCode.equals("00")) { //Tức là thanh toán thành công, gửi xác nhận về email
            String response = iBillService.sendEmailConfirmPayment(billId, userLogin.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else { //Thanh toán thất bại
            String response = iBillService.deleteBill(userLogin.getUserId(), billId); //Thanh toán thất bại thì xoá bill đi, bắt nguười dùng đặt vé lại
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
