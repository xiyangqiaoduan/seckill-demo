package com.yangcb.seckill.hystrix.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-26 18:01
 **/
public class HystrixFilter  implements Filter {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.debug("hystrix 开始拦截");
        DubboHystrixCommand command=new DubboHystrixCommand(invoker, invocation);
        return command.execute();
    }
}
