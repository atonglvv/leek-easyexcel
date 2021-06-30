package cn.atong.leek.easyexcel.leekeasyexcel.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: leek-easyexcel
 * @description: UserEntity
 * @author: atong
 * @create: 2021-06-29 17:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
}
