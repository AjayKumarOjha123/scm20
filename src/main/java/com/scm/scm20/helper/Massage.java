package com.scm.scm20.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Massage {
    private String contant;
    @Builder.Default
    private MassageType type = MassageType.blue;
}
