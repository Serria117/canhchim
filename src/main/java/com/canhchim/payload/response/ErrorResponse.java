package com.canhchim.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorResponse
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private int code;
    private List<Object> errors;

    public ErrorResponse(LocalDateTime time, HttpStatus status, int code, Object message) {
        this.timestamp = time;
        this.status = status;
        this.code = code;
        this.errors = new ArrayList<>();
        this.errors.add(message);
    }
}
