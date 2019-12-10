package com.elijah.permissionhelper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

/**
 * Description:
 *
 * @author elijahliu
 * @Note Talk is cheap,just show me ur code.- -!
 * ProjectName:permissionhelper
 * PackageName: com.elijah.permissionhelper
 * Date: 2019/12/9 11:13
 */
@Mapper
public interface EhrNewbornDao {

    @Select("select * from ehr_newborn a ,ehr_newborn_result b where a.sampleid=b.sampleid and a.enable=1 limit 1;")
    Map selectOne();

}
