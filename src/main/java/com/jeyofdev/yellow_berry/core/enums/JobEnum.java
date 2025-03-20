package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.JobDeserializer;

import java.util.Random;

@JsonDeserialize(using = JobDeserializer.class)
public enum JobEnum {
    CEO,
    CTO,
    MANAGER,
    CO_FOUNDER,
    FOUNDER,
    TEAM_LEADER;


    public static JobEnum getRandomJob() {
        JobEnum[] jobs = JobEnum.values();
        return jobs[new Random().nextInt(jobs.length)];
    }
}
