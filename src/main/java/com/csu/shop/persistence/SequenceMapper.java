package com.csu.shop.persistence;

import com.csu.shop.domain.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {
    Sequence getSequence(Sequence sequence);

    void updateSequence(Sequence sequence);
}
