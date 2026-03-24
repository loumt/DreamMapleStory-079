import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LouMT
 * @name DateTest
 * @date 2026-03-17 9:26
 * @email lmtemail163@163.com
 * @description
 */
public class DateTest {
    public static void main(String[] args) {

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(10);

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeText = startTime.format(formatter);
        String endTimeText = endTime.format(formatter);

        System.out.println(startTimeText);
        System.out.println(endTimeText);
    }
}
