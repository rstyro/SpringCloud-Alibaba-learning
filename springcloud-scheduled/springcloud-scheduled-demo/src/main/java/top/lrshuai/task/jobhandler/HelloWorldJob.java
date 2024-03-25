package top.lrshuai.task.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloWorldJob {

    @XxlJob("helloWorldHandler")
    public void helloWorldHandler(){
        log.info("Hello XxlJob !!!");
        XxlJobHelper.log("Hello XxlJob !!!");
    }
}
