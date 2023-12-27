package com.virtualarena.api.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("REPLY")
@NoArgsConstructor
@Getter
@Setter
public class ReplyEntity extends EventPostEntity {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
