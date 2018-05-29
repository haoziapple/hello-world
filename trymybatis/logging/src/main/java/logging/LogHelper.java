package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用的日志打印类
 * @author wanghao
 */
public class LogHelper {
    private static final Logger log = LoggerFactory.getLogger("ufast");

    private static final String split = ":";

    /**
     * 业务性质的错误打印，必须传入错误码
     *
     * @param message
     * @param errorCode
     */
    public static void error(String message, int errorCode) {
        if (log.isWarnEnabled())
            log.warn(errorCode + split + message);
    }

    /**
     * 系统级的异常打印
     *
     * @param message
     * @param e
     */
    public static void fatal(String message, Throwable e) {
        log.error(message, e);
    }

    public static void debug(String message) {
        if (log.isDebugEnabled())
            log.debug(message);
    }

    public static void monitor(String message) {
        if (log.isInfoEnabled())
            log.info(message);
    }
}
