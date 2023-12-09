package com.virtualarena.api.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("QUESTION")
@Getter
@Setter
public class QuestionEntity extends EventPostEntity {
}
