package top.lrshuai.cloud.task.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;

import java.time.LocalDateTime;


/**
 * 开发自己的任务，继承 IJobHandler
 */
public class DemoHandler extends IJobHandler {
    @Override
    public void execute() throws Exception {
        String jobParam = XxlJobHelper.getJobParam();
        XxlJobHelper.log("自己定义的参数={}",jobParam);
        System.out.println("===================当前时间"+ LocalDateTime.now());
        XxlJobHelper.handleSuccess();
    }
}
