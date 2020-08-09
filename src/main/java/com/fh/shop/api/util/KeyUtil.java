package com.fh.shop.api.util;

public class KeyUtil {

    public static final int  MEMBER_KEY_GUOQI_TIME=5*60;
    public static String buildMemberKey(String uuid,Long mamberId){
        return "member"+uuid+":"+mamberId;

    }

    public static String buildCartKey(Long memberId){
        return"cart:"+memberId;
    }
}
