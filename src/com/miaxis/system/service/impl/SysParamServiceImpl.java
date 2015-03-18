package com.miaxis.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.system.entity.SysParam;
import com.miaxis.system.service.SysParamService;

@Service("sysParamService")
public class SysParamServiceImpl extends CommonServiceImpl implements
        SysParamService {

    public String getValue(String key) {

        List<SysParam> list = this.getListByHql(SysParam.class,
                " from SysParam t WHERE t.spKey='" + key
                        + "' and t.spStatus='1'");
        if (list.size() > 0) {
            return list.get(0).getSpValue();
        } else {
            return null;
        }
    }

}
