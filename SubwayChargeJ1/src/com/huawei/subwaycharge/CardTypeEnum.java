package com.huawei.subwaycharge;

/**
 * <p>Title: 考生不能修改</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public enum CardTypeEnum {
            A, /* 单程票 */
            B, /* 老年卡 */
            C; /* 普通卡 */

    private static final String[] CARD_TYPE_STR;

    public String toString() {
        return CARD_TYPE_STR[this.ordinal()];
    }

    static {
        CARD_TYPE_STR = new String[] {"单程票", "老年卡", "普通卡"};
    }
}
