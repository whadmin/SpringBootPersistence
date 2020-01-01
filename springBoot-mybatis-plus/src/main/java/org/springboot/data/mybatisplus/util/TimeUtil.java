package org.springboot.data.mybatisplus.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;

/**
 * @author TianYiming
 */
@UtilityClass
public class TimeUtil {

    public static Integer toTs(Timestamp timestamp) {
        if (timestamp == null) {
            return 0;
        }
        return Math.toIntExact(timestamp.getTime()/1000);
    }

    public static Integer currentTimeStampInt() {
        Long timeStamp = System.currentTimeMillis()/1000;
        return timeStamp.intValue();
    }
}
