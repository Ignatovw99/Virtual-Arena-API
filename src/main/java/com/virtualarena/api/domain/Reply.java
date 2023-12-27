package com.virtualarena.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Reply extends EventPost {

    private Question question;
}
