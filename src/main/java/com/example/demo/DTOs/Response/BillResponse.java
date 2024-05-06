package com.example.demo.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BillResponse {

    private int billId;

    private double totalPrice;

    private String tradingCode;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createTime;

    private List<TicketResponse> ticketResponseList;

    private List<BillFoodResponse> billFoodResponseList;


}
