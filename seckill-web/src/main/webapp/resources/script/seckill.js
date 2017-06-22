/***存放主要交互逻辑代码js****/
//模块化
var seckill = {
    //封装秒杀相关ajax的url
    URL: {
        now: function () {
            return "/seckill/time/now";
        },
        exposer: function (seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },
    //验证手机号
    validataPhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }

    },
    handleSeckillKill: function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {

            console.log(result);
            if (result && result['success']) {
                var exposer = result['data'];

                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log(killUrl);
                    $('#killBtn').one('click', function () {
                        //禁用按钮
                        $(this).addClass('disable');
                        //发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            } else {
                                var killResult = result['data'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });

                } else {
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdwon(seckillId, now, start, end);

                }

            } else {
                console.log("result" + result)
            }

        });
        node.show();

    },
    countdwon: function (seckillId, nowTime, startTime, endTime) {

        var seckillBox = $('#seckill-box');

        //时间的判断
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {

            //秒杀未开始 计时时间绑定
            var killTime = new Date(startTime + 1000);

            //倒计时插件
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀计时：%D天  %H时  %M分  %S秒');
                seckillBox.html(format);
            }).on("finish.countdown", function () {
                //获取秒杀地址控制显示逻辑
                seckill.handleSeckillKill(seckillId, seckillBox);
            });
        } else {
            seckill.handleSeckillKill(seckillId, seckillBox);
        }


    },

    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机和用户登录的验证,计时交互
            //从cookie查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if (!seckill.validataPhone(killPhone)) {
                //控制输出
                var killPhoneModal = $('#killPhoneModal');

                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhone').val();
                    if (seckill.validataPhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }

                });
            }


            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //计时交互

            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断
                    seckill.countdwon(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result);
                }

            });

        }
    }

}