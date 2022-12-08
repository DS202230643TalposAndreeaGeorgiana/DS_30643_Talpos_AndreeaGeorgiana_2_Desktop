package com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Measure implements Serializable {
    private Long measureId;
    private Long deviceId;
    private Float energyConsumption;
    private Timestamp timestamp;
}
