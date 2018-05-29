package logging;

import ch.qos.logback.classic.pattern.CallerDataConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;

/**
 * 自定义的logback caller converter
 * @author wanghao
 */
public class FastCallerDataConverter extends CallerDataConverter {
    public String convert(ILoggingEvent le) {
        String result = super.convert(le);
        if (result.endsWith(CoreConstants.LINE_SEPARATOR))
            return result.substring(0, result.length() - CoreConstants.LINE_SEPARATOR_LEN);
        return result;
    }

}
