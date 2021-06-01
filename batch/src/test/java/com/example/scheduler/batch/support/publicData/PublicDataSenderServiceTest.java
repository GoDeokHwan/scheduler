package com.example.scheduler.batch.support.publicData;


import com.example.scheduler.batch.support.PublicDataSenderService;
import com.example.scheduler.batch.support.model.PublicDataHoliday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublicDataSenderServiceTest {

    @Autowired
    PublicDataSenderService publicDataSenderService;

    @Test
    public void 공공_데이터_조회 () {
        List<PublicDataHoliday> data = publicDataSenderService.apiSend("2021", "05");
        assertThat(data.size()).isEqualTo(2);
    }

}
