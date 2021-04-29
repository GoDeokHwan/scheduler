package com.example.scheduler.api.domain.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    @Autowired
    SampleUserRepository sampleUserRepository;

    public SampleUser save(SampleUser sampleUser) {
        return sampleUserRepository.save(sampleUser);
    }

    public List<SampleUser> findAll() {
        return sampleUserRepository.findAll();
    }

}
