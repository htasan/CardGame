package com.cardgame.testutil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestConstants {
    private TestConstants() {}

    public static final List<Integer> EMPTY_DAMAGE_LIST = Collections.emptyList();
    public static final List<Integer> DAMAGE_LIST_WITH_ONE_ELEMENT = Collections.singletonList(3);
    public static final List<Integer> DAMAGE_LIST_WITH_FOUR_ELEMENTS = Collections.unmodifiableList(Arrays.asList(1,1,2,3));
    public static final List<Integer> DAMAGE_LIST_WITH_FIVE_ELEMENTS = Collections.unmodifiableList(Arrays.asList(1,1,2,3,3));
    public static final List<Integer> DAMAGE_LIST_WITH_SIX_ELEMENTS = Collections.unmodifiableList(Arrays.asList(1,1,2,3,3,4));
}
