package com.canhchim.services;

import com.canhchim.models.PrdOrder;
import com.canhchim.models.dto.OrderReportDto;
import com.canhchim.repositories.PrdOrderRepository;
import com.canhchim.repositories.ShpShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class ReportService
{
    ShpShopRepository shopRepo;
    PrdOrderRepository orderRepo;

    public Map<String, Object> incomeByDate(long shopId, LocalDateTime fromDate, LocalDateTime toDate)
    {
        if ( toDate.isBefore(fromDate) ) {
            throw new RuntimeException("From-Date must be before To-Date.");
        }

        var period = toDate.toEpochSecond(ZoneOffset.UTC) - fromDate.toEpochSecond(ZoneOffset.UTC);
        if ( period / (60 * 60 * 24) > 365 ) {
            throw new RuntimeException("Period must be within 365 days.");
        }

        Map<String, Object> result = new TreeMap<>();
        List<PrdOrder> orders = orderRepo.findByDate(shopId, fromDate, toDate);

        var total = orders.stream().mapToLong(PrdOrder::getTotalPrice).sum();
        var countOrder = orders.size();
        result.put("totalIncome", total);
        result.put("totalOrders", countOrder);

        var orderReports = new ArrayList<OrderReportDto>();
        orders.forEach(o -> {
            OrderReportDto orderReport = new OrderReportDto();
            orderReport.setOrderTime(o.getOrderDate1());
            orderReport.setTotal(o.getTotalPrice());
            orderReport.setTable(o.getTable());
            var orderList = o.getOrderList();
            orderList.forEach(orderReport::addItem);
            orderReports.add(orderReport);
        });

        result.put("detail", orderReports);
        return result;
    }
}
