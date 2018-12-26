package com.przemek.patronage.Equipment;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Equipment {

    public Equipment() {
    }

    @Nullable
    private @Id
    @GeneratedValue
    Long id;
    @Nullable
    private String projectorName;
    private boolean isPhone;
    @Min(value = 0, message = "Internal number has to be an integer between 0 and 99")
    @Max(value = 99, message = "Internal number has to be an integer between 0 and 99")
    @Nullable
    private Integer internalNumber;
    @Nullable
    @Pattern(regexp = "^(\\+\\d{2}\\s\\d{9})$")
    private String externalNumber;
    @Nullable
    private InterfaceConnections connections;

    public Equipment(@Nullable String projectorName,
                     boolean isPhone,
                     @Min(value = 0, message = "Internal number has to be an integer between 0 and 99") @Max(value = 99, message = "Internal number has to be an integer between 0 and 99") int internalNumber,
                     @Nullable @Pattern(regexp = "^(\\+\\d{2}\\s\\d{9})$") String externalNumber,
                     @Nullable InterfaceConnections connections) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
        this.internalNumber = internalNumber;
        this.externalNumber = externalNumber;
        this.connections = connections;
    }

    public Equipment(@Nullable String projectorName,
                     boolean isPhone) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
    }
}
