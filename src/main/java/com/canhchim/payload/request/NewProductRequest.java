package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewProductRequest
{

    Long id = null;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name;
    String code = Long.toString(Instant.now().toEpochMilli());
    @PositiveOrZero(message = "Giá tiền phải lớn hơn hoặc bằng 0")
    Long price;
    @PositiveOrZero(message = "Giá tiền phải lớn hơn hoặc bằng 0")
    Long price2 = 0L;
    @PositiveOrZero(message = "Giá tiền phải lớn hơn hoặc bằng 0")
    Long price3 = 0L;
    @PositiveOrZero(message = "Số lượng phải lớn hơn 0")
    Integer quantity = 9999;
    int isMultiPrice = 0;
    @NotNull
    Long categoryId;
    @NotNull
    Long shopId;
    int hasTopUp = 0;
    int isTopUp = 0;
    List<String> images = new ArrayList<>();

}
