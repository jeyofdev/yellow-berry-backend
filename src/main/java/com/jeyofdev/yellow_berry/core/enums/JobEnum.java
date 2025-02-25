package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.JobDeserializer;

@JsonDeserialize(using = JobDeserializer.class)
public enum JobEnum {
    CEO,
    CTO,
    MANAGER,
    CO_FOUNDER,
    FOUNDER,
    TEAM_LEADER,

}
