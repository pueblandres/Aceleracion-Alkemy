package com.alkemy.ong.domain.members;

import com.alkemy.ong.domain.members.Members;
import com.alkemy.ong.domain.utils.PageModel;

import java.util.List;

public interface MemberGateway {

    Members save(Members members);

    PageModel findByPage(int pageNumber, int size);

    void delete(Long id);

    Members update(Long id, Members members);
}
