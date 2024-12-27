package com.huijia.sharing.module.system.model.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import java.util.List;

/**
 * @author huijia
 * @date 2024/12/26 9:24
 */
@Data
public class ChangeRoleRequest {
    private Long roleId;
    private Long[] userIds;

    private Long userId;
    private Long[] roleIds;



    @JsonSetter("userIds")
    public void setRes(Object userIds) {
      this.userIds =  parse(userIds);
    }

    @JsonSetter("roleIds")
    public void setRoleIds(Object roleIds) {
        this.roleIds = parse(roleIds);
    }

    public Long[] parse(Object object){
        if (object instanceof String) {
            String[] parts = ((String) object).split(",");
            Long [] res = new Long[parts.length];
            for (int i = 0; i < parts.length; i++) {
                res[i] = Long.valueOf(parts[i].trim());
            }
            return res;
        } else if (object instanceof List) {
            List<?> list = (List<?>) object;
           return list.stream()
                    .map(item -> Long.valueOf(item.toString()))
                    .toArray(Long[]::new);
        } else {
           return new Long[0];
        }
    }

}
