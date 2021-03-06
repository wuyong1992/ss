package com.zzc.ss.repository;

import com.zzc.ss.entity.EnterpriseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface EnterpriseInfoRepository extends JpaRepository<EnterpriseInfo, Integer>, JpaSpecificationExecutor<EnterpriseInfo> {


    /**
     * 根据企业全称模糊查询出符合条件的企业ID集合
     *
     * @param fullName
     * @return
     */
    @Query("select e.enterpriseId from EnterpriseInfo e where e.fullName like %?1%")
    List<Integer> selectEnterpriseIdByFullNameLike(String fullName);

    /**
     * 根据ID查询企业去盐城
     * @param enterpriseId
     * @return
     */
    @Query("select e.fullName from EnterpriseInfo e where e.enterpriseId = ?1")
    String selectEnterpriseFullNameById(Integer enterpriseId);


    /**
     * 获取简单的对象
     * @return
     */
    @Query("select e.enterpriseId, e.fullName, e.status from EnterpriseInfo e order by e.sort DESC ")
    List<EnterpriseInfo> selectSimpleList();

}
