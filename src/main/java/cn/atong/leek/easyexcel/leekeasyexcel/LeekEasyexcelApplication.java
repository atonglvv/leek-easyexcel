package cn.atong.leek.easyexcel.leekeasyexcel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.atong.leek.easyexcel.leekeasyexcel.mapper")
public class LeekEasyexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeekEasyexcelApplication.class, args);
    }

}
