package logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志打印级别自定义converter
 * @author wanghao
 */
public class FastLevelConverter extends ClassicConverter {
    private static final Map<String, String> levelConvertMap = new HashMap<>();

    static {
        levelConvertMap.put("ERROR", "FATAL");
        levelConvertMap.put("WARN", "ERROR");
        levelConvertMap.put("INFO", "MONITOR");
    }


    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String levelString = iLoggingEvent.getLevel().toString();
        String convertLevel = levelConvertMap.get(levelString);
        if (null != convertLevel)
            return convertLevel;
        else
            return levelString;
    }
}
