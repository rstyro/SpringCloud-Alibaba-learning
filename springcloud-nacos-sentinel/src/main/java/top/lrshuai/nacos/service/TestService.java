package top.lrshuai.nacos.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {

    @SentinelResource(value = "TestService#sayHi", defaultFallback = "bonjourFallback")
    public String sayHi(String name){
        return "Hi "+name;
    }

    @Autowired
    RestTemplate restTemplate;

    @SentinelResource(value = "TestService#remoteSayHi", defaultFallback = "bonjourFallback")
    public String remoteSayHi(String name){
        String result = restTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, String.class);
        return "访问provider 返回 : " + result;
    }

    public String bonjourFallback(Throwable t) {
        if (BlockException.isBlockException(t)) {
            return "===Blocked by Sentinel: " + t.getClass().getSimpleName();
        }
        return "===Oops, failed: " + t.getClass().getCanonicalName();
    }
}
