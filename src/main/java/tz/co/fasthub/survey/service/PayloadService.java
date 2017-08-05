package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Payload;

/**
 * Created by root on 6/20/17.
 */
public interface PayloadService {

    Payload save(Payload payload);
     Payload getPayloadById(Long id);
}