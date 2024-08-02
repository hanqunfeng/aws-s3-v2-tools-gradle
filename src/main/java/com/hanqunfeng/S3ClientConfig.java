package com.hanqunfeng;

import lombok.Getter;

import static software.amazon.awssdk.transfer.s3.SizeConstant.MB;

/**
 * <h1>config</h1>
 * Created by hanqf on 2024/7/19 16:11.
 */

public class S3ClientConfig {
    /**
     * 分段上传条件，每段必须>=5M，<=5G，这里要注意，分段上传要求除最后一段外，其余段的大小不能小于5M
     */
    public static final Long MIN_PART_SIZE = 5 * MB;
    /**
     * 最大连接数
     */
    public static final int MAX_CONNECTIONS = 500;

    @Getter
    private String region = "myRegionName";
    @Getter
    private String aws_access_key = "myAccessKey";
    @Getter
    private String aws_secret_key = "aws_secret_key";


    public S3ClientConfig(String region, String aws_access_key, String aws_secret_key) {
        this.region = region;
        this.aws_access_key = aws_access_key;
        this.aws_secret_key = aws_secret_key;
    }
}
