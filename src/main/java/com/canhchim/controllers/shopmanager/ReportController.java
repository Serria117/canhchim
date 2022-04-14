package com.canhchim.controllers.shopmanager;

import com.canhchim.payload.response.Content;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.services.*;
import lombok.AllArgsConstructor;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController
@RequestMapping(path = "/shop-report")
@AllArgsConstructor
public class ReportController
{
    UserService userService;
    OrderService orderService;
    ProductService productService;
    ShopService shopService;
    ReportService reportService;

    @GetMapping("income-by-date")
    public ResponseEntity<?> incomeByDate(@RequestParam(name = "shopId") @NotNull long shopId,
                                          @RequestParam(name = "fromDate", required = false) @Pattern("dd-MM-yyyy") String fromDate,
                                          @RequestParam(name = "toDate", required = false) @Pattern("dd-MM-yyyy") String toDate)
    {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Content content;
        HttpStatus status;
        try {
            @Subst("dd-MM-yyyy")
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            if ( Objects.equals(toDate, "") || toDate == null ) {
                toDate = today;
            }
            if ( Objects.equals(fromDate, "") || fromDate == null ) {
                fromDate = today;
            }

            var startDate = LocalDateTime.parse(fromDate + " 00:00:00", dateFormatter);
            var endDate = LocalDateTime.parse(toDate + " 23:59:59", dateFormatter);
            var res = reportService.incomeByDate(shopId, startDate, endDate);
            res.put("from", fromDate);
            res.put("to", toDate);
            content = new Content(res);
            status = HttpStatus.OK;
        }
        catch ( Exception e ) {
            content = new Content(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status)
                             .body(new ResponseObject(
                                     status.value(),
                                     status.getReasonPhrase(),
                                     content));
    }
}
