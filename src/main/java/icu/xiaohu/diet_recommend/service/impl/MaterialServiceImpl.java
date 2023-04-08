package icu.xiaohu.diet_recommend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MaterialDto;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.mapper.MaterialMapper;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    @Override
    public boolean add(List<MaterialDto> materials) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;

        // 食材数据列表
        List<Material> materialList = new ArrayList<>();
        // 参数判断
        for (int i = 0; i < materials.size(); i ++){
            MaterialDto materialDto = materials.get(i);
            // 名称判空
            String name = materialDto.getName();
            // 类型判空
            String type = materialDto.getType();
            // 营养成分判空
            String nutrition = materialDto.getNutrition();
            if (StringUtils.isAnyBlank(name, nutrition, type)){
                errMsg.append("第" + (i + 1) + "名称为【" + name + "】的餐品数据不能为空\n");
                isValidate = true;
                continue;
            }
            // 转换为实体
            Material material = BeanUtil.copyProperties(materialDto, Material.class);
            materialList.add(material);
        }

        if (isValidate){
            throw new BusinessException(ResultCode.PARAMS_ERROR, errMsg.toString());
        }

        return this.saveBatch(materialList);
    }

    @Override
    public boolean delete(List<Long> materialIds) {
        return removeBatchByIds(materialIds);
    }

    @Override
    public boolean modify(Long materialId, MaterialDto materialDto) {
        UpdateWrapper<Material> update = new UpdateWrapper<>();
        String name = materialDto.getName();
        String desc = materialDto.getDescription();
        String type = materialDto.getType();
        BigDecimal price = materialDto.getPrice();

        // 拼接
        if (!StringUtils.isBlank(name)){
            update.set("name", name);
        }
        if (!StringUtils.isBlank(desc)){
            update.set("description", desc);
        }
        if (!StringUtils.isBlank(type)){
            update.set("type", type);
        }
        if (price != null){
            update.set("price", price);
        }
        // 查询materialId是否存在
        if (this.getById(materialId) == null){
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        update.eq("id", materialId);
        return this.update(update);
    }

    @Override
    public IPage<Material> getMaterialPage(int pageNum, int pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

    @Override
    public IPage<Material> listQuery(MaterialDto materialDto, int pageNum, int pageSize) {
        if (materialDto == null){
            return this.page(new Page<>(pageNum, pageSize));
        }
        QueryWrapper<Material> query = new QueryWrapper<>();
        // 查询条件拼接
        String type = materialDto.getType();
        String name = materialDto.getName();
        if (!StringUtils.isBlank(name)){
            query.like("name", "%"+ name +"%");
        }
        if (!StringUtils.isBlank(type)){
            query.eq("type", type);
        }
        return this.page(new Page<Material>(pageNum, pageSize), query);
    }
}
