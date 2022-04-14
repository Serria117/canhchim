package com.canhchim;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CanhChimApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CanhChimApplication.class, args);
    }

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
}
