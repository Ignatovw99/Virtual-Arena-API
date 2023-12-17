package com.virtualarena.api.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuestionAndAnswerConstants {

    public static final String QUESTION_RESOURCE = "Question";

    public static final String QUESTION_CONTENT_EMPTY = "The question's content is empty";

    public static final String QUESTION_NOT_PART_OF_EVENT = "The question does not belong to this event";

    public static final String USER_IS_NOT_PARTICIPATING_IN_EVENT = "User is not participating in event";

    public static final String USER_IS_PARTICIPATING_IN_EVENT = "User is participating in this event";

    public static final String ID = "id";
}
