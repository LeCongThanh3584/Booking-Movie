package com.example.demo.Services.Bill;

import com.example.demo.DTOs.Request.BillRequest;
import com.example.demo.DTOs.Response.BillResponse;
import com.example.demo.Entities.Bill;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Exceptions.UnAvailableException;

import java.util.List;

public interface IBillService {
    List<Bill> getAllBillByUserId(Integer userId);
    BillResponse addNewBill(BillRequest billRequest) throws NotFoundException, UnAvailableException;
    Bill getBillByBillId(Integer billId);
    String deleteBill(Integer userId, Integer billId);
    String sendEmailConfirmPayment(Integer billId, Integer userId);
}
