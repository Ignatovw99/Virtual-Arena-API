package com.virtualarena.api.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserConstants {

    public static final String USER_RESOURCE = "User";

    public static final String EMAIL_SHOULD_BE_PRESENT = "Email should be present";
    public static final String USER_ALREADY_EXISTS = "User exists already";

    public static final String FULL_NAME_SHOULD_BE_PRESENT = "Full name should be present";

    public static final String PROFILE_PICTURE_SHOULD_BE_PRESENT = "Profile picture should be present";

    public static final String USER_IS_MISSING = "User is missing";

    public static final String EMAIL_CANNOT_BE_CHANGED = "User email cannot be changed";

    public static final String ID = "id";

    public static final String EMAIL = "email";
}
