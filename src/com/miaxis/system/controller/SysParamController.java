package com.miaxis.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.SysParam;

/**
 * 系统参数
 * 
 * @author Zhou.Wf
 * 
 */

@Controller
@RequestMapping("/sysparam")
public class SysParamController extends CommonController {

    /**
     * 主页
     * 
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "main")
    public ModelAndView main(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView mav = this
                .getModelMainMav("WEB-INF/pages/system/param/paramList");
        return mav;
    }

    /**
     * 获取列表
     * 
     * @param id
     * @return
     */
    @RequestMapping(params = "list")
    @ResponseBody
    public Map list(String page, String sort, String order, String rows,
            String qspType, String qspName) {

        /**
         * 初始化分页对象
         */
        PageConfig pageConfig = new PageConfig();
        pageConfig.initData(page, rows, true);

        /***************************************************************
         * 按QBC查询
         ***************************************************************/
        QueryCondition qc = new QueryCondition();

        if (StringUtils.isNotEmpty(sort)) {
            if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)) {
                qc.desc(sort);
            } else {
                qc.asc(sort);
            }
        }

        if (StringUtils.isNotEmpty(qspType)) {
            qc.eq("spType", qspType);
        }
        if (StringUtils.isNotEmpty(qspName)) {
            qc.like("spName", qspName.concat("%"));
        }

        List list = this.commonService.getPageList(SysParam.class, pageConfig,
                qc);
        return this.buidResultMap(list, pageConfig.getTotalCount());
    }

    /**
     * 修改或新增
     * 
     * @param id
     * @return
     */
    @RequestMapping(params = "insertOrUpdate")
    public ModelAndView insertOrUpdate(String id, String operationType,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(
                "WEB-INF/pages/system/param/paramDetail");
        if (operationType.equals("edit")) {
            SysParam sysParam = commonService.get(SysParam.class, id);

            mav.getModelMap().put("sysParam", sysParam);
            mav.getModelMap().put("operationType", "edit");
        } else {
            mav.getModelMap().put("operationType", "insert");
        }

        return mav;
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @RequestMapping(params = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map del(String id) {

        SysParam sysParam = commonService.get(SysParam.class,
                Long.parseLong(id));

        if (sysParam == null) {
            return this.buidMessageMap("该参数信息不存在!", "1");
        } else {
            try {
                commonService.delete(sysParam);
            } catch (Exception e) {
                return this.buidMessageMap("参数保存失败了", "1");
            }
            return this.buidMessageMap("参数信息删除成功!", "0");
        }
    }

    /**
     * 保存
     * 
     * @param id
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public Map save(SysParam sysParam, String operationType) {

        String msg = operationType.equals("edit") ? "修改" : "新增";
        try {
            if (operationType.equals("edit")) {
                QueryCondition qc = new QueryCondition();
                qc.eq("id", sysParam.getId());
                List<SysParam> list = commonService.getPageList(SysParam.class,
                        null, qc);
                if (list == null) {
                    return this.buidMessageMap("该参数信息不存在!", "1");
                } else {
                    commonService.updateEntitie(sysParam);
                }
            } else {
                List<SysParam> list = commonService.getListByHql(
                        SysParam.class, " from SysParam t WHERE t.spKey='"
                                + sysParam.getSpKey() + "' and t.spStatus='1'");
                if (list.size() > 0) {
                    return this.buidMessageMap(msg + "参数失败，该参数已存在！", "1");
                }
                commonService.save(sysParam);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return this.buidMessageMap(msg + "参数失败", "1");
        }

        return this.buidMessageMap(msg + "参数成功", "0");
    }
}