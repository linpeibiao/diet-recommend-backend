package icu.xiaohu.diet_recommend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.model.dto.MaterialDto;
import icu.xiaohu.diet_recommend.model.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface IMaterialService extends IService<Material> {

    /**
     * add
     * @param materials
     * @return
     */
    boolean add(List<MaterialDto> materials);

    /**
     * delete
     * @param materialIds
     * @return
     */
    boolean delete(List<Long> materialIds);

    /**
     * update
     * @param materialId
     * @param material
     * @return
     */
    boolean modify(Long materialId, MaterialDto material);

    /**
     * get
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Material> getMaterialPage(int pageNum, int pageSize);

    /**
     * list
     * @param material
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Material> listQuery(MaterialDto material, int pageNum, int pageSize);
}
