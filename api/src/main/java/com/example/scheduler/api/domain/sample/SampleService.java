package com.example.scheduler.api.domain.sample;

import com.example.scheduler.api.domain.sample.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SampleService {

    @Autowired
    SampleUserRepository sampleUserRepository;

    public SampleUser save(UserRequest request) {
        log.info("sampleUser :: name {}, phoneNumber {}", request.getName(), request.getPhoneNumber());
        return sampleUserRepository.save(SampleUser
                .builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build());
    }

    public List<SampleUser> findAll() {
        return sampleUserRepository.findAll();
    }

}
