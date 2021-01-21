package com.sd.lib.push.xiaomi;

import android.content.Context;
import android.content.IntentFilter;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

public class DefaultXiaoMiPushMessageReceiver extends PushMessageReceiver
{
    private boolean mHasRegister;
    /** 注册id */
    public String mRegistrationId;

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message)
    {
        final String command = message.getCommand();
        if (MiPushClient.COMMAND_REGISTER.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                final List<String> listArguments = message.getCommandArguments();
                if (listArguments != null && listArguments.size() > 0)
                {
                    final String arg0 = listArguments.get(0);
                    mRegistrationId = arg0;
                }
            }
        }
    }

    /**
     * 注册广播
     *
     * @param context
     */
    public synchronized void register(Context context)
    {
        if (mHasRegister)
            return;

        final IntentFilter filter = new IntentFilter();
        filter.addAction("com.xiaomi.mipush.RECEIVE_MESSAGE");
        filter.addAction("com.xiaomi.mipush.MESSAGE_ARRIVED");
        filter.addAction("com.xiaomi.mipush.ERROR");
        context.registerReceiver(this, filter);
        mHasRegister = true;
    }

    /**
     * 取消注册
     *
     * @param context
     */
    public synchronized void unregister(Context context)
    {
        if (mHasRegister)
        {
            mHasRegister = false;
            context.unregisterReceiver(this);
        }
    }
}
