package com.yangcb.seckill.hystrix.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-26 18:01
 **/
@Activate(group = Constants.CONSUMER)
public class HystrixFilter  implements Filter{
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return null;
    }
}
