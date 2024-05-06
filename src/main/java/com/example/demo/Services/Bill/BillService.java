package com.example.demo.Services.Bill;

import com.example.demo.DTOs.Request.BillRequest;
import com.example.demo.DTOs.Request.OrderFoodRequest;
import com.example.demo.DTOs.Response.BillResponse;
import com.example.demo.Entities.*;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Exceptions.UnAvailableException;
import com.example.demo.Mapper.BillFoodMapper;
import com.example.demo.Mapper.TicketMapper;
import com.example.demo.Repositories.*;
import com.example.demo.Services.Email.EmailService;
import com.example.demo.Utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class BillService implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private BillFoodRepository billFoodRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Bill> getAllBillByUserId(Integer userId) {
        return billRepository.getAllByUserId(userId);
    }

    @Override
    public BillResponse addNewBill(BillRequest billRequest) throws NotFoundException, UnAvailableException {

        double totalPrice = 0;

        Optional<User> userOptional = userRepository.findById(billRequest.getUserId());
        if(userOptional.isEmpty()) throw new NotFoundException("Người đặt vé không tồn tại");

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(billRequest.getScheduleId());
        if(scheduleOptional.isEmpty()) throw new NotFoundException("Lịch chiếu không tồn tại");

        Bill newBill = new Bill();
        newBill.setTradingCode(GenerateCodeUtil.generateCode());
        newBill.setCreateTime(LocalDateTime.now().now());
        newBill.setUser(userOptional.get());

        billRepository.save(newBill);

        Optional<Seat> seatOptional;
        Optional<Ticket> ticketOptional;
        for(Integer seatId : billRequest.getListSeatId()) {
            seatOptional = seatRepository.findById(seatId);
            if(seatOptional.isEmpty()) throw new NotFoundException("Ghế có id " + seatId + " không tồn tại");

            ticketOptional = ticketRepository.findByScheduleIdAndSeatId(billRequest.getScheduleId(), seatId);
            if(ticketOptional.isPresent()) throw new UnAvailableException("Đã có người nhanh tay hơn đặt ghế, mời bạn chọn lại");

            Ticket newTicket = new Ticket();
            newTicket.setBill(newBill);
            newTicket.setSeat(seatOptional.get());
            newTicket.setSchedule(scheduleOptional.get());
            newTicket.setPrice(scheduleOptional.get().getPrice());

            ticketRepository.save(newTicket);

            totalPrice += scheduleOptional.get().getPrice();
        }

        if(!billRequest.getFoodRequestList().isEmpty()) {  //Kiểm tra nếu order đồ ăn thì mới thực hiện lặp để tạo billFood
            Optional<Food> foodOptional;
            for (OrderFoodRequest item : billRequest.getFoodRequestList()) {
                foodOptional = foodRepository.findById(item.getFoodId());
                if(foodOptional.isEmpty()) throw new NotFoundException("Đồ ăn có id " + item.getFoodId() + " không tồn tại");

                BillFood newBillFood = new BillFood();
                newBillFood.setBill(newBill);
                newBillFood.setFood(foodOptional.get());
                newBillFood.setQuantity(item.getQuantity());
                newBillFood.setPrice(item.getQuantity() * foodOptional.get().getPrice());

                billFoodRepository.save(newBillFood);

                totalPrice += item.getQuantity() * foodOptional.get().getPrice();
            }
        }

        newBill.setTotalPrice(totalPrice);
        billRepository.save(newBill);

        //Khởi tạo mới một billResponse rồi trả về
        BillResponse billResponse = new BillResponse();
        billResponse.setBillId(newBill.getBillId());
        billResponse.setTotalPrice(newBill.getTotalPrice());
        billResponse.setTradingCode(newBill.getTradingCode());
        billResponse.setCreateTime(newBill.getCreateTime());

        return billResponse;
    }

    @Override
    public Bill getBillByBillId(Integer billId) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if(billOptional.isEmpty()) return null;

        return billOptional.get();
    }

    @Override
    public String deleteBill(Integer userId, Integer billId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) return "Người đặt vé không tồn tại";

        Optional<Bill> billOptional = billRepository.findById(billId);
        if(billOptional.isEmpty()) return "Hoá đơn có id " + billId + " không tồn tại";

        Optional<Bill> existBill = billRepository.findByBillIdAndUserId(billId, userId);
        if(existBill.isEmpty()) return "Hoá đơn không phải của người đặt vé";

        billRepository.deleteById(billId);
        return "Thanh toán thất bại, huỷ hoá đơn thành công";
    }

    @Override
    public String sendEmailConfirmPayment(Integer billId, Integer userId) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        if(billOptional.isEmpty()) return "Hoá đơn có id " + billId + " không tồn tại";

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) return "Người đặt vé không tồn tại";

        Optional<Bill> existBill = billRepository.findByBillIdAndUserId(billId, userId);
        if(existBill.isEmpty()) return "Hoá đơn không phải của người đặt vé";

        String message = "Bạn đã đặt vé thành công, chi tiết vé như sau: ";
        for(Ticket item : billOptional.get().getTicketList()) {
            message += "Tên phim: " + item.getSchedule().getMovie().getMovieName() + ", ngày chiếu: " + item.getSchedule().getStartDate()
                    + ", giờ chiếu: " + item.getSchedule().getStartTime() + ", mã số ghế: " + item.getSeat().getSeatName();
        }
        for(BillFood item : billOptional.get().getBillFoodList()) {
            message += ", Đồ ăn đi kèm: " + item.getFood().getFoodName() + ", số lượng: " + item.getQuantity();
        }

        emailService.sendEmail(userOptional.get().getEmail(), "Xác nhận đặt vé thành công", message);

        return "Thanh toán thành công, đã gửi mail thông báo xác nhận";
    }
}
