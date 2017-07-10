package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Payload;
import tz.co.fasthub.survey.repository.PayloadRepository;
import tz.co.fasthub.survey.service.PayloadService;

/**
 * Created by root on 6/20/17.
 */
@Service("payloadService")
public class PayloadServiceImpl implements PayloadService {

    private final PayloadRepository payloadRepository;

    @Autowired
    public PayloadServiceImpl(PayloadRepository payloadRepository) {
        this.payloadRepository = payloadRepository;
    }


    @Override
    public Payload save(Payload payload) {
        return payloadRepository.save(payload);
    }

    @Override
    public Payload getPayloadById(Long id) {
        return payloadRepository.findOne(id);
    }

}