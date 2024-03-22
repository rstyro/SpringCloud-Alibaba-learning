package top.lrshuai.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.lrshuai.user.api.entity.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lrs
 * @since 2024-03-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
