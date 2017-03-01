package dayan.eve.repository;

import dayan.eve.model.Constants;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xsg on 12/30/2016.
 */
@Mapper
public interface CodeRepository {
    void setCode(String emojiCode);
}
