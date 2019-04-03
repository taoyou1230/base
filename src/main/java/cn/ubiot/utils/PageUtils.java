package cn.ubiot.utils;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页工具类
 */
public class PageUtils {

    public static RowBounds setRowBounds(int pageNum,int pageSize){
        int offset = 0;
        if(pageNum>0) {
            offset = (pageNum - 1) * pageSize;
        }
        return new RowBounds(offset,pageSize);
    }
}
