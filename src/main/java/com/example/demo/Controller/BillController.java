package com.example.demo.Controller;

import com.example.demo.DTOs.Request.BillRequest;
import com.example.demo.DTOs.Response.BillResponse;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Exceptions.UnAvailableException;
import com.example.demo.Mapper.BillMapper;
import com.example.demo.Services.Bill.IBillService;
import com.example.demo.Utils.GetUserLoginUtil;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BillController {

    @Autowired
    private IBillService iBillService;

    @GetMapping("/user/get-all-bill-user-login")  //API lấy tất cả các bill của người dùng đăng nhập
    public ResponseEntity<?> getAllBill() {
        User userLogin = GetUserLoginUtil.getUserLogin();
        System.out.println(userLogin.getUserId());
        List<BillResponse> billResponseList = iBillService.getAllBillByUserId(userLogin.getUserId()).stream().map(
                item -> BillMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        return new ResponseEntity<>(billResponseList, HttpStatus.OK);
    }

    @PostMapping("/user/create-new-bill") //API tạo mới bill
    public ResponseEntity<?> createNewBill(@RequestBody @Valid BillRequest billRequest,
                                           BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return new ResponseEntity<>(errorMsg, HttpStatus.OK);
        }

        try {
            BillResponse billResponse = iBillService.addNewBill(billRequest);
            return new ResponseEntity<>(billResponse, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UnAvailableException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
