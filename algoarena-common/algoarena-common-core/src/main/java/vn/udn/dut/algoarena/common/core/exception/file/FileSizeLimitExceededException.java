package vn.udn.dut.algoarena.common.core.exception.file;

import java.io.Serial;

/**
 * Filename size limit exception class
 *
 * @author HoaLD
 */
public class FileSizeLimitExceededException extends FileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
