package com.sd.lib.push.xiaomi;

import android.content.Context;

import com.xiaomi.mipush.sdk.MiPushClient;

public class FPushXiaoMi
{
    private FPushXiaoMi()
    {
    }

    /**
     * 初始化
     *
     * @param appid
     * @param appkey
     */
    public static void init(String appid, String appkey, Context context)
    {
        MiPushClient.registerPush(context, appid, appkey);
    }
}
