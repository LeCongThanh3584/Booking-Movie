package com.example.demo.Mapper;

import com.example.demo.DTOs.Response.BillResponse;
import com.example.demo.Entities.Bill;

import java.util.stream.Collectors;

public class BillMapper {

    public static BillResponse mapFromEntityToResponse(Bill bill) {
        return new BillResponse(
                bill.getBillId(),
                bill.getTotalPrice(),
                bill.getTradingCode(),
                bill.getCreateTime(),
                bill.getTicketList().stream().map(
                        item -> TicketMapper.mapFromEntityToResponse(item)
                ).collect(Collectors.toList()),
                bill.getBillFoodList().stream().map(
                        item -> BillFoodMapper.mapFromEntityToResponse(item)
                ).collect(Collectors.toList())
        );
    }
}
