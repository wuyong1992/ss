package com.zzc.ss.service;

import com.zzc.ss.vo.UserJobVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public interface UserJobService {

    /**
     * 根据条件获取求职信息列表{@link UserJobVO}
     * @param pageable
     * @param realName
     * @param phone
     * @param enterpriseFullName
     * @param status
     * @return
     */
    Page<UserJobVO> getUserJobVOList(Pageable pageable, String realName, String phone, String enterpriseFullName, String status);

    /**
     * 审核某个求职信息通过
     * @param id
     * @return
     */
    Boolean applySuccess(Integer id);

    /**
     * 拒绝某个求职申请
     * @param id
     * @return
     */
    Boolean applyFail(Integer id);
}
