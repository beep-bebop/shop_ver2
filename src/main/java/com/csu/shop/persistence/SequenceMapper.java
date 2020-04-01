package com.csu.shop.persistence;

import com.csu.shop.domain.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapper {
    Sequence getSequence(Sequence sequence);

    void updateSequence(Sequence sequence);
}
